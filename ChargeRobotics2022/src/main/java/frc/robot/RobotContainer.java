// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveManually;
import frc.robot.commands.DrivePointTurn;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.TankDriveDistance;
import frc.robot.commands.TankPointTurn;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.NavX;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.Constants.DriveConstants.*;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

  private final Drivetrain m_drivetrain;
  private final NavX m_navX;
  
  private final XboxController m_driver = new XboxController(kDriverControllerPort);
  private final XboxController m_operator = new XboxController(kOperatorControllerPort);
  
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_drivetrain = new Drivetrain(); 
    m_navX = new NavX();

    //Drive Manually for this only uses triggerdrive so the first two parameters are needed the other two are not

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
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   
   * @return the command to run in autonomous
   */



  public Command getAutonomousCommand() {
    
    // An ExampleCommand will run in autonomous

    return new DrivePointTurn(90, m_navX, m_drivetrain);
  }

}
