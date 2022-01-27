// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
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
        public static final int kIntakeMotorStopSpeed = 0;
        public static final int kEjectMotorSpeed = -1;

        public static final int kIntakeExtendChannel = 0;
        public static final int kIntakeRetractChannel = 0;
        public static final Value kIntakeExtendValue = Value.kForward;
        public static final Value kIntakeRetractValue = Value.kReverse;
       

    }


    public static final class ShooterConstants{

        public static final int kMotorPort = 0;
        public static final double kMotorSpeed = 0;

        public static final int kBottomExtendedChannel = 0;
        public static final int kBottomRetractedChannel = 0;
        public static final int kSideExtendedChannel = 0;      
        public static final int kSideRetractedChannel = 0;
        
        public static final Value kPistonExtendedValue = Value.kForward;
        public static final Value kPistonRetractedValue = Value.kReverse;
        public static final PneumaticsModuleType kPistonModuleType = PneumaticsModuleType.REVPH;

        public static final double kVelocityConversion = 0;

        public static final double kHubShotSpeed = 0;
        public static final double kMiddleShotSpeed = 0;
        public static final double kCloseLaunchPadShotSpeed = 0;
        public static final double kFarLaunchPadShotSpeed = 0;

        // Used to calculate the feedforward
        public static final double kS = 0; // static constant
        public static final double kV = 0; // velocity constant
        public static final double kA = 0; // accelaration constant
     
        // PID values to maintain speed 
        public static final double kP = 0; // proportional constant
        public static final double kI = 0; // integral constant 
        public static final double kD = 0; // derivative constant
        public static final double kIz = 0; // integral zone
        public static final double kFF = 0; // feed forward constant
        public static final double kMinOutput = 0;
        public static final double kMaxOutput = 0;
    }

    public static final class DriveConstants {
        // Get Values For Everything:
        // Drive ports, current limit, gear ratio, feedforward values, pid values

        // Drive Motor Ports
        public static final int kLeftLeaderMotorPort = 0;
        public static final int kLeftFollowerMotorPort = 0;
        public static final int kRightLeaderMotorPort = 0;
        public static final int kRightFollowerMotorPort = 0;

        // Set If Drive Motors are Reversed
        public static final boolean kLeftLeaderMotorReversedDefault = true;
        public static final boolean kLeftFollowerMotorReversedDefault = false;
        public static final boolean kRightLeaderMotorReversedDefault = true;
        public static final boolean kRightFollowerMotorReversedDefault = false;

        public static final int kCurrentLimit = 0;


        // Encoder calculations
        public static final double kDriveWheelDiameterInches = 6.0;
        public static final double kGear = 1; // NEED GEAR RATIO!!!!
    
        
        // Calculates the distace per pulse by dividing the circumference by the pulses per revolution
        public static final double kDriveDistancePerRev = Math.PI * kDriveWheelDiameterInches * kGear;
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
        public static final double kLeftP = 0;
        public static final double kLeftI = 0;
        public static final double kLeftD = 0;

        public static final double kRightP = 0;
        public static final double kRightI = 0;
        public static final double kRightD = 0;

        // Tolerance and Maximums in inches and seconds
        public static final double kPositionTolerance = 1.0; // Placeholder
        public static final double kVelocityTolerance = 5.0; //Placeholder

    }
    
    


    

}
