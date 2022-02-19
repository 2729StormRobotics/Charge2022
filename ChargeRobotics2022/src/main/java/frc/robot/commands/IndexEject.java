// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;



public class IndexEject extends CommandBase {
  private final Index m_index;
  /** Creates a new IndexEject. */
  public IndexEject(Index subsystem) {
     m_index = subsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_index);
  }

 

  // Called when the command is initially scheduled.
  //reverses index motors
  @Override
  public void initialize() {
   m_index.ejectIndex();
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  //stops both upper and lower motors
  @Override
  public void end(boolean interrupted) {
    m_index.stopIndexMotors();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}