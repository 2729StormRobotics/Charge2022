// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class VisionChooseShooterSetpoint extends InstantCommand {

  private final Vision m_vision;
  private final Shooter m_shooter;

  public VisionChooseShooterSetpoint(Vision vision, Shooter shooter) {
    m_vision = vision;
    m_shooter = shooter;

    addRequirements(vision, shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double dist = m_vision.getHorizontalDistanceToUpperHub();

    // Start with Close Launchpad
    double setpoint = ShooterConstants.kCloseLaunchpadShotSetpoint;
    // This is how far we are from the tuned distance
    double diff = dist - ShooterConstants.kCloseLaunchpadDistance;

    // Figure out which tuned distance we are closest to
    if (Math.abs(dist - ShooterConstants.kFarLaunchpadDistance) < Math.abs(diff)){

      // For example, in this case we are closer to the far launchpad tuned distance than
      // the close launchpad tuned distance. 
      // Use the tuned speed for the far launchpad
      setpoint = ShooterConstants.kFarLaunchpadDistance;

      // This is how far we are from the currently selected shot.
      diff = dist - ShooterConstants.kFarLaunchpadShotSetpoint;

    } else if (Math.abs(dist - ShooterConstants.kWallDistance) < Math.abs(diff)) {

      setpoint = ShooterConstants.kWallShotSetpoint;
      diff = dist - ShooterConstants.kWallDistance;

    } else if (Math.abs(dist - ShooterConstants.kTarmacDistance) < Math.abs(diff)) {

      setpoint = ShooterConstants.kTarmacShotSetpoint;
      diff = dist - ShooterConstants.kTarmacDistance;

    }

    m_shooter.setSetpoint(setpoint);

    SmartDashboard.putNumber("Shot Pos. Offset", diff);
  }
}
