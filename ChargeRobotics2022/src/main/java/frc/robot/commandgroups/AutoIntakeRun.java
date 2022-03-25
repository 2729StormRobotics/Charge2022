// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.IntakeRun;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoIntakeRun extends ParallelDeadlineGroup {
  /** Creates a new AutoIntakeRun. */
  public AutoIntakeRun(Intake intake, Index index) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new WaitCommand(4));
    addCommands(new IntakeAndIndex(intake, index));
  }
}