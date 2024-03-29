// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.HangerConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hanger extends SubsystemBase {
  private final CANSparkMax m_hangerMotorLeft;
  private final CANSparkMax m_hangerMotorRight;

  private final RelativeEncoder m_encoderLeft;
  private final RelativeEncoder m_encoderRight;  

  // private final Solenoid m_pawlPiston;

  /**
   * Creates a new Hanger.
   * 
   */
  public Hanger() {
    m_hangerMotorLeft = new CANSparkMax(kHangerMotorLeftPort, MotorType.kBrushless);
    motorInit(m_hangerMotorLeft);

    m_hangerMotorRight = new CANSparkMax(kHangerMotorRightPort, MotorType.kBrushless);
    motorInit(m_hangerMotorRight);

    m_hangerMotorLeft.follow(m_hangerMotorRight, true);

    m_encoderLeft = m_hangerMotorLeft.getEncoder();
    m_encoderRight = m_hangerMotorRight.getEncoder();

    // m_pawlPiston = new Solenoid(Constants.kPneumaticsHubCanId, PneumaticsModuleType.REVPH, kPawlPistonChannel);
    // m_pawlPiston.set(kPawlPistonDisabled);

    shuffleboardInit();
  }

  

  /**
   * Initializes the hanger motor
   * 
   * @param motor The motor to be initialized
   */
  private void motorInit(CANSparkMax motor) {
    motor.restoreFactoryDefaults(); // Resets the motors to default settings
    motor.setIdleMode(IdleMode.kBrake); // Sets the motor to brake mode when idle
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
    //if (speed > 0) {                     need to know direction.
    m_hangerMotorRight.set(speed);
    //}
  }

  /**
   * Stops the hanger motor
   */
  public void stopClimb() {
    climb(0.0);
  }

  /**
   * Get the current height of the left side
   * 
   * @return The current height, in inches
   */
  public double getHeightLeft() {
    return m_encoderLeft.getPosition();
  }

  /**
   * Get the current height of the right side
   * 
   * @return The current height, in inches
   */
  public double getHeightRight() {
    return m_encoderRight.getPosition();
  }

  /**
   * Get the average of the left and right sides
   * 
   * @return The current average height, in inches
   */
  public double getHeightAverage(){
    return (getHeightLeft() + getHeightRight()) / 2;
  }

  /**
   * Get the current left speed
   * 
   * @return The current speed, in inches per second
   */
  public double getSpeedLeft() {
    return m_encoderLeft.getVelocity();
  }

  /**
   * Get the current right speed
   * 
   * @return The current speed, in inches per second
   */
  public double getSpeedRight() {
    return m_encoderRight.getVelocity();
  }

  /**
   * Determine if the climber is above its max height
   * 
   * @return true if the current height is above its maximum
   */
  public boolean atMaxHeight() {
    return getHeightLeft() > kMaxHeight && getHeightRight() > kMaxHeight;
  }

  /**
   * Determine if the climber is below its min height
   * 
   * @return true if the current height is below its minimum
   */
  public boolean atMinHeight() {
    return getHeightAverage() < 0;
  }

  /**
   * Engages the pawl piston, which locks the elevator at its current extension
   */
  public void engagePawlPiston() {
    // m_pawlPiston.set(kPawlPistonEnabled);
  }

  /**
   * Disengages the pawl piston, which locks the elevator at its current extension
   */
  public void disengagePawlPiston() {
    // m_pawlPiston.set(kPawlPistonDisabled);
  }

/**
 * Adds data to shuffleboard
 */
private void shuffleboardInit(){

}

  @Override
  public void periodic() {
    // if (getHeightAverage() > 1) {
    //   m_retracted = false;
    // } else {
    //   m_retracted = true;
    // }
    // This method will be called once per scheduler run

    // SmartDashboard.putBoolean("Hanger Pawl", m_pawlPiston.get());
  }  
}
