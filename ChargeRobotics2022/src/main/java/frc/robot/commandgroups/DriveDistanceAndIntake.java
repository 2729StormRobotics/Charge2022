// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveDistanceAndIntake extends ParallelDeadlineGroup {
  /** Creates a new DriveDistanceAndIntake. */
  public DriveDistanceAndIntake(Drivetrain drivetrain, Intake intake, Index index, double speed, double distance) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new DriveDistance(drivetrain, speed, distance));
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new IntakeAndIndex(intake, index));
  }
}
