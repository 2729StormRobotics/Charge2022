// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
  
  private final CANSparkMax m_motor;

  private final RelativeEncoder m_encoder;

  private final DoubleSolenoid m_piston1;
  private final DoubleSolenoid m_piston2;

  private final SimpleMotorFeedforward m_feedForward;
  private final PIDController m_pidController;

  private double m_setpoint;

  private boolean m_pistonsExtended;
  
  /** Creates a new Shooter. */
  public Shooter() {

    // Instantiate the motor
    m_motor = new CANSparkMax(kMotorPort, MotorType.kBrushless);

    // Instantiate the encoder
    m_encoder = m_motor.getEncoder();

    // Instantiate the pistons
    m_piston1 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);
    m_piston2 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);

    // Initialize the motor
    motorInit(m_motor);

    // Initialize the pistons 
    pistonInit();

    // Initialize the setpoint 
    m_setpoint = 0;

    // Initialize the PID controller for the motor controller.
    m_pidController = new PIDController(kP, kI, kD);

    // Initialize the PID coefficients
    pidInit();

    m_feedForward = new SimpleMotorFeedforward(kS, kV, kA);

    m_pistonsExtended = false;
}

  // Intialize the motor
  private void motorInit(CANSparkMax motor){
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    encoderInit(motor.getEncoder());
  }

  // Stops the motor
  public void stopMotor(){
    m_motor.set(0);
  }

  public double getSetpoint(){
    return m_setpoint;
  }

  public void setSetpoint(double setpoint){
    m_setpoint = setpoint;
  }

  public double getVelocity(){
    return m_encoder.getVelocity();
  }

  // Intialize the encoders
    private void encoderInit(RelativeEncoder encoder){
      m_encoder.setVelocityConversionFactor(kVelocityConversion); // Sets encoder units to be the desired ones
      resetEncoder(encoder); 
  }
  
  // Reset encoder distance
    private void resetEncoder(RelativeEncoder encoder){
      encoder.setPosition(0);
  }
  
  // Intialize the pistons to be retracted
  private void pistonInit(){
    retractPistons();
  }

  // Retracts the pisons
  public void retractPistons(){
    if (m_pistonsExtended){
      m_piston1.set(kPistonRetractedValue);
      m_piston2.set(kPistonRetractedValue);

      m_pistonsExtended = false;
    }  
  }

  // Extends the pistons
  public void extendPistons(){
    if (!m_pistonsExtended){
      m_piston1.set(kPistonExtendedValue);
      m_piston2.set(kPistonExtendedValue);

      m_pistonsExtended = true;
    }
  }

  // Set PID coefficients for the PID Controller to use 
  private void pidInit(){
    // Set the proportional constant
    m_pidController.setP(kP); 
    // Set the integral constant
    m_pidController.setI(kI); 
    // Set the derivative constant
    m_pidController.setD(kD); 
    // // Set the integral zone, which is the maximum error for the integral gain to take effect
    // m_pidController.setIZone(kIz);
    // // Set the feed forward constant  
    // m_pidController.setFF(kFF);
    // // Set the output range
    // m_pidController.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void pidAdjust() {
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
  }

  /*
  // Set the motor controller to set the PID controller 
  public void revToSpeed(double speed) {
    double feedforward = m_feedForward.calculate(speed);
    m_pidController.setReference(speed, CANSparkMax.ControlType.kVelocity, 0, feedforward);
  }

  // Sets the motor for the hub shot
  public void revHubShot(){
    revToSpeed(kHubShotSpeed);
  }

  // Sets the motor for the close launch pad shot
  public void revCloseLaunchPadShot(){
    revToSpeed(kCloseLaunchPadShotSpeed);
  }

  // Sets the motor for the long launch pad shot
  public void revFarLaunchPadShot(){
    revToSpeed(kFarLaunchPadShotSpeed);
  }
  */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_motor.set(m_pidController.calculate(m_encoder.getVelocity()));
  }

}
