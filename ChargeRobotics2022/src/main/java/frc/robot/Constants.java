// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

        // Port number for the hanger motor
        public static final int kHangerMotorPort = 0;
        // Boolean to track whether the motor needs to be inverted
        public static final boolean kMotorInverted = false;

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

    public static final class DriveConstants {

        public static final int kLeftLeaderMotorPort = 0;
        public static final int kLeftFollowerMotorPort = 0;
        public static final int kRightLeaderMotorPort = 0;
        public static final int kRightFollowerMotorPort = 0;

        public static final boolean kLeftLeaderMotorReversedDefault = true;
        public static final boolean kLeftFollowerMotorReversedDefault = false;
        public static final boolean kRightLeaderMotorReversedDefault = true;
        public static final boolean kRightFollowerMotorReversedDefault = false;

        public static final int kCurrentLimit = 0;



    }
    
    

    


}
