// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoVisionAlign;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.IntakeExtend;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import static frc.robot.Constants.*;
import frc.robot.commands.ShooterPrep;

/**
 * Starts against the hub, drives to the Wall
 */
public class AutoThorHammer extends SequentialCommandGroup {
  /** Creates a new ShootAutomatically. */
  public AutoThorHammer(Shooter shooter, Index index, Drivetrain drivetrain, Intake intake, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(//new DriveDistanceAndIntake(drivetrain, intake, index, -0.3, 50),
    // new ShooterPrep(ShooterConstants.kHubShotSetpoint, ShooterConstants.kHubShotExtended, shooter),
    // new AutoShoot(shooter, index), 
    new DriveDistance(drivetrain, -0.3, 60),
    new WaitCommand(1),
    new IntakeExtend(intake),
    new AutoIntakeRun(intake, index),
    new AutoVisionAlign(vision, drivetrain),
    new ShooterPrep(-2450, ShooterConstants.kWallShotExtendHood, shooter),
    new AutoShoot(shooter, index),
    new InstantCommand(shooter::disableLoop, shooter)); // VERIFY DISTANCE VALUE
  }
}
