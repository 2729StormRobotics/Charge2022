// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IndexRunUpper;
import frc.robot.commands.ShooterFarLaunchPadShot;
import frc.robot.commands.IndexRunLower;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IndexThenShootFarLaunchPadShot extends SequentialCommandGroup {

  private final Index m_index;
  private final Shooter m_shooter;

  /** Creates a new IndexThenShootFarLaunchPadShot. */
  //revs up the motor to the speed required for a far launch pad shot
  //if the index contains cargo in the upper position, feed it to the shooter
  //rev up the motor again
  //if the index contains cargo in the lower position, move it to the upper and then feed to shooter
  public IndexThenShootFarLaunchPadShot(Index index, Shooter shooter) {
    m_index = index;
    m_shooter = shooter;

    if (!m_index.getUpperBeamBrakerStatus()) {
      addCommands(new ShooterFarLaunchPadShot(m_shooter), new IndexRunUpper(m_index));
    }
    
    if (!m_index.getLowerBeamBrakerStatus()) {
      addCommands(new ShooterFarLaunchPadShot(m_shooter), new IndexRunLower(m_index), new IndexRunUpper(m_index));
    }
  }

  @Override
  public void end(boolean interrupted) {}

}
