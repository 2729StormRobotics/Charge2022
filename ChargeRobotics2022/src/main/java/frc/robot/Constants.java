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
        /*
        real nums needed for:
        motor port
        motor Intake/Reject speed
        extend/retract
        */
        public static final int kIntakeMotorPort = 4;
        public static final double kIntakeMotorSpeed = 0.56;
        public static final int kIntakeMotorStopSpeed = 0;
        public static final int kEjectMotorSpeed = -1;

        public static final int kIntakeExtendChannel = 0;
        public static final int kIntakeRetractChannel = 0;
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
        public static final int kLowerIndexMotorPort = 0;
        public static final int kUpperIndexMotorPort = 0;
        public static final int kLowerIndexBeamBrakerPort = 0;
        public static final int kUpperIndexBeamBrakerPort = 0;
        public static final int kLowerIndexMotorSpeed = 0;
        public static final int kUpperIndexMotorSpeed = 0;
        public static final int kIndexMotorStopSpeed = 0;
        public static final int kEjectIndexMotorSpeed = -1;

    }

}
