// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IndexThenShootCloseLaunchPadShot extends SequentialCommandGroup {

  private final Index m_index;
  private final Shooter m_shooter;

  /** Creates a new IndexThenShootCloseLaunchPadShot. */
  public IndexThenShootCloseLaunchPadShot(Index index, Shooter shooter) {

    m_index = index;
    m_shooter = shooter;
//ShooterCloseLaunchPadShot,ShooterFarLaunchPadShot,ShooterHubShot
    // addCommands(new ShooterCloseLaunchPadShot(m_shooter));

  }

  @Override
  public void end(boolean interrupted) {
      m_index.stopIndexMotors();
      m_shooter.stopMotor();
  }

}
