/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.SpeedConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveForDist extends CommandBase 
{  
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;

  private int direction = 1;
  private final double distance;

  public DriveForDist(Drivetrain drive, double dist, boolean back) 
  {
    drivetrain = drive;
    if(back)
      direction = -1;
    distance = dist;
    drivetrain.setRamp(SpeedConstants.autoDriveRampSpeed);
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() 
  {
    drivetrain.reset();
  }

  @Override
  public void execute() 
  {
    drivetrain.drive(SpeedConstants.autoDriveSpeed*direction, 0);
  }

  @Override
  public void end(boolean interrupted) 
  {
    drivetrain.setRamp(SpeedConstants.rampSpeed);
    drivetrain.drive(0, 0);
  }

  @Override
  public boolean isFinished() 
  {
    return Math.abs(drivetrain.leftEncoder()) > distance;
  }
}