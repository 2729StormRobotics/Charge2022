// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeRun extends CommandBase {
 private final Intake m_intake;

  public IntakeRun(Intake subsystem) {
    m_intake = subsystem;

    addRequirements(m_intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.stopIntake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  //Runs the Intake.
  @Override
  public void execute() {
    m_intake.runIntake();
  }

  // Called once the command ends or is interrupted.
  //Stops the intake.
  @Override
  public void end(boolean interrupted) {
    m_intake.stopIntake();
  }

  // Returns true when the command should end.
  //Command is finished.
  @Override
  public boolean isFinished() {
    return false;
  }
}
