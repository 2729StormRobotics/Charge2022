// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShooterPrep extends InstantCommand {
  private final Shooter m_shooter;
  private final double m_setpoint;
  private final boolean m_extendHood;
  public ShooterPrep(double setpoint, boolean extendHood, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_setpoint = setpoint;
    m_extendHood = extendHood;
    m_shooter = shooter;  

    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setSetpoint(m_setpoint);
    if (m_extendHood) {
      m_shooter.extendPistons();
    } else {
      m_shooter.retractPistons();
    }
  }
}
