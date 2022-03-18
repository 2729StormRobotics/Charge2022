// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.Constants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoHubDump extends SequentialCommandGroup {
  /** Creates a new ShootAutomatically. */
  public AutoHubDump(Shooter shooter, Index index, Drivetrain drivetrain, Intake intake, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    // addCommands(//new DriveDistanceAndIntake(drivetrain, intake, index, -0.3, 50),
    //     new TwoSpeedShoot(ShooterConstants.kHubShotSetpoint, ShooterConstants.kHubShotActualSpeed, shooter, index),
    //     new DriveDistance(drivetrain, -0.3, -100)
    //     );
  }
}
