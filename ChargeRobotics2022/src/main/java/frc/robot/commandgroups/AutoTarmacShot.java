// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.ShooterPrep;
import frc.robot.commands.VisionAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTarmacShot extends SequentialCommandGroup {
  /** Creates a new AutoTarmacSho. */
  public AutoTarmacShot(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Vision vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
     new IntakeExtend(intake), 
     new DriveDistanceAndIntake(drivetrain, intake, index, -0.3, DriveConstants.kAutoTarmacDistance - vision.getHorizontalDistanceToUpperHub()), 
    new VisionAlign(vision, drivetrain), 
    new ShooterPrep(ShooterConstants.kAutoTarmacShotSetpoint, ShooterConstants.kAutoTarmacShotExtended, shooter),
    new Shoot(shooter, index));
  }
}
