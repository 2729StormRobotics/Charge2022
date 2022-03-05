// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

import static frc.robot.Constants.ShooterConstants.*;

public class ShooterShoot extends CommandBase {
  private final Shooter m_shooter;
  private final double m_motorSpeed;

  /** Creates a new ShooterShoot. */
  public ShooterShoot(Shooter subsystem, double motorSpeed) {
    m_shooter = subsystem;
    m_motorSpeed = motorSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.retractPistons();
    m_shooter.setSetpoint(m_motorSpeed); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_shooter.atSetpoint());
  }
}
