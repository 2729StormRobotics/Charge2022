// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IndexConstants.*;

public class Index extends SubsystemBase {

  private final CANSparkMax m_lowerIndexMotor;
  private final CANSparkMax m_upperIndexMotor;
  private final DigitalInput m_lowerBeamBraker;
  private final DigitalInput m_upperBeamBraker;
  /** Creates a new Index. */
  public Index() {

    m_lowerIndexMotor = new CANSparkMax(kLowerIndexMotorPort, MotorType.kBrushless);
    m_upperIndexMotor = new CANSparkMax(kUpperIndexMotorPort, MotorType.kBrushless);
    m_lowerBeamBraker = new DigitalInput(kLowerIndexBeamBrakerPort);
    m_upperBeamBraker = new DigitalInput(kUpperIndexBeamBrakerPort);

  }

  public void runLowerIndexMotor() {
    m_lowerIndexMotor.set(kLowerIndexMotorSpeed);
    //may have to add ball retraction
  }


  public void runUpperIndexMotor() {
    m_upperIndexMotor.set(kUpperIndexMotorSpeed);
     //may have to add ball retraction
  }

  public void stopIndexMotors(boolean stopLower) {
    m_lowerIndexMotor.set(stopLower ? kIndexMotorStopSpeed:kLowerIndexMotorSpeed);
    m_upperIndexMotor.set(stopLower ? kUpperIndexMotorSpeed:kIndexMotorStopSpeed);
  }

  public void stopIndexMotors() {
    m_lowerIndexMotor.set(kIndexMotorStopSpeed);
    m_upperIndexMotor.set(kIndexMotorStopSpeed);
  }

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
