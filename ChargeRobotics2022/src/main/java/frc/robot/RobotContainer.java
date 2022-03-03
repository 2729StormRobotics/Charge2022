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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commandgroups.IntakeAndIndex;
import frc.robot.commands.DriveManuallyTank;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.HangManually;
// import frc.robot.commands.HangStop;
import frc.robot.commands.IndexLowerIn;
import frc.robot.commands.IndexOut;
import frc.robot.commands.IndexUpperIn;
import frc.robot.commands.IntakeEject;
import frc.robot.commands.PointTurnUsingLimelight;
import frc.robot.commands.ShooterHubShot;
import frc.robot.commands.ShooterManuallySetExtendedAngle;
import frc.robot.commands.ShooterManuallySetRetractedAngle;
import frc.robot.commands.ShooterPrepHubShot;
import frc.robot.commands.ShooterShoot;
import frc.robot.commands.VisionAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.commands.PointTurnEncoderTank;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


import static frc.robot.Constants.*;
import static frc.robot.Constants.DriveConstants.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final XboxController m_driver = new XboxController(DriveConstants.kDriverControllerPort);
  private final XboxController m_operator = new XboxController(DriveConstants.kOperatorControllerPort);

  private final Drivetrain m_drivetrain;
  private final Hanger m_hanger;
  private final Index m_index;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Vision m_vision;
  // private final Compressor m_testCompressor;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_drivetrain = new Drivetrain();
    m_hanger = new Hanger();
    m_index = new Index();
    m_intake = new Intake();
    m_vision = new Vision();
    m_shooter = new Shooter();

    m_drivetrain.setDefaultCommand(
        new DriveManuallyTank(() -> m_driver.getLeftY(), () -> m_driver.getRightY(), m_drivetrain));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Shooter Prep Buttons - Launch Prep Command?
    //Thinking abt only using one prep command bc b x and y buttons will finalize the speed anyway
    // new JoystickButton(m_operator, Button.kLeftStick.value).whenPressed();
    new JoystickButton(m_operator, Button.kRightStick.value).whenPressed(new ShooterPrepHubShot(m_shooter));
 
 
    //Shooter Buttons
    new JoystickButton(m_operator, Button.kA.value).whenPressed(new ShooterHubShot(m_shooter));
    new JoystickButton(m_operator, Button.kRightStick.value).whenPressed(new ShooterPrepHubShot(m_shooter));
    new JoystickButton(m_operator, Button.kB.value).whenPressed(new ShooterShoot(m_shooter, Constants.ShooterConstants.kCloseLaunchPadMotorSpeed));
    new JoystickButton(m_operator, Button.kX.value).whenPressed(new ShooterShoot(m_shooter, Constants.ShooterConstants.kFarLaunchPadMotorSpeed));
    new JoystickButton(m_operator, Button.kY.value).whenPressed(new ShooterShoot(m_shooter, Constants.ShooterConstants.kWallShotMotorSpeed));
    
    new JoystickButton(m_operator, Button.kRightBumper.value).whenPressed(new VisionAlign(m_vision, m_drivetrain));
   
    //Intake Buttons
    new Trigger(() -> (m_operator.getRightTriggerAxis() > 0.01)).whenActive(new IntakeAndIndex(m_intake, m_index));
    new Trigger(() -> (m_operator.getLeftTriggerAxis() > 0.01)).whenActive(new IntakeEject(m_intake));

    //Hang Buttons
    new JoystickButton(m_driver, Button.kA.value).whileHeld(new HangManually(m_hanger, Constants.HangerConstants.kClimbUpSpeed));
    // new JoystickButton(m_driver, Button.kB.value).whenPressed(new HangStop(m_hanger));

    
    new JoystickButton(m_operator, Button.kLeftBumper.value).whenPressed(new PointTurnEncoderTank(m_drivetrain, kTurnSpeed, -m_vision.getXOffset()));
  }
    
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    
    // An ExampleCommand will run in autonomous
    return new ExampleCommand(new ExampleSubsystem());

  }

}
