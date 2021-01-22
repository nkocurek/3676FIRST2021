/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Constants.SpeedConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BetterVision extends CommandBase {
  
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;

  private double tx;
  private double tv;
  private final double offset;
  private double steer;
  private double sum;

  public BetterVision(Drivetrain drive, double offset) 
  {
    drivetrain = drive;
    this.offset = offset;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() 
  {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      sum = 0;
      steer = 0;
  }

  @Override
  public void execute() 
  {
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0) + offset;
    if(tv < 1.0)
        drivetrain.drive(0, 0);
    else
    {
        if(tx < 0)
          steer = -VisionConstants.minDriveSpeed + tx*VisionConstants.kP;
        else
          steer = VisionConstants.minDriveSpeed + tx*VisionConstants.kP;

        if(Math.abs(tx) > VisionConstants.minThreshold)
          sum += tx*VisionConstants.kI;
        else
          sum = 0;  

        steer += sum;

        if(steer > VisionConstants.maxSteer)
          steer = VisionConstants.maxSteer;
        if(steer < -VisionConstants.maxSteer)
          steer = -VisionConstants.maxSteer;
        
        drivetrain.drive(steer, 0);
    }
  }

  @Override
  public void end(boolean interrupted) 
  {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    drivetrain.drive(0, 0);
  }

  @Override
  public boolean isFinished() 
  {
    return Math.abs(tx) < VisionConstants.minThreshold;
  }
}
