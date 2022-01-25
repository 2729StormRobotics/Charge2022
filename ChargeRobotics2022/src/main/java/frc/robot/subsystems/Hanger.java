// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.HangerConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hanger extends SubsystemBase {

  private final CANSparkMax m_hangerMotor;

  private final RelativeEncoder m_encoder;

  private final Solenoid m_pawlPiston;
  private boolean m_retracted = true;

  /**
   * Creates a new Hanger.
   * 
   */
  public Hanger() {
    m_hangerMotor = new CANSparkMax(kHangerMotorPort, MotorType.kBrushless);
    motorInit(m_hangerMotor, kMotorInverted);

    m_encoder = m_hangerMotor.getEncoder();

    m_pawlPiston = new Solenoid(kPawlPistonChannel);
    m_pawlPiston.set(kPawlPistonDisabled);

    shuffleboardInit();

  }

  

  /**
   * Initializes the hanger motor
   * 
   * @param motor The motor to be initialized
   */
  private void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults(); // Resets the motors to default settings
    motor.setIdleMode(IdleMode.kBrake); // Sets the motor to brake mode when idle
    motor.setInverted(invert); // Invert the motor if needed
    encoderInit(motor.getEncoder()); // Initialize the encoder
  }

  /**
   * Initializes the encoder
   * 
   * @param encoder The encoder being reset
   */
  private void encoderInit(RelativeEncoder encoder) {
    encoder.setPositionConversionFactor(kDistancePerRotation);
    encoder.setVelocityConversionFactor(kSpeedPerRotation);
    encoderReset(encoder);
  }

  /**
   * Resets the encoder position to 0
   * 
   * @param encoder The encoder being reset
   */
  private void encoderReset(RelativeEncoder encoder) {
    encoder.setPosition(0.0);
  }

  /**
   * Drives the hanger motor
   * 
   * @param speed The motor speed at which to run the hanger
   */
  public void climb(double speed) {
    m_hangerMotor.set(speed);
  }

  /**
   * Drives the hanger motor so that the elevator rises using a set constant speed
   */
  public void climbUp() {
    climb(kClimbUpSpeed);
  }

  /**
   * Drives the hanger motor so that the elevator descends using a set constant speed
   */
  public void climbDown() {
    climb(kClimbDownSpeed);
  }

  /**
   * Stops the hanger motor
   */
  public void stopClimb() {
    climb(0.0);
  }

  /**
   * Get the current height
   * 
   * @return The current height, in inches
   */
  private double getHeight() {
    return m_encoder.getPosition();
  }

  /**
   * Get the current speed
   * 
   * @return The current speed, in inches per second
   */
  private double getSpeed() {
    return m_encoder.getVelocity();
  }

  /**
   * Determine if the climber is above its max height
   * 
   * @return true if the current height is above its maximum
   */
  public boolean atMaxHeight() {
    return getHeight() > kMaxHeight;
  }

  /**
   * Determine if the climber is below its min height
   * 
   * @return true if the current height is below its minimum
   */
  public boolean atMinHeight() {
    return getHeight() < 0;
  }

  /**
   * Engages the pawl piston, which locks the elevator at its current extension
   */
  public void engagePawlPiston() {
    m_pawlPiston.set(kPawlPistonEnabled);
  }

  /**
   * Disengages the pawl piston, which locks the elevator at its current extension
   */
  public void disengagePawlPiston() {
    m_pawlPiston.set(kPawlPistonDisabled);
  }

/**
 * Adds data to shuffleboard
 */
private void shuffleboardInit(){

}

  @Override
  public void periodic() {
    if (getHeight() > 1) {
      m_retracted = false;
    } else {
      m_retracted = true;
    }
    // This method will be called once per scheduler run
  }  
}
