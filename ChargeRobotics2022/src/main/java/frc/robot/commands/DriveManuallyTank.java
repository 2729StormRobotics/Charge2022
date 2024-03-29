// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveManuallyTank extends CommandBase {
  /** Creates a new TankDriveManually. */

  private final Drivetrain m_drivetrain;

  private final DoubleSupplier m_leftSpeed;
  private final DoubleSupplier m_rightSpeed;
  private  double m_currentSpeed = 0;

  public DriveManuallyTank(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed, Drivetrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = subsystem;
    m_leftSpeed = leftSpeed;
    m_rightSpeed = rightSpeed;
    
    addRequirements(m_drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.stopDrive();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.tankDrive(m_leftSpeed.getAsDouble() * 0.5, m_rightSpeed.getAsDouble() * 0.5, true);

    // sets the current speed to the average velocity 
    m_currentSpeed = m_drivetrain.getAverageVelocity();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_currentSpeed = 0;
    m_drivetrain.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
