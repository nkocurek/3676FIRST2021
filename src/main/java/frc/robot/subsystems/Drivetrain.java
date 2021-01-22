/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.PortConstants;
import frc.robot.Constants.SpeedConstants;

public class Drivetrain extends SubsystemBase 
{  
  private final WPI_TalonFX lMainFalcon = new WPI_TalonFX(PortConstants.lMainFalcon);
  private final WPI_TalonFX rMainFalcon = new WPI_TalonFX(PortConstants.rMainFalcon);
  private final WPI_TalonFX lSubFalcon = new WPI_TalonFX(PortConstants.lSubFalcon);
  private final WPI_TalonFX rSubFalcon = new WPI_TalonFX(PortConstants.rSubFalcon);

  private final DifferentialDrive drive = new DifferentialDrive(lMainFalcon, rMainFalcon);
  private final AHRS gyro = new AHRS(Port.kMXP);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

  public Drivetrain()
  {
    lMainFalcon.configFactoryDefault();
    rMainFalcon.configFactoryDefault();

    lMainFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    rMainFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

    lMainFalcon.setInverted(true);
    rMainFalcon.setInverted(false);

    lMainFalcon.setNeutralMode(NeutralMode.Brake);
    rMainFalcon.setNeutralMode(NeutralMode.Brake);
    lSubFalcon.setNeutralMode(NeutralMode.Brake);
    rSubFalcon.setNeutralMode(NeutralMode.Brake);

    lSubFalcon.follow(lMainFalcon);
    lSubFalcon.setInverted(InvertType.FollowMaster);
    rSubFalcon.follow(rMainFalcon);
    rSubFalcon.setInverted(InvertType.FollowMaster);

    drive.setSafetyEnabled(false);

    lMainFalcon.configOpenloopRamp(SpeedConstants.rampSpeed);
    rMainFalcon.configOpenloopRamp(SpeedConstants.rampSpeed);
    lMainFalcon.configClosedloopRamp(SpeedConstants.autoDriveRampSpeed);
    rMainFalcon.configClosedloopRamp(SpeedConstants.autoDriveRampSpeed);

    reset();
  }

  @Override
  public void periodic() 
  {
    odometry.update(Rotation2d.fromDegrees(getHeading()), lMainFalcon.getSelectedSensorPosition()*AutoConstants.distancePerPulse,
                    rMainFalcon.getSelectedSensorPosition()*AutoConstants.distancePerPulse);

    SmartDashboard.putNumber("Left Encoder", lMainFalcon.getSelectedSensorPosition());                  
    SmartDashboard.putNumber("Right Encoder", rMainFalcon.getSelectedSensorPosition());
  }

  public void drive(double speed, double steer)
  {
    //needs to be tested for direction
    drive.arcadeDrive(steer, speed, true); 
  }
  
  public void reset() 
  {
    lMainFalcon.setSelectedSensorPosition(0);
    rMainFalcon.setSelectedSensorPosition(0);
    odometry.resetPosition(odometry.getPoseMeters(), Rotation2d.fromDegrees(getHeading()));
  }

  public void setRamp(double seconds)
  {
    lMainFalcon.configOpenloopRamp(seconds);
    rMainFalcon.configOpenloopRamp(seconds);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) 
  {
    lMainFalcon.setVoltage(leftVolts);
    rMainFalcon.setVoltage(rightVolts);
    drive.feed();
  }

  public double rightEncoder()
  {
    return rMainFalcon.getSelectedSensorPosition()*AutoConstants.distancePerPulse;
  }

  public double leftEncoder()
  {
    return lMainFalcon.getSelectedSensorPosition()*AutoConstants.distancePerPulse;
  }

  public double rightEncoderRate()
  {
    return rMainFalcon.getSelectedSensorVelocity() * AutoConstants.distancePerPulse;
  }
 
  public double leftEncoderRate()
  {
    return lMainFalcon.getSelectedSensorVelocity() * AutoConstants.distancePerPulse;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() 
  {
    return new DifferentialDriveWheelSpeeds(leftEncoderRate(), rightEncoderRate());
  }

  private double getHeading() 
  {
    return Math.IEEEremainder(gyro.getAngle(), 360);
  }

  public Pose2d getPose() 
  {
    return odometry.getPoseMeters();
  }
}
