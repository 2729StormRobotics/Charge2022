// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;
import static frc.robot.Constants.DriveConstants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DrivePointTurn extends PIDCommand {
  /** Creates a new DrivePointTurn. */


  public DrivePointTurn(double deltaAngle, NavX navX, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(kTurnP, kTurnI, kTurnD),
        // This should return the measurement
        () -> navX.getAngleNavX(),
        // This should return the setpoint (can also be a constant)
        // adds the change in angle to the current angle and sets so it is between -180 and 180
        () -> (((navX.getAngleNavX() + deltaAngle)) % 360) - 180,
        // This uses the output
        output -> {
          // Use the output here
            drivetrain.arcadeDrive(0, output, true);

        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(navX, drivetrain);
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(kAngleTolerance, kTurnSpeedTolerance);
    getController().enableContinuousInput(-180, 180);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
