// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.AutoVisionAlign;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.commands.ShooterPrep;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTwoBallWallShot extends SequentialCommandGroup {
  /** Creates a new ShootAutomatically. */
  public AutoTwoBallWallShot(Shooter shooter, Index index, Drivetrain drivetrain, Intake intake, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new DriveDistance(drivetrain, -0.3, 7.5),
        new ShooterPrep(ShooterConstants.kAutoTarmacShotSetpoint, ShooterConstants.kHubShotExtended, shooter),
        new AutoShoot(shooter, index),
        new InstantCommand(shooter::disableLoop, shooter),
        new WaitCommand(1),
        new IntakeExtend(intake),
        new WaitCommand(1),
        new DriveDistanceAndIntake(drivetrain, intake, index, -0.2, 35), // CHANGE THE DISTANCE VALUE
        new WaitCommand(2),
        new IntakeRetract(intake),
        new WaitCommand(1),
        new DriveDistance(drivetrain, -0.3, 10), // CHANGE THE DISTANCE VALUE
        new WaitCommand(1),
        new AutoVisionAlign(vision, drivetrain),
        new WaitCommand(1),
        new ShooterPrep(ShooterConstants.kWallShotSetpoint, ShooterConstants.kHubShotExtended, shooter),
        new AutoShoot(shooter, index),
        new InstantCommand(shooter::disableLoop, shooter));
    // new TwoSpeedShoot(ShooterConstants.kWallShotSetpoint,
    // ShooterConstants.kWallShotActualSpeed, shooter, index));
  }
}
