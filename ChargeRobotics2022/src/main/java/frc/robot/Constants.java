// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        private static final double kPulleyDiameter = 0.0; // Inches
        public static final double kPulleyCircumference = Math.PI * kPulleyDiameter;

        public static final double kDistancePerRotation = kPulleyCircumference * kGearing;
        public static final double kSpeedPerRotation = kDistancePerRotation / 60;

        public static final int kHangerMotorPort = 0;
        public static final boolean kMotorInverted = false;
        public static final double kClimbUpSpeed = 0.0;
        public static final double kClimbDownSpeed = 0.0;
        public static final double kMaxHeight = 0.0;
        public static final int kPawlPistonChannel = 0;
        public static final boolean kPawlPistonEnabled = false;
        public static final boolean kPawlPistonDisabled = true;

    }

}
