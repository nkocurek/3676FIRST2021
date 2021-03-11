/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants.SpeedConstants;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinForDist extends CommandBase {  
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain drivetrain;

    
    private double startAngle;
    private final double angleDist;

    public SpinForDist(Drivetrain drive, double degrees) 
    {
        drivetrain = drive;
        addRequirements(drivetrain);
        angleDist = degrees; 
    }

    @Override
    public void initialize() 
    {
        drivetrain.reset();
        startAngle = drivetrain.getHeading();
    }

    @Override
    public void execute() 
    {
        drivetrain.tankDrive(-SpeedConstants.driveTestSpeed, SpeedConstants.driveTestSpeed);
    }

    @Override
    public void end(boolean interrupted) 
    {
        
    }

    @Override
    public boolean isFinished() 
    {
        return drivetrain.getHeading() == startAngle + angleDist;
    }
}