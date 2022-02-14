// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.Constants.DriveConstants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveDistance extends PIDCommand {
  /** Creates a new DriveDistance. */

  public DriveDistance(double distance, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(kLeftP, kLeftI, kLeftD),
        // This should return the measurement
        () -> drivetrain.getAverageDistance(),
        // This should return the setpoint (can also be a constant)
        distance,
        // This uses the output
        output -> {
          // Use the output here
          // drivetrain.arcadeDrive(-output, 0, false);
          drivetrain.resetAllEncoders();

          System.out.println("initial");
          System.out.println("right dist:  " + drivetrain.getRightDistance());
          System.out.println("left dist:  " + drivetrain.getLeftDistance());
          System.out.println("avg dist:  " + drivetrain.getAverageDistance());
         

          drivetrain.tankDrive(output, output, true);
          // SmartDashboard.putNumber("output", output);
          
          
          System.out.println("output " + output);
          
          System.out.println("final");
          System.out.println("right dist:  " + drivetrain.getRightDistance());
          System.out.println("left dist:  " + drivetrain.getLeftDistance());
          System.out.println("avg dist:  " + drivetrain.getAverageDistance());
          
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(kPositionTolerance, kVelocityTolerance);

    System.out.println("pos err:  " + getController().getPositionError());
    System.out.println("vel err:  " + getController().getVelocityError());

    //drivetrain.resetAllEncoders();

  }


// Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // SmartDashboard.putNumber("poition error", getController().getPositionError());
    // SmartDashboard.putNumber("velocity error", getController().getVelocityError());

    return getController().atSetpoint();
  }
}
