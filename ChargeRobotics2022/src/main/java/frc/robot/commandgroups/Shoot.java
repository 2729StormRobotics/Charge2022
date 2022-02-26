// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IndexMoveUp;
import frc.robot.commands.IndexUpperOut;
import frc.robot.commands.ShooterSetSetpoint;
import frc.robot.commands.ShooterShoot;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter3;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {

  /** Creates a new Shoot. */
  public Shoot(Index index, Shooter3 shooter, double speed) {
    if (index.hasUpperBall() && index.hasLowerBall()) {
      addCommands(new ShooterShoot(shooter, speed), new IndexUpperOut(index), new ShooterShoot(shooter, speed), new IndexMoveUp(index), new IndexUpperOut(index), new ShooterSetSetpoint(shooter, 0));
    } else if (index.hasUpperBall()) {
      addCommands(new ShooterShoot(shooter, speed), new IndexUpperOut(index), new ShooterSetSetpoint(shooter, 0));
    } else if (index.hasLowerBall()) {
      addCommands(new ShooterShoot(shooter, speed), new IndexMoveUp(index), new IndexUpperOut(index), new ShooterSetSetpoint(shooter, 0));
    }
  }
}
