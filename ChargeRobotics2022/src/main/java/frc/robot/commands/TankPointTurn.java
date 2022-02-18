// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.Constants.DriveConstants.*;



public class TankPointTurn extends CommandBase {
  /** Creates a new TankPointTurn. */

  private final Drivetrain m_drivetrain;
  private final double m_angle;
  private final double m_speed;

/**
 * Creates a new TankPointTurn
 * @param drivetrain
 * @param speed
 * @param angle the angle to turn in degrees, positive is counter-clockwise
 */
  public TankPointTurn(Drivetrain drivetrain, double speed, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_drivetrain = drivetrain;
    m_speed = speed;
    m_angle = angle;

    addRequirements(m_drivetrain);


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_drivetrain.encoderInit();
    m_drivetrain.resetAllEncoders();


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    m_drivetrain.tankDrive(m_speed * Math.signum(m_angle) * -1, m_speed * Math.signum(m_angle), false);   

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   
    if ( m_angle * m_speed > 0  ) {
      return  m_drivetrain.getRightDistance() >= (m_angle * kTurnAngleToInches);
    }
    else {
      return m_drivetrain.getRightDistance() <= -(m_angle * kTurnAngleToInches);
    }
   
  } 
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    