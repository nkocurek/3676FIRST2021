/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PortConstants;
import frc.robot.Constants.SpeedConstants;

public class BetterElevator extends SubsystemBase 
{   
    private final WPI_TalonFX lElevator = new WPI_TalonFX(PortConstants.lElevator);
    private final WPI_TalonFX rElevator = new WPI_TalonFX(PortConstants.rElevator);

    public BetterElevator()
    {
        rElevator.follow(lElevator);
        rElevator.setInverted(InvertType.OpposeMaster);
    }
    
    public void run(double speed)
    {
        lElevator.set(ControlMode.PercentOutput, SpeedConstants.elevatorSpeed*speed);
    }


    @Override
    public void periodic() 
    {
    
    }
}
