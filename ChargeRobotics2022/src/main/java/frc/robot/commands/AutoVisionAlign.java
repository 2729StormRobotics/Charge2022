// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.VisionConstants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoVisionAlign extends PIDCommand {
  private final Vision m_vision;
  private final Drivetrain m_drivetrain;

  /** Creates a new VisionAlign. */
  public AutoVisionAlign(Vision vision, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(0.011, .025, 0),
        // This should return the measurement
        () -> vision.getXOffset(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
         // if (vision.isTargetDetected()){

            // double o = Math.signum(output) * MathUtil.clamp(Math.abs(output), 0.1, 0.5);

            // if (Math.abs(output) < 0.1) {
            //   output = Math.signum(output) * 0.1;
            // }

            drivetrain.arcadeDrive(0, output, false);

            System.out.println("Vision Loop: " + output);

            
          //}
        });

    m_vision = vision;
    m_drivetrain = drivetrain;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_vision);
    addRequirements(m_drivetrain);

    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(kAutoAlignTolerance, kAutoAlignSpeedTolerance);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }

  @Override
  public void execute() {
    super.execute();
    // getController().setP(SmartDashboard.getNumber("VisionP", 0));
    // getController().setI(SmartDashboard.getNumber("VisionI", 0));
    // getController().setD(SmartDashboard.getNumber("VisionD", 0));
  }
}
