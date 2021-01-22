package frc.robot.commands;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.BetterShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Magazine;

public class Autonomous extends SequentialCommandGroup
{
    public Autonomous(Drivetrain drivetrain, BetterShooter shooter, Magazine magazine, Feeder feeder)
    {
        addCommands(
            new ShootForTime(3, shooter, magazine, feeder),
            new DriveForDist(drivetrain, .5, false),
            new DriveForDist(drivetrain, 1, true)
        );
    }
}