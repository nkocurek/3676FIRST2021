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
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveStraightForDist extends CommandBase 
{  
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;

  private int direction = 1;
  private final double rDistance;
  private final double lDistance;
  private final Pose2d goal;

  public DriveStraightForDist(Drivetrain drive, double rDist, double lDist, boolean back) 
  {
    drivetrain = drive;
    if(back)
      direction = -1;
    rDistance = rDist;
    lDistance = lDist;
    drivetrain.setRamp(SpeedConstants.autoDriveRampSpeed);
    goal = new Pose2d(rDistance, lDistance, Rotation2d.fromDegrees(0));
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
    DifferentialDriveKinematics kinematics =
    new DifferentialDriveKinematics(AutoConstants.kTrackwidthMeters);

    var chassisSpeeds = new ChassisSpeeds(SpeedConstants.driveSpeed*direction, 0, 0);

    DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
    
    System.out.println(wheelSpeeds.leftMetersPerSecond);

    drivetrain.tankDrive(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
  }

  @Override
  public void end(boolean interrupted) 
  {
    drivetrain.drive(0, 0);
  }

  @Override
  public boolean isFinished() 
  {
    return drivetrain.getPose().equals(goal);
  }
}