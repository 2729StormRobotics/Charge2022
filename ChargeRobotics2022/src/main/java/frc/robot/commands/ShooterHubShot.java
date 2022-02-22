// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

import static frc.robot.Constants.ShooterConstants.*;

public class ShooterHubShot extends CommandBase {
  private final Shooter m_shooter;
  /** Creates a new ShooterHubShot. */
  public ShooterHubShot(Shooter subsystem) {    
    m_shooter = subsystem;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.extendPistons();
    m_shooter.m_setpoint = kHubShotMotorSpeed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_shooter.m_encoder.getVelocity() >= m_shooter.m_setpoint){
      return true;
    }
    return false;
  }
}
