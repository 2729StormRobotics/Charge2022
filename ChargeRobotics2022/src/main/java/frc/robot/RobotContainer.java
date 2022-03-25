// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.logging.Handler;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commandgroups.AutoHubDumpAndDriveBack;
import frc.robot.commandgroups.AutoTarmacShot;
import frc.robot.commandgroups.AutoTwoBallWallShot;
import frc.robot.commandgroups.DriveDistanceAndIntake;
import frc.robot.commandgroups.AutoDriveBackwards;
import frc.robot.commandgroups.IntakeAndIndex;
import frc.robot.commandgroups.Shoot;
import frc.robot.commandgroups.ShootStop;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveManuallyArcade;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.HangManually;
import frc.robot.commands.IndexOut;
import frc.robot.commands.IndexEject;
import frc.robot.commands.IntakeEject;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.commands.PointTurnUsingLimelight;
import frc.robot.commands.ShooterManuallySetExtendedAngle;
import frc.robot.commands.ShooterManuallySetRetractedAngle;
import frc.robot.commands.ShooterPrep;
import frc.robot.commands.ShooterTestMode;
import frc.robot.commands.VisionAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.commands.PointTurnEncoderTank;
import frc.robot.commands.PointTurnGyroPID;
import frc.robot.commands.PointTurnGyroTank;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import static frc.robot.Constants.*;
import static frc.robot.Constants.DriveConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final XboxController m_driver = new XboxController(DriveConstants.kDriverControllerPort);
  private final XboxController m_operator = new XboxController(DriveConstants.kOperatorControllerPort);
  private final XboxController m_technician = new XboxController(2);

  private final Drivetrain m_drivetrain;
  private final Hanger m_hanger;
  private final Index m_index;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Vision m_vision;
  // private final Compressor m_testCompressor;
  private final double straightSpeedFactor = 0.6;
  private final double turnSpeedFactor = 0.5;
  private final double straightBoostSpeedFactor = 1.0;
  private final double turnBoostSpeedFactor = 0.7;

  private final SendableChooser<Command> m_autoChooser;
  private final SendableChooser<Command> m_testChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    m_drivetrain = new Drivetrain();
    m_hanger = new Hanger();
    m_index = new Index();
    m_intake = new Intake();
    m_vision = new Vision();
    m_shooter = new Shooter();

    m_autoChooser = new SendableChooser<>();
    SmartDashboard.putData("Autonomous Selector", m_autoChooser);
    m_autoChooser.setDefaultOption("Do Nothing", new InstantCommand());
    m_autoChooser.addOption("Hub Dump", new AutoHubDumpAndDriveBack(m_shooter, m_index, m_drivetrain, m_intake, m_vision));
    m_autoChooser.addOption("Wall Shot", new AutoTwoBallWallShot(m_shooter, m_index, m_drivetrain, m_intake, m_vision));
    m_autoChooser.addOption("Just Drive Backwards", new AutoDriveBackwards(m_drivetrain));
    m_autoChooser.addOption("Tarmac Shot", new AutoTarmacShot(m_drivetrain, m_shooter, m_intake, m_index, m_vision));

    m_testChooser = new SendableChooser<>();
    SmartDashboard.putData("Test Selector", m_testChooser);
    SmartDashboard.putBoolean("Test Mode", false);
    m_autoChooser.setDefaultOption("Test Mode", new ShooterTestMode(m_shooter, true));

    m_drivetrain.setDefaultCommand(
        new DriveManuallyArcade(() -> (m_driver.getLeftY() * 0.85), () -> (-m_driver.getRightX() * 0.7), m_drivetrain));


    SmartDashboard.putNumber("Auto Delay", 0);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver buttons
    new JoystickButton(m_driver, Button.kLeftBumper.value).whileHeld(
        new DriveManuallyArcade(
            () -> (m_driver.getLeftY() * straightSpeedFactor),
            () -> (-m_driver.getRightX() * turnSpeedFactor), m_drivetrain));
    new JoystickButton(m_driver, Button.kRightBumper.value).whileHeld(
        new DriveManuallyArcade(
            () -> (m_driver.getLeftY() * straightBoostSpeedFactor),
            () -> (-m_driver.getRightX() * turnBoostSpeedFactor), m_drivetrain));

    // Shooter Prep Buttons
    // Operator A: Wall Shot
    new JoystickButton(m_operator, Button.kA.value).whenPressed(
        new ShooterPrep(ShooterConstants.kWallShotSetpoint, ShooterConstants.kWallShotExtendHood, m_shooter));
    // Operator Y: Hub Dump
    new JoystickButton(m_operator, Button.kY.value).whenPressed(
        new ShooterPrep(ShooterConstants.kHubShotSetpoint, ShooterConstants.kHubShotExtended, m_shooter));
    // Operator X: Close Launchpad
    new JoystickButton(m_operator, Button.kX.value).whenPressed(
        new ShooterPrep(ShooterConstants.kCloseLaunchpadShotSetpoint, ShooterConstants.kCloseLaunchpadShotExtended,
            m_shooter));
    // Operator B: Far Launchpad
    new JoystickButton(m_operator, Button.kB.value).whenPressed(
        new ShooterPrep(ShooterConstants.kFarLaunchpadShotSetpoint, ShooterConstants.kFarLaunchpadShotExtended,
            m_shooter));

    // Operator Right Trigger: Intake & Index (In)
    new Trigger(() -> (m_operator.getRightTriggerAxis() > 0.01))
        .whileActiveContinuous(new IntakeAndIndex(m_intake, m_index));
    // Operator Left Trigger: Eject (Out)
    new Trigger(() -> (m_operator.getLeftTriggerAxis() > 0.01))
        .whileActiveContinuous(new IntakeEject(m_intake));
    // Operator Start: Extend Intake Pistons
    new JoystickButton(m_operator, Button.kStart.value)
        .whenPressed(new IntakeExtend(m_intake));
    // Operator Back: Retract Intake Pistons
    new JoystickButton(m_operator, Button.kBack.value).whenPressed(new IntakeRetract(m_intake));

    // Driver Y: Hanger
    new JoystickButton(m_driver, Button.kY.value)
        .whileHeld(new HangManually(m_hanger, Constants.HangerConstants.kClimbSpeed));

    // Driver X: Hanger
    new JoystickButton(m_driver, Button.kX.value)
        .whileHeld(new HangManually(m_hanger, Constants.HangerConstants.kReverseClimbSpeed));

    // Operator Right Bumper: Shoot
    new JoystickButton(m_operator, Button.kRightBumper.value).whenPressed(new Shoot(m_shooter, m_index));
    new JoystickButton(m_operator, Button.kRightBumper.value).whenPressed(new Shoot(m_shooter, m_index));
    new JoystickButton(m_operator, Button.kRightBumper.value).whenReleased(new ShootStop(m_shooter, m_index));
   
    // Operator Left Bumper: Index Eject
    new JoystickButton(m_operator, Button.kLeftBumper.value).whileHeld(new IndexEject(m_index));
    
    // new JoystickButton(m_operator, Button.kRightBumper.value)
    //     .whenPressed(new InstantCommand(m_shooter::gentleStop, m_shooter));
    
    new JoystickButton(m_driver, Button.kA.value).whenPressed(new VisionAlign(m_vision, m_drivetrain));
    new JoystickButton(m_driver, Button.kA.value).whenReleased(new InstantCommand(m_drivetrain::stopDrive, m_drivetrain));
    
    // SmartDashboard.putNumber("VisionP", 0.0075);
    // SmartDashboard.putNumber("VisionD", 0);
    // SmartDashboard.putNumber("VisionI", 0);
    new JoystickButton(m_driver, Button.kA.value)
        .whenReleased(new InstantCommand(m_drivetrain::stopDrive, m_drivetrain));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {

    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(new WaitCommand(SmartDashboard.getNumber("Auto Delay", 0)), m_autoChooser.getSelected());

  }

}
