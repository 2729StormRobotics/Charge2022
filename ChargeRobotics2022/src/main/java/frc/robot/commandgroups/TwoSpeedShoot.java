// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IndexLowerOut;
import frc.robot.commands.IndexUpperOut;
import frc.robot.commands.ShooterTwoSpeedShoot;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoSpeedShoot extends SequentialCommandGroup {
  /** Creates a new TwoSpeedShoot. */
  public TwoSpeedShoot(double setpoint, double actualSpeed, Shooter shooter, Index index) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ShooterTwoSpeedShoot(setpoint, actualSpeed, shooter),
        new IndexUpperOut(index), new IndexLowerOut(index),
        new ShooterTwoSpeedShoot(setpoint, actualSpeed, shooter),
        new IndexUpperOut(index));
  }
}