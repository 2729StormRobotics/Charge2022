// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.DriveConstants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PointTurnEncoderPID extends PIDCommand {
  /** Creates a new PointTurnEncoderPID. */
  public PointTurnEncoderPID(double angle, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(kP, kI, kD),
        // This should return the measurement
        () -> drivetrain.getRightDistance(),
        // This should return the setpoint (can also be a constant)
        () -> angle * kTurnAngleToInches,
        // This uses the output
        output -> {
          // Use the output here
          drivetrain.arcadeDrive(0, output, false);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(kAngleTolerance, kTurnSpeedTolerance);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
