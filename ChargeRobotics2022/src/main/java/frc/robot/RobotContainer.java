// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.logging.Handler;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commandgroups.IntakeAndIndex;
import frc.robot.commands.DriveManually;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeEject;
import frc.robot.commands.ShooterCloseLaunchPadShot;
import frc.robot.commands.ShooterFarLaunchPadShot;
import frc.robot.commands.ShooterHubShot;
import frc.robot.commands.ShooterPrepHubShot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Constants.ButtonBindingConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XboxController m_driver = new XboxController(kDriverControllerPort);
  private final XboxController m_operator = new XboxController(kOperatorControllerPort);

  private final Drivetrain m_drivetrain;
  private final Hanger m_hanger;
  private final Index m_index;
  private final Intake m_intake;
  private final Shooter m_shooter;
  // private final Vision m_vision;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

        m_drivetrain = new Drivetrain();
        m_hanger = new Hanger();
        m_index = new Index();
        m_intake = new Intake();
        m_shooter = new Shooter();
        // m_vision = new Vision();


        m_drivetrain.setDefaultCommand(
            new DriveManually(() -> m_driver.getRightX(), () -> m_driver.getRightY(),
              () -> m_driver.getLeftY(), () -> m_driver.getLeftY(), m_drivetrain));

        
    
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
    new JoystickButton(m_operator, Button.kLeftStick.value).whenPressed(new ShooterCloseLaunchPadShot(m_shooter));
    new JoystickButton(m_operator, Button.kRightStick.value).whenPressed(new ShooterPrepHubShot(m_shooter));

   // new JoystickButton(m_operator, Button.kLeftkLeftBumper).whenPressed(new Flush);
  //  new JoystickButton(m_operator, Button.kA.value).whenPressed(new );
    new JoystickButton(m_operator, Button.kB.value).whenPressed(new ShooterCloseLaunchPadShot(m_shooter));
    new JoystickButton(m_operator, Button.kX.value).whenPressed(new ShooterFarLaunchPadShot(m_shooter));
    new JoystickButton(m_operator, Button.kY.value).whenPressed(new ShooterHubShot(m_shooter));

    new JoystickButton(m_operator, Button.kRightBumper.value).whenPressed(new IntakeEject(m_intake));
    new Trigger(() -> (m_driver.getLeftTriggerAxis() > 0.01)).whenActive(new IntakeAndIndex(m_intake, m_index));


    

  }
    
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new ExampleCommand(new ExampleSubsystem());
  }
}
