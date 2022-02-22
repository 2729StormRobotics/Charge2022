// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IndexConstants.*;

public class Index extends SubsystemBase {

  private final CANSparkMax m_lowerIndexMotor;
  private final CANSparkMax m_upperIndexMotor;
  private final DigitalInput m_lowerBeamBraker;
  private final DigitalInput m_upperBeamBraker;
  /**
  Creates a new Index.
  The index system has two motors and two beam brakers.
  */

  public Index() {

    m_lowerIndexMotor = new CANSparkMax(kLowerIndexMotorPort, MotorType.kBrushless);
    m_upperIndexMotor = new CANSparkMax(kUpperIndexMotorPort, MotorType.kBrushless);
    m_lowerBeamBraker = new DigitalInput(kLowerIndexBeamBrakerPort);
    m_upperBeamBraker = new DigitalInput(kUpperIndexBeamBrakerPort);

    motorInit(m_lowerIndexMotor);
    motorInit(m_upperIndexMotor);

  }

  private void motorInit(CANSparkMax motor) {
    motor.restoreFactoryDefaults(); 
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kCurrentLimit);
  }

  //run lower motor
  public void runLowerIndexMotor() {
    m_lowerIndexMotor.set(kLowerIndexMotorSpeed);
  }

  //run upper motor
  public void runUpperIndexMotor() {
    m_upperIndexMotor.set(kUpperIndexMotorSpeed);
  }

  //reverse both motors
  public void ejectIndex() {
    m_lowerIndexMotor.set(kEjectIndexMotorSpeed);
    m_upperIndexMotor.set(kEjectIndexMotorSpeed);
  }

  //stop one motor(stopLower = true for left and false for right)
  public void stopIndexMotors(boolean stopLower) {
    m_lowerIndexMotor.set(stopLower ? kIndexMotorStopSpeed:kLowerIndexMotorSpeed);
    m_upperIndexMotor.set(stopLower ? kUpperIndexMotorSpeed:kIndexMotorStopSpeed);
  }

  //stop both motors
  public void stopIndexMotors() {
    m_lowerIndexMotor.set(kIndexMotorStopSpeed);
    m_upperIndexMotor.set(kIndexMotorStopSpeed);
  }

  //returns true if the beam is not broken
  public boolean getLowerBeamBrakerStatus() {
    return m_lowerBeamBraker.get();
  }
  
  public boolean getUpperBeamBrakerStatus() {
    return m_upperBeamBraker.get();
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
