/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PortConstants;
import frc.robot.Constants.SpeedConstants;

public class Feeder extends SubsystemBase 
{
  private final WPI_VictorSPX lFeeder = new WPI_VictorSPX(PortConstants.lFeeder);
  private final WPI_VictorSPX rFeeder = new WPI_VictorSPX(PortConstants.rFeeder);

  public Feeder()
  {
    lFeeder.setInverted(true);

    rFeeder.follow(lFeeder);
    rFeeder.setInverted(InvertType.FollowMaster);

    lFeeder.setNeutralMode(NeutralMode.Coast);
    rFeeder.setNeutralMode(NeutralMode.Coast);
    lFeeder.configOpenloopRamp(SpeedConstants.rampSpeed);
    rFeeder.configOpenloopRamp(SpeedConstants.rampSpeed);
  }

  public void run()
  {
    lFeeder.set(ControlMode.PercentOutput, SpeedConstants.feederSpeed);
  }

  public void regurgitate()
  {
    lFeeder.set(ControlMode.PercentOutput, -SpeedConstants.feederSpeed);
  }

  public void stop()
  {
    lFeeder.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() 
  {
    
  }
}
