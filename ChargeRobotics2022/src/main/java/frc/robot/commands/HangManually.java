// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hanger;
import frc.robot.Constants.HangerConstants;

public class HangManually extends CommandBase {
  private final Hanger m_hanger;
  private final double m_speed;

  /** Creates a new HangManually. */
  public HangManually(Hanger subsystem, double speed) {
    m_hanger = subsystem;
    m_speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_hanger);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("HangManually initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // If the elevator is above its max height and has an upwards velocity, stop
    if (m_hanger.atMaxHeight() && -m_speed > 0) { // May need to adjust the sign on m_speed
      m_hanger.stopClimb();
      m_hanger.engagePawlPiston();
    } else if (m_hanger.atMinHeight() && -m_speed < 0){ // May need to adjust the sign on m_speed
      m_hanger.stopClimb();
      m_hanger.engagePawlPiston();
    } else if (Math.abs(m_hanger.getHeightLeft() - m_hanger.getHeightRight()) > kHeightDifferenceTolerance){

    }else {
      m_hanger.disengagePawlPiston();
      m_hanger.climb(m_speed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_hanger.stopClimb();
    m_hanger.engagePawlPiston();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
