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

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PortConstants;
import frc.robot.Constants.SpeedConstants;

public class Intake extends SubsystemBase 
{
    private final WPI_VictorSPX intake = new WPI_VictorSPX(PortConstants.intake);
    private final DoubleSolenoid deployPiston = new DoubleSolenoid(PortConstants.fPiston, PortConstants.rPiston);
    
    public Intake()
    {
        intake.setNeutralMode(NeutralMode.Coast);
        intake.configOpenloopRamp(SpeedConstants.rampSpeed);
    }

    public void deploy()
    {
        run();
        deployPiston.set(Value.kReverse);
    }

    public void unploy()
    {
        stop();
        deployPiston.set(Value.kForward);
    }

    public void run()
    {
        intake.set(ControlMode.PercentOutput, SpeedConstants.intakeSpeed);
    }

    public void regurgitate()
    {
        intake.set(ControlMode.PercentOutput, -SpeedConstants.intakeSpeed);
    }

    public void stop()
    {
        intake.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void periodic() 
    {
       
    }
}
