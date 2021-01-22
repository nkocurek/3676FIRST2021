/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.PortConstants;

public class BetterShooter extends SubsystemBase 
{
  private final WPI_TalonFX lShooter = new WPI_TalonFX(PortConstants.lShooter);
  private final WPI_TalonFX rShooter = new WPI_TalonFX(PortConstants.rShooter);
  
  private double speed;

  public BetterShooter() 
  {
    lShooter.configFactoryDefault();
    rShooter.configFactoryDefault();

    lShooter.configNeutralDeadband(.02);
    rShooter.configNeutralDeadband(.02);
    
    rShooter.follow(lShooter);
    rShooter.setInverted(InvertType.OpposeMaster);
    
    lShooter.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                          Constants.ShooterConstants.kPIDLoopIdx, 
                                          Constants.ShooterConstants.kTimeoutMs);
    rShooter.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                          Constants.ShooterConstants.kPIDLoopIdx, 
                                          Constants.ShooterConstants.kTimeoutMs);                                  
    
    lShooter.configNominalOutputForward(0, Constants.ShooterConstants.kTimeoutMs);
    lShooter.configNominalOutputReverse(0, Constants.ShooterConstants.kTimeoutMs);
    lShooter.configPeakOutputForward(1, Constants.ShooterConstants.kTimeoutMs);
    lShooter.configPeakOutputReverse(-1, Constants.ShooterConstants.kTimeoutMs);
    rShooter.configNominalOutputForward(0, Constants.ShooterConstants.kTimeoutMs);
    rShooter.configNominalOutputReverse(0, Constants.ShooterConstants.kTimeoutMs);
    rShooter.configPeakOutputForward(1, Constants.ShooterConstants.kTimeoutMs);
    rShooter.configPeakOutputReverse(-1, Constants.ShooterConstants.kTimeoutMs);
  
    lShooter.config_kF(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kF, Constants.ShooterConstants.kTimeoutMs);
	  lShooter.config_kP(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kP, Constants.ShooterConstants.kTimeoutMs);
	  lShooter.config_kI(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kI, Constants.ShooterConstants.kTimeoutMs);
    lShooter.config_kD(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kD, Constants.ShooterConstants.kTimeoutMs);
    rShooter.config_kF(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kF, Constants.ShooterConstants.kTimeoutMs);
	  rShooter.config_kP(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kP, Constants.ShooterConstants.kTimeoutMs);
	  rShooter.config_kI(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kI, Constants.ShooterConstants.kTimeoutMs);
	  rShooter.config_kD(Constants.ShooterConstants.kPIDLoopIdx, Constants.ShooterConstants.kD, Constants.ShooterConstants.kTimeoutMs);
  
    lShooter.setNeutralMode(NeutralMode.Brake);
    rShooter.setNeutralMode(NeutralMode.Brake);
    lShooter.configClosedloopRamp(.75);
    rShooter.configClosedloopRamp(.75);
    lShooter.configOpenloopRamp(1);
    rShooter.configOpenloopRamp(1);
    lShooter.setSelectedSensorPosition(0);
    rShooter.setSelectedSensorPosition(0);
  }

  public void shoot(double speed)
  {
    lShooter.set(ControlMode.Velocity, speed);
    this.speed = speed;
  }

  public void run()
  {
    lShooter.set(ControlMode.PercentOutput, 1);
  }

  public void stop()
  {
    lShooter.set(ControlMode.PercentOutput, 0);
  }

  public boolean atSpeed()
  {
    return lShooter.getSelectedSensorVelocity() >= speed;
  }

  @Override
  public void periodic()
  {
    SmartDashboard.putNumber("LSpeed", lShooter.getSelectedSensorVelocity());
    SmartDashboard.putNumber("RSpeed", rShooter.getSelectedSensorVelocity());
  }
}
