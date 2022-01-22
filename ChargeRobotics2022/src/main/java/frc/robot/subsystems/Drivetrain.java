// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import static frc.robot.Constants.DriveConstants.*;

public class Drivetrain extends SubsystemBase {

  /** Creates a new Drivetrain. */

  // Declare Motor Variables
  private final CANSparkMax m_leftLeaderMotor;
  private final CANSparkMax m_rightLeaderMotor;
  private final CANSparkMax m_leftFollowerMotor;
  private final CANSparkMax m_rightFollowerMotor;

  // Declare Encoder Variables
  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  private final DifferentialDrive m_drive;



  public Drivetrain() {

    // Set Motor Ports and Motor Type
    m_leftLeaderMotor = new CANSparkMax(kLeftLeaderMotorPort, MotorType.kBrushless);
    m_rightLeaderMotor = new CANSparkMax(kRightLeaderMotorPort, MotorType.kBrushless);
    m_leftFollowerMotor = new CANSparkMax(kLeftFollowerMotorPort, MotorType.kBrushless);
    m_rightFollowerMotor = new CANSparkMax(kRightFollowerMotorPort, MotorType.kBrushless);

    // Initialize Motors
    motorInit(m_leftLeaderMotor, kLeftLeaderMotorReversedDefault);
    motorInit(m_rightLeaderMotor, kRightLeaderMotorReversedDefault);
    motorInit(m_leftFollowerMotor, kLeftFollowerMotorReversedDefault);
    motorInit(m_rightFollowerMotor, kRightFollowerMotorReversedDefault);

    // Set Motor Followers
    m_leftFollowerMotor.follow(m_leftLeaderMotor);
    m_rightFollowerMotor.follow(m_rightLeaderMotor);

    // Set Encoder to Leader Motor Encoder
    m_leftEncoder = m_leftLeaderMotor.getEncoder();
    m_rightEncoder = m_rightLeaderMotor.getEncoder();

    m_drive = new DifferentialDrive(m_leftLeaderMotor, m_rightLeaderMotor);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // Initializes Motors by Setting Defaults
  private void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults(); 
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kCurrentLimit);
    motor.setInverted(invert);
  }

  // Drives Using Tank Drive
  public void tankDrive(double leftPower, double rightPower, boolean squareInputs){
    m_drive.tankDrive(leftPower, rightPower, squareInputs);
  }
  
  // Drives Using Arcade Drive
  public void arcadeDrive(double speed, double turn, boolean squareInputs){
    m_drive.arcadeDrive(speed, turn, squareInputs);
  }
  // Stop All Drive Motors
  public void stopDrive(){
    m_drive.tankDrive(0, 0);
  }
}
