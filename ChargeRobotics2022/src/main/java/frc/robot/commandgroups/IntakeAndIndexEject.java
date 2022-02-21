// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.IndexEject;
import frc.robot.commands.IntakeEject;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAndIndexEject extends ParallelCommandGroup {

  private final Intake m_intake;
  private final Index m_index;

  /** Creates a new IntakeAndIndexEject. */
  public IntakeAndIndexEject(Intake intake, Index index) {

    m_intake = intake;
    m_index = index;

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new IntakeEject(intake), new IndexEject(index));
  }

  @Override
  public void end(boolean interrupted) {
    m_intake.stopIntake();
    m_index.stopIndexMotors();
  }

}
