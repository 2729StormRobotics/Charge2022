// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IndexLower;
import frc.robot.commands.IndexStop;
import frc.robot.commands.IndexUpper;
import frc.robot.commands.IntakeRun;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeThenIndex extends SequentialCommandGroup {

  private final Intake m_intake;
  private final Index m_index;

  private CommandBase indexCommand;

  /** Creates a new IntakeThenIndex. */

  public IntakeThenIndex(Intake intake, Index index) {

    m_intake = intake;
    m_index = index;
    // If the lower beam breaker is open, index to the lower position.
    if (index.getLowerBeamBrakerStatus()) {
      indexCommand = new IndexLower(m_index);
    }
    // If the upper beam breaker is open, index to the upper position.
    if (index.getUpperBeamBrakerStatus()) {
      indexCommand = new IndexUpper(m_index);
    }

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new IntakeRun(m_intake), indexCommand);
  }

@Override
public void end(boolean interrupted) {
  m_intake.stopIntake();
  m_index.stopIndexMotors();
}

}
