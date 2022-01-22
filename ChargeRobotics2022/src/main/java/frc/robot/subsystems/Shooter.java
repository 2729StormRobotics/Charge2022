// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
  
  private final CANSparkMax m_motor;

  private final RelativeEncoder m_encoder;

  private final DoubleSolenoid m_bottomAnglePiston1;
  private final DoubleSolenoid m_bottomAnglePiston2;
  private final DoubleSolenoid m_sideAnglePiston1;
  private final DoubleSolenoid m_sideAnglePiston2;

  private boolean m_bottomPistonsExtended;
  private boolean m_sidePistonsExtended;

  private final SimpleMotorFeedforward m_feedForward;
  private final SparkMaxPIDController m_pidController;
  
  /** Creates a new Shooter. */
  public Shooter() {

    // Instantiate the motor
    m_motor = new CANSparkMax(kMotorPort, MotorType.kBrushless);

    // Instantiate the encoders
    m_encoder = m_motor.getEncoder();

    // Instantiate the pistons
    m_bottomAnglePiston1 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);
    m_bottomAnglePiston2 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);
    m_sideAnglePiston1 = new DoubleSolenoid(kPistonModuleType, kSideExtendedChannel, kSideRetractedChannel);
    m_sideAnglePiston2 = new DoubleSolenoid(kPistonModuleType, kSideExtendedChannel, kSideRetractedChannel);

    // Initialize the motor
    motorInit(m_motor);

    // Initialize the pistons 
    pistonInit();

    // Determine if bottom and side pistons are extended or retracted
    m_bottomPistonsExtended = false;
    m_sidePistonsExtended = false;

    // Initialize the PID controller for the motor controller.
    m_pidController = m_motor.getPIDController();

     // Initialize pid coefficients
    pidInit();

    m_feedForward = new SimpleMotorFeedforward(kS, kV, kA);
}

  // Intialize the motor
  private void motorInit(CANSparkMax motor){
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    encoderInit(motor.getEncoder());
  }

  // Runs the motor
  public void runMotor(){
    m_motor.set(kMotorSpeed);
  } 

  // Stops the motor
  public void stopMotor(){
    m_motor.set(0);
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
    retractSidePistons();
    retractBottomPistons();
  }

  // Retract bottom pistons
  private void retractBottomPistons(){
    m_bottomAnglePiston1.set(kPistonRetractedValue);
    m_bottomAnglePiston2.set(kPistonRetractedValue);
    m_bottomPistonsExtended = false;
  }

  // Extends bottom pistons
  private void extendBottomPistons(){
    m_bottomAnglePiston1.set(kPistonExtendedValue);
    m_bottomAnglePiston2.set(kPistonExtendedValue);
    m_bottomPistonsExtended = true;
  }

  // Retract side pistons
  private void retractSidePistons(){
    m_sideAnglePiston1.set(kPistonRetractedValue);
    m_sideAnglePiston2.set(kPistonRetractedValue);
    m_sidePistonsExtended = false;
  }

  // Extend side pistons
  private void extendSidePistons(){
    m_sideAnglePiston1.set(kPistonExtendedValue);
    m_sideAnglePiston2.set(kPistonExtendedValue);
    m_sidePistonsExtended = true;
  }

  // Retract to lowest position 
  public void setRetractedAngle(){
    // retractSidePistons();
    // retractBottomPistons();

    if (!m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle high position
      retractSidePistons();
    }
    else if (m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in extended position 
      retractBottomPistons();
    }
    else if (m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle low positions
      retractBottomPistons();
      retractSidePistons();
    }
  }
  
  // Extend to highest position
  public void setExtendedAngle(){    
    // retractSidePistons();
    // extendBottomPistons();

    if (!m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in retracted position 
      extendBottomPistons();
    }
    else if (!m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle high position
      retractSidePistons();
      extendBottomPistons();
    }
    else if (m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle low position 
      retractSidePistons();
    }
  }

  // Extend to second lowest position
  public void setMiddleLowAngle(){
    // setRetractedAngle();
    // extendSidePistons();
    // extendBottomPistons();

    if (!m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in retracted position 
      extendSidePistons();
      extendBottomPistons();
    }
    else if (!m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle high position
      retractSidePistons();
      extendSidePistons();
      extendBottomPistons();
    }
    else if (m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in extended position
      retractBottomPistons();
      extendSidePistons();
      extendBottomPistons();
    }
  }

  // Extend to second highest position
  public void setMiddleHighAngle(){
    // setRetractedAngle();
    // extendBottomPistons();
    // extendSidePistons();

    if (!m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in retracted position
      extendBottomPistons();
      extendSidePistons();
      retractBottomPistons();
    }
    else if (m_bottomPistonsExtended && !m_sidePistonsExtended){ // if pistons are in extended position
      extendSidePistons();
      retractBottomPistons();
    }
    else if (m_bottomPistonsExtended && m_sidePistonsExtended){ // if pistons are in middle low position
      retractSidePistons();
      extendSidePistons();
      retractBottomPistons();
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
    // Set the integral zone, which is the maximum error for the integral gain to take effect
    m_pidController.setIZone(kIz);
    // Set the feed forward constant  
    m_pidController.setFF(kFF);
    // Set the output range
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void pidAdjust() {
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
  }

  // Set the motor controller to set the PID controller 
  public void revToSpeed(double speed) {
    double feedforward = m_feedForward.calculate(speed);
    m_pidController.setReference(speed, CANSparkMax.ControlType.kVelocity, 0, feedforward);
  }

  // Sets the motor for the hub shot
  public void revHubShot(){
    revToSpeed(kHubShotSpeed);
  }

  // Sets the motor for the middle shot
  public void revMiddleShot(){
    revToSpeed(kMiddleShotSpeed);
  }

  // Sets the motor for the close launch pad shot
  public void revCloseLaunchPadShot(){
    revToSpeed(kCloseLaunchPadShotSpeed);
  }

  // Sets the motor for the long launch pad shot
  public void revFarLaunchPadShot(){
    revToSpeed(kFarLaunchPadShotSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}
