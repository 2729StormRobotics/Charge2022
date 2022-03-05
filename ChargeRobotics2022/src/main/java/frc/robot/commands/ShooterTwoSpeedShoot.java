// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShooterTwoSpeedShoot extends CommandBase {

  final double m_setpoint;
  final double m_actualSpeed;
  final Shooter m_shooter;

  /** Creates a new ShooterTwoSpeedShoot. */
  public ShooterTwoSpeedShoot(double setpoint, double actualSpeed, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_setpoint = setpoint;
    m_actualSpeed = actualSpeed;
    m_shooter = shooter;

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setSetpoint(m_setpoint);
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
    return (Math.abs(m_shooter.getFlywheelSpeed() - m_actualSpeed) < Constants.ShooterConstants.kVelocityTolerance);
  }
}
