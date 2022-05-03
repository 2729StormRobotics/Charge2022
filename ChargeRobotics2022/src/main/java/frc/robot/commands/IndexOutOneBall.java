// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;

public class IndexOutOneBall extends CommandBase {
  /** Creates a new IndexOutOneBall. */
  private final Index m_index;

  // Keeps track of whether or not there has been an upper ball at some point.
  private boolean m_hadUpperBall = false;
  public IndexOutOneBall(Index index) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_index = index;
    addRequirements(m_index);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_hadUpperBall = m_index.hasUpperBall();  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_hadUpperBall) {
      // There was an upper ball at some point, so just push out until there is no longer an upper ball
      if (m_index.hasUpperBall()) {
        m_index.runLowerIndexMotor(-0.6);
        m_index.runUpperIndexMotor(-0.3);
      } else {
        // This actually means the command is finished
        m_index.stopIndexMotors();
      }
    } else {
      // There hasn't yet been an upper ball, so push up until there is an upper ball
      if (!m_index.hasUpperBall()) {
        m_index.runLowerIndexMotor(-0.6);
        m_index.runUpperIndexMotor(-0.3);
      } else {
        // This is the first time that Index#hasUpperBall is returning true
        m_index.stopIndexMotors();
        m_hadUpperBall = true;
      }
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_hadUpperBall && !m_index.hasUpperBall();
  }
}
