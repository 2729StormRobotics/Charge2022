// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.IndexLowerInUntilBoth;
import frc.robot.commands.IndexUpperIn;
import frc.robot.commands.IntakeRun;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAndIndex extends ParallelCommandGroup {

  private final Intake m_intake;
  private final Index m_index;

  /** Creates a new IntakeAndIndex. */
  public IntakeAndIndex(Intake intake, Index index) {

    m_intake = intake;
    m_index = index;

    addCommands(new IntakeRun(m_intake), new IndexUpperIn(m_index), new IndexLowerInUntilBoth(m_index));
  }
}
