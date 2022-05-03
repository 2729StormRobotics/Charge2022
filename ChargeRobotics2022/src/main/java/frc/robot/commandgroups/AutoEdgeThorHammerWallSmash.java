// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.AutoVisionAlign;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.commands.ShooterPrep;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoEdgeThorHammerWallSmash extends SequentialCommandGroup {
  /** Creates a new AutoEdgeThorHammerWallSmash. */
  public AutoEdgeThorHammerWallSmash(Shooter shooter, Index index, Drivetrain drivetrain, Intake intake, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveDistance(drivetrain, -0.3, 25),
      new WaitCommand(1),
      new IntakeExtend(intake),
      new AutoIntakeRun(intake, index),
      new WaitCommand(1),
      new IntakeRetract(intake),
      new WaitCommand(1),
      new DriveDistance(drivetrain, 0.3, 20),
      new AutoVisionAlign(vision, drivetrain),
      new ShooterPrep(ShooterConstants.kTarmacShotSetpoint, ShooterConstants.kWallShotExtendHood, shooter),
      new AutoShoot(shooter, index),
      new InstantCommand(shooter::disableLoop, shooter)); // VERIFY DISTANCE VALUE); );
  }
}
