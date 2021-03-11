package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BetterShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Magazine;

public class Autonomous extends SequentialCommandGroup
{
    public Autonomous(Drivetrain drivetrain, double angle)
    {
        addCommands(new SpinForDist(drivetrain, angle));
    }
}