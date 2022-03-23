// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.PointTurnGyroPID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.commands.ShooterPrep;
import static frc.robot.Constants.*;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoWallShot extends SequentialCommandGroup {
  /** Creates a new ShootAutomatically. */
  public AutoWallShot(Shooter shooter, Index index, Drivetrain drivetrain, Intake intake, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
     addCommands(new DriveDistanceAndIntake(drivetrain, intake, index, -0.3, 40),
    new PointTurnGyroPID(vision.getXOffset(), drivetrain), new ShooterPrep(ShooterConstants.kWallShotSetpoint, ShooterConstants.kHubShotExtended, shooter),
    new Shoot(shooter, index));
    //new TwoSpeedShoot(ShooterConstants.kWallShotSetpoint, ShooterConstants.kWallShotActualSpeed, shooter, index));
  }
}
