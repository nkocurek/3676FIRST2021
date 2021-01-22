/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.BetterElevator;
import frc.robot.subsystems.BetterShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.Constants.*;
import frc.robot.commands.Autonomous;
import frc.robot.commands.BetterVision;

public class RobotContainer 
{
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Magazine magazine = new Magazine();
  private final Feeder feeder = new Feeder();
  private final BetterShooter shooter = new BetterShooter();
  private final BetterElevator elevator = new BetterElevator();

  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

  private final Command kill = new InstantCommand(() -> intake.stop())
                                .andThen(() -> shooter.stop())
                                .andThen(() -> magazine.stop())
                                .andThen(() -> feeder.stop());

  private final Command shootSequence = new InstantCommand(() -> shooter.shoot(20000), shooter)
                                          .andThen(new WaitUntilCommand(shooter::atSpeed))
                                          .andThen(feeder::run)
                                          .andThen(magazine::run);

  private final Command bleh = new InstantCommand(() -> shooter.shoot(5000), shooter)
                                          .andThen(new WaitUntilCommand(shooter::atSpeed))
                                          .andThen(feeder::run)
                                          .andThen(magazine::run);

  public RobotContainer() 
  {
    configureDefaultCommands();
    configureButtonBindings();
    configureLimelight();
    configureShuffleboard();
  }

  private void configureDefaultCommands()
  {
    drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.drive(-driver.getRawAxis(1)*SpeedConstants.driveSpeed, driver.getRawAxis(4)*SpeedConstants.driveSpeed), drivetrain));
    elevator.setDefaultCommand(new RunCommand(() -> elevator.run(operator.getRawAxis(1)), elevator));
  }

  private void configureButtonBindings() 
  {
    JoystickButton dA, dB, dBACK,
                   oA, oB, oY, oLB, oRB, oBACK;

    dA = new JoystickButton(driver, 1);
    dB = new JoystickButton(driver, 2);
    dBACK = new JoystickButton(driver, 7);

    oA = new JoystickButton(operator, 1);
    oB = new JoystickButton(operator, 2);
    oY = new JoystickButton(operator, 4);
    oLB = new JoystickButton(operator, 5);
    oRB = new JoystickButton(operator, 6);
    oBACK = new JoystickButton(operator, 7);

    //driver:
    dA.whenPressed(new BetterVision(drivetrain, 0 /*offset*/));
    dB.toggleWhenPressed(new StartEndCommand(() -> intake.deploy(), () -> intake.unploy(), intake));
    dBACK.whenPressed(kill);

    //operator:
    oRB.whenPressed(() -> intake.run()).whenReleased(() -> intake.stop());
    oLB.whenPressed(() -> intake.regurgitate()).whenReleased(() -> intake.stop());
    oA.whenPressed(shootSequence)
    .whenReleased(kill);
    oB.whenPressed(bleh)
    .whenReleased(kill);
    oBACK.whenPressed(kill);
    oY.whenPressed(shooter::run).whenReleased(kill);
  }

  private void configureLimelight()
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
  }

  private void configureShuffleboard()
  {
    
  }

  public Command getAutonomousCommand() 
  {
    return new Autonomous(drivetrain, shooter, magazine, feeder);
  }
}
