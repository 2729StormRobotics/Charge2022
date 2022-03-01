// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.DriveConstants.*;

public class PointTurnUsingLimelight extends CommandBase {
  /** Creates a new PointTurnUsingLimelight. */
  private final Vision m_vision;
  private final Drivetrain m_drivetrain;
  private final double m_speed;
  public double m_angle;

  public PointTurnUsingLimelight(double speed, Vision vision, Drivetrain drivetrain) {
    m_vision = vision;
    m_drivetrain = drivetrain;
    m_speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_vision);
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.encoderInit();
    m_drivetrain.resetAllEncoders();
    
    m_angle = m_vision.getXOffset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_vision.isTargetDetected()){
      m_drivetrain.arcadeDrive(0, m_speed, false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_vision.getXOffset() > 0) {
      return  m_drivetrain.getRightDistance() >= (m_angle * kTurnAngleToInches);
    }
    else {
      return m_drivetrain.getRightDistance() <= -(m_angle * kTurnAngleToInches);
    }
  }
}
