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

  public DrivePointTurn(double setAngle, NavX navX, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(kLeftP, kLeftI, kLeftD),
        // This should return the measurement
        () -> navX.getAngleNavX(),
        // This should return the setpoint (can also be a constant)
        () -> setAngle,
        // This uses the output
        output -> {
          // Use the output here

          // if the desired angle is greater than the current angle then turn right
          //if the desired angle is less than the current angle then turn left
          // *may need to change the direction*
          if (setAngle > navX.getAngleNavX()) {
            drivetrain.tankDrive(output, -output, true);
          }
          else if (setAngle < navX.getAngleNavX()) {
            drivetrain.tankDrive(-output, output, true);
          }
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
