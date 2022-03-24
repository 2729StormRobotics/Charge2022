// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.VisionChooseShooterSetpoint;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChooseSetpointThenShoot extends SequentialCommandGroup {
  /** Creates a new ChooseSetpointThenShoot. */
  public ChooseSetpointThenShoot(Vision vision, Shooter shooter, Index index) {
    addCommands(new VisionChooseShooterSetpoint(vision, shooter), new Shoot(shooter, index));
  }
}
