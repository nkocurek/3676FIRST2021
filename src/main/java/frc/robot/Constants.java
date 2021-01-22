/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public final class Constants 
{
    public static final class PortConstants
    {
        public static final int lMainFalcon = 1;
        public static final int lSubFalcon  = 2;
        public static final int rMainFalcon = 3;
        public static final int rSubFalcon  = 4;
        public static final int intake    = 5;
        public static final int magazine  = 6;
        public static final int rFeeder   = 7;
        public static final int lFeeder   = 8;
        public static final int lElevator = 9;
        public static final int rElevator = 10;
        public static final int lShooter  = 11;
        public static final int rShooter  = 12;

        public static final int fPiston  = 5;
        public static final int rPiston  = 4;
    }
    public static final class AutoConstants
    {
        public static final double ksVolts = .193;
        public static final double kvVoltSecondsPerMeter = 2.93;
        public static final double kaVoltSecondsSquaredPerMeter = 0.245;
        public static final double kPDriveVel = 0;
        public static final double kTrackwidthMeters = 0.641241342;
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
        //needs testing
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final double distancePerPulse = (1.0 / 2048) * (.1524 * Math.PI) / 12.92;
    }
    public static final class VisionConstants
    {
        public static final double kP = 0.0015;
        public static final double kI = 0.001;
        public static final double minThreshold = .3;
        public static final double maxSteer     = .4;
        public static final double minDriveSpeed = .26;
        public static final double offset = 3.8;
        public static final double offsetNear = 5.071236;
        public static final double offsetFar  = 3.198624;
        public static final double towerHeight = 98;
        public static final double limeHeight = 21.5;
        public static final double limeAngle = 18.55362925;
    }
    public static final class SpeedConstants
    {
        public static final double driveTestSpeed = 0.4;
        public static final double driveSpeed = driveTestSpeed;//0.8
        public static final double autoDriveSpeed = 0.6;

        public static final double minShootSpeed = .9;
        public static final double maxShootSpeed = 0.95;

        public static final double intakeSpeed = 1.0;
        public static final double magazineSpeed = 0.7;
        public static final double feederSpeed = 1.0;
        public static final double elevatorSpeed = 0.2;

        public static final double rampSpeed = 0.125;
        public static final double autoDriveRampSpeed = 1.0;
    }
    public static final class ShooterConstants
    {
        public static final int kSlotIdx = 0;
	    public static final int kPIDLoopIdx = 0;
        public static final int kTimeoutMs = 30;

        //0.2, 0.00001, 8, 1023.0/21264,  100,  1.00
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kF = 1023.0/21404.0;
    }
    public static final class ElevatorConstants
    {
        public static final double elevatorHookSpeed = 0.3;
        public static final double elevatorClimbSpeed = 0.3;
    }
}
