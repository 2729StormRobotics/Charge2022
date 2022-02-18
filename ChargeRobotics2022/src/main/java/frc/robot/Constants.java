// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class IntakeConstants {
        //real nums needed for:
        // motor port
        // motor Intake/Reject speed
        // extend/retract
        public static final int kIntakeMotorPort = 4;
        public static final double kIntakeMotorSpeed = 0.56;
        public static final int kEjectMotorSpeed = -1;

        public static final int kIntakeExtendChannel = 0;
        public static final int kIntakeRetractChannel = 0;
        public static final Value kIntakeExtendValue = Value.kForward;
        public static final Value kIntakeRetractValue = Value.kReverse;
       

    }

    public static final class DriveConstants {
        // Get Values For Everything:
        // Drive ports, current limit, gear ratio, feedforward values, pid values (for both), navX Port, 

        // Drive Motor Ports
        public static final int kLeftLeaderMotorPort = 9;
        public static final int kLeftFollowerMotorPort = 0;
        public static final int kRightLeaderMotorPort = 1;
        public static final int kRightFollowerMotorPort = 0;

        // Set If Drive Motors are Reversed
        public static final boolean kLeftLeaderMotorReversedDefault = false;
        public static final boolean kLeftFollowerMotorReversedDefault = false;
        public static final boolean kRightLeaderMotorReversedDefault = true;
        public static final boolean kRightFollowerMotorReversedDefault = true;

        public static final int kCurrentLimit = 0; // NEED TO SET


        // Encoder calculations
        public static final double kDriveWheelDiameterInches = 6.0;
        public static final double kGear = 10.75; 
        //Testbot Gear ratio- 10.75 : 1
        //Real Robot Gear Ratio- 12 : 1
    
        
        // Calculates the distace per pulse by dividing the circumference by the pulses per revolution
        public static final double kDriveDistancePerRev = Math.PI * kDriveWheelDiameterInches / kGear;
        // Encoders are in RPM so this converts to inches/sec
        public static final double kDriveSpeedPerRev = kDriveDistancePerRev / 60.0; 


        // Drive Distance Feedforward Values
        public static final double kLeftS = 0; // Position
        public static final double kLeftV = 0; // Velocity
        public static final double kLeftA = 0; // Acceleration

        public static final double kRightS = 0;
        public static final double kRightV = 0;
        public static final double kRightA = 0;

        // Drive Distance PID Values
        public static /*final*/ double kLeftP = .1;
        public static final double kLeftI = 0;
        public static final double kLeftD = 0;

        public static /*final*/ double kRightP = .1;
        public static final double kRightI = 0;
        public static final double kRightD = 0;

        // Drive Distance Tolerance and Maximums in inches and seconds
        public static final double kPositionTolerance = 1.0; // Placeholder
        public static final double kVelocityTolerance = 5.0; //Placeholder


        //NavX Port
        public static final int kNavXPort = 0; 

        //Point Turn PID Values
        public static final double kTurnP = .005;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;

        //Point Turn Tolerances in degrees and seconds
        public static final double kAngleTolerance = 1.0; //Placeholder
        public static final double kTurnSpeedTolerance = 1.0; //Placeholder

        public static final double kTurnAngleToInches = (22.75 * Math.PI) / 360;


        // Driver Controller Ports
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;

    }
    
    

    

}
