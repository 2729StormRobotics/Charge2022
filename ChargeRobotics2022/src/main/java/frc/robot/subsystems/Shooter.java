// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {

  /**
   * The motors. One is reversed.
   */
  private final CANSparkMax m_leftMotor;
  private final CANSparkMax m_rightMotor;

  private final RelativeEncoder m_encoder;

  private final SparkMaxPIDController m_pidController;

  private final DoubleSolenoid m_pistons;

  // Constants
  // These are members so they can be edited by the Shuffleboard.
  // However, they should become constants in production code.
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private double m_setpoint;

  private boolean m_enabled;

  /** Creates a new ShoooterBetter. */
  public Shooter() {

    // Instantiate the solenoid
    m_pistons = new DoubleSolenoid(kPneumaticsHubCanId, ShooterConstants.kPistonModuleType,
        ShooterConstants.kBottomExtendedChannel, ShooterConstants.kBottomRetractedChannel);
    pistonInit();

    // Instantiate the motors
    m_leftMotor = new CANSparkMax(ShooterConstants.kLeftMotorSlePort, MotorType.kBrushless);
    m_rightMotor = new CANSparkMax(ShooterConstants.kRightMotorSlePort, MotorType.kBrushless);

    motorInit(m_leftMotor);
    motorInit(m_rightMotor);
    
    m_rightMotor.follow(m_leftMotor, true);

    // Use the left motor's encoder, because it should be positive.
    // The motors are on the same axle; they must have the same speed.
    m_encoder = m_leftMotor.getEncoder();
    encoderInit(m_encoder);

    // Use the left motor's PID Controller
    m_pidController = m_leftMotor.getPIDController();

    // Because we are using the left motor for control,
    // set the right motor to follow the left motor, but backwards
    //  m_rightMotor.setInverted(true);

    // PID coefficients
    kP = ShooterConstants.kP;
    kI = ShooterConstants.kI;
    kD = ShooterConstants.kD;
    kIz = ShooterConstants.kIz;
    kFF = ShooterConstants.kFF;

    // Using .set() on the motor
    kMaxOutput = 1;
    kMinOutput = -1;

    // Set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // Display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    // SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    // SmartDashboard.putNumber("I Zone", kIz);
    // SmartDashboard.putNumber("Feed Forward", kFF);
    // SmartDashboard.putNumber("Max Output", kMaxOutput);
    // SmartDashboard.putNumber("Min Output", kMinOutput);

    m_setpoint = 0;
    m_enabled = false;
    SmartDashboard.putNumber("Set Velocity", m_setpoint);

  }

  /**
   * Get the current flywheel speed
   * @return the current speed in RPM
   */
  public double getFlywheelSpeed() {
    return m_encoder.getVelocity();
  }

  /**
   * Initializes an encoder
   * 
   * @param encoder the encoder to initialize
   */
  private void encoderInit(RelativeEncoder encoder) {
    // 1 so that we use RPM
    m_encoder.setVelocityConversionFactor(1);
    // Initial position is 0
    encoder.setPosition(0);
  }

  /**
   * Initializes a motor
   * 
   * @param motor the motor to initialize
   */
  private void motorInit(CANSparkMax motor) {
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    // motor.setInverted(invert);
  }

  // Initialize the pistons to be retracted
  private void pistonInit() {
    retractPistons();
  }

  /**
   * Retracts the pistons
   */
  public void retractPistons() {
    m_pistons.set(ShooterConstants.kPistonRetractedValue);
  }

  /**
   * Extends the pistons
   */
  public void extendPistons() {
    m_pistons.set(ShooterConstants.kPistonExtendedValue);
  }

  /**
   * Checks if the flywheel velocity is within a tolerable range near the
   * setpoint.
   * 
   * @return if the flywheel is up to speed
   */
  public boolean atSetpoint() {
    return (Math.abs(m_encoder.getVelocity() - m_setpoint) <= ShooterConstants.kVelocityTolerance);
  }

  /**
   * Sets the PID controller's setpoint
   * 
   * @param rpm the new setpoint, in RPM
   */
  public void setSetpoint(double rpm) {
    m_setpoint = rpm;
    if (m_enabled) {
      m_pidController.setP(kP);
      m_pidController.setReference(m_setpoint, ControlType.kVelocity);
    }

    

  }

  public void enableLoop() {
    m_enabled = true;
    m_pidController.setP(kP);
    m_pidController.setReference(m_setpoint, ControlType.kVelocity);
  }

  public void disableLoop() {
    m_enabled = false;
    m_pidController.setP(0);
    m_pidController.setReference(0, ControlType.kVelocity);  
    m_leftMotor.set(0);
  }

  public void gentleStop() {
    m_pidController.setP(0);
    m_pidController.setReference(0, ControlType.kVelocity);
    m_setpoint = 0;
    m_leftMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Read PID coeffecients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    // double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    // double iz = SmartDashboard.getNumber("I Zone", 0);
    // double ff = SmartDashboard.getNumber("Feed Forward", 0);
    // double max = SmartDashboard.getNumber("Max Output", 0);
    // double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    // if (p != kP) {
    //   m_pidController.setP(p);
    //   kP = p;
    // }
    // if (i != kI) {
    //   m_pidController.setI(i);
    //   kI = i;
    // }
    // if (d != kD) {
    //   m_pidController.setD(d);
    //   kD = d;
    // }
    // if (iz != kIz) {
    //   m_pidController.setIZone(iz);
    //   kIz = iz;
    // }
    // if (ff != kFF) {
    //   m_pidController.setFF(ff);
    //   kFF = ff;
    // }
    // if ((max != kMaxOutput) || (min != kMinOutput)) {
    //   m_pidController.setOutputRange(min, max);
    //   kMinOutput = min;
    //   kMaxOutput = max;
    // }

     double setPoint;
     setPoint = SmartDashboard.getNumber("Set Velocity", 0);
     setSetpoint(setPoint);

    double processVariable;
    processVariable = m_encoder.getVelocity();

    SmartDashboard.putNumber("SetPoint", m_setpoint);
    SmartDashboard.putNumber("Flywheel Speed", processVariable);
    // SmartDashboard.putNumber("Output", m_leftMotor.getAppliedOutput());

    SmartDashboard.putBoolean("Shooter Pistons Extended", m_pistons.get().equals(ShooterConstants.kPistonExtendedValue));

    String setpointString;

    if (m_setpoint == kWallShotSetpoint) {
      setpointString = "Wall Shot";
    } else if (m_setpoint == kCloseLaunchpadShotSetpoint) {
      setpointString = "Close Launchpad Shot";
    } else if (m_setpoint == kFarLaunchpadShotSetpoint) {
      setpointString = "Far Launchpad";
    } else if (m_setpoint == kHubShotSetpoint) {
      setpointString = "Hub Shot";
    } else {
      setpointString = String.valueOf(m_setpoint);
    }

    SmartDashboard.putString("Shooter Setting", setpointString);
  }
}