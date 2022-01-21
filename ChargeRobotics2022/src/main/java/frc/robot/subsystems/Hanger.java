// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.HangerConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class Hanger extends SubsystemBase {

  private final CANSparkMax m_hangerMotor;

// private final RelativeEncoder m_encoder;

  /** Creates a new Hanger. */
  public Hanger() {
m_hangerMotor = new CANSparkMax(3, MotorType.kBrushless);


// m_encoder = new RelativeEncoder() {
  
// };


  }

/**
 * Initializes the hanger motor.
 * @param motor The motor to be initialized
 */
  private void motorInit(CANSparkMax motor){
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);



  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
