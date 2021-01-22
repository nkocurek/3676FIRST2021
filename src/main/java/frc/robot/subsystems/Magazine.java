/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PortConstants;
import frc.robot.Constants.SpeedConstants;

public class Magazine extends SubsystemBase
{
    private final WPI_VictorSPX magazine = new WPI_VictorSPX(PortConstants.magazine);

    public Magazine()
    {
        magazine.setNeutralMode(NeutralMode.Brake);
        magazine.configOpenloopRamp(SpeedConstants.rampSpeed);
    }

    //check speed
    public void run()
    {
        magazine.set(ControlMode.PercentOutput, SpeedConstants.magazineSpeed);
    }

    public void regurgitate()
    {
        magazine.set(ControlMode.PercentOutput, -SpeedConstants.magazineSpeed);
    }

    public void stop()
    {
        magazine.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void periodic() 
    {
    
    }
}
