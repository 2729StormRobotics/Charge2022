// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class HangerConstants {
        // These are all temporary fill-in values

        // Gearing for calculations, given in output turns per motor turn
        private static final double kGearing = 0.0;
        // Diameter of the pulley in inches
        private static final double kPulleyDiameter = 0.0;
        // Circumference of the pulley in inches
        public static final double kPulleyCircumference = Math.PI * kPulleyDiameter;

        // Distance per rotation determined by multiplying pulley circumference by the gearing
        public static final double kDistancePerRotation = kPulleyCircumference * kGearing;
        // Speed per rotation determined by dividing distance per rotation by 60 seconds
        public static final double kSpeedPerRotation = kDistancePerRotation / 60;


        // Port number for the left hanger motor
        public static final int kHangerMotorLeftPort = 1;
        //Port number for the right hanger motor
        public static final int kHangerMotorRightPort = 4;
        // Boolean to track whether the left motor needs to be inverted
        public static final boolean kMotorLeftInverted = false;
        // Boolean to track whether the right motor needs to be inverted
        public static final boolean kMotorRightInverted = false;



        // Constant speed at which the hanger motor will rotate to extend the elevator
        public static final double kClimbUpSpeed = 0.0;
        // Constant speed at which the hanger motor will rotate to retract the elevator
        public static final double kClimbDownSpeed = 0.0;
        // The maximum extension of the elevator
        public static final double kMaxHeight = 0.0;

        // Port numberfor the pawl piston
        public static final int kPawlPistonChannel = 0;
        // Booleans for when the piston is enabled or disabled to make the code more readable
        public static final boolean kPawlPistonEnabled = false;
        public static final boolean kPawlPistonDisabled = true;

    }


    public static final class IntakeConstants {
        /*
        real nums needed for:
        motor port
        motor Intake/Reject speed
        extend/retract
        */
        public static final int kIntakeMotorPort = 5;
        public static final double kIntakeMotorSpeed = 1;
        public static final int kIntakeMotorStopSpeed = 0;
        public static final int kEjectMotorSpeed = -1;

        public static final int kIntakeExtendChannel = 4;
        public static final int kIntakeRetractChannel = 5;
        public static final Value kIntakeExtendValue = Value.kForward;
        public static final Value kIntakeRetractValue = Value.kReverse;
       

    }


    public static final class IndexConstants {
        /*
        real nums needed for:
        motor ports
        beam braker ports
        motor speeds
        */

        public static final int kLowerIndexMotorPort = 10;
        public static final int kUpperIndexMotorPort = 11;
        public static final int kLowerIndexBeamBrakerPort = 1;
        public static final int kUpperIndexBeamBrakerPort = 3;
        public static final double kLowerIndexMotorSpeed = -0.3;
        public static final double kUpperIndexMotorSpeed = -0.3;

        public static final int kIndexMotorStopSpeed = 0;
        public static final int kEjectIndexMotorSpeed = -1;

    }


    public static final class ShooterConstants{

        public static final int kLeftMotorPort = 12;
        public static final int kRightMotorPort = 13;

        public static final double kHubShotMotorSpeed = 0;
        public static final double kCloseLaunchPadMotorSpeed = -1000;
        public static final double kFarLaunchPadMotorSpeed = 0;

        public static final int kBottomExtendedChannel = 4;
        public static final int kBottomRetractedChannel = 5;
        
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
        public static final String kShooterShuffleboardTabName = "Shooter";
    }

    public static final class VisionConstants{
        public static final double klimelightHeight = 0;
        public static final double klimelightAngle = 0;

        public static final double kUpperTargetHeight = 104; // 8'8"

        public static final int kdefaultPipeline = 0;
        
        public static final double khorizontalRange = 1.5; // How far off from the crosshair the target can be to be centered on the x-axis

        // Alignment constants for the limelight
        public static final double kAutoAlignP = 0.0;
        public static final double kAutoAlignI = 0.0;
        public static final double kAutoAlignD = 0.0;
        public static final double kAutoAlignTolerance = 1.0;
        public static final double kAutoAlignSpeedTolerance = 1.0;
    }

    public static final class DriveConstants {
        // Get Values For Everything:
        // Drive ports, current limit, gear ratio, feedforward values, pid values (for both), navX Port, 

        // Drive Motor Ports

        public static final int kLeftLeaderMotorPort = 8;
        // public static final int kLeftFollowerMotorPort = 2;
        public static final int kRightLeaderMotorPort = 6;
        // public static final int kRightFollowerMotorPort = 3;


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
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;

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
    
    public static final class ButtonBindingConstants {
        // public static final int kDriverControllerPort = 0;
        // public static final int kOperatorControllerPort = 1;
    }


}
