// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import static frc.robot.Constants.ShooterConstants.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {

  private final CANSparkMax m_leftMotor;
  private final CANSparkMax m_rightMotor;

  private final RelativeEncoder m_encoder;

  private final DoubleSolenoid m_piston1;
  // private final DoubleSolenoid m_piston2;

  /** Creates a new Shooter2. */
  public Shooter() {
    super(
        // The PIDController used by the subsystem
        new PIDController(kP, kI, kD));

    // Instantiate the motor
    m_leftMotor = new CANSparkMax(kLeftMotorPort, MotorType.kBrushless);
    m_rightMotor = new CANSparkMax(kRightMotorPort, MotorType.kBrushless);

    // Instantiate the pLeftistons
    m_piston1 = new DoubleSolenoid(kPneumaticsHubCanId, kPistonModuleType, kBottomExtendedChannel,
        kBottomRetractedChannel);
    // m_piston2 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel,
    // kBottomRetractedChannel);

    // Initialize the motor
    motorInit(m_leftMotor);
    motorInit(m_rightMotor);

    // Instantiate the encoder
    m_encoder = m_leftMotor.getEncoder();

    encoderInit(m_encoder);

    // Initialize the pistons
    pistonInit();

    m_controller.setTolerance(300);

    // SmartDashboard.putNumber("Shooter Velocity", m_encoder.getVelocity());
    SmartDashboard.putNumber("Shooter Setpoint", m_controller.getSetpoint());
    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    // SmartDashboard.putNumber("I Zone", kIz);
    // SmartDashboard.putNumber("Feed Forward", kFF);
    // SmartDashboard.putNumber("Max Output", kMaxOutput);
    // SmartDashboard.putNumber("Min Output", kMinOutput);

    super.enable();
  }

  // Initialize the encoders
  private void encoderInit(RelativeEncoder encoder) {
    m_encoder.setVelocityConversionFactor(1); // Sets encoder units to be the desired ones
    resetEncoder(encoder);
  }

  // Reset encoder distance
  private void resetEncoder(RelativeEncoder encoder) {
    encoder.setPosition(0);
  }

  // Initialize the motor
  private void motorInit(CANSparkMax motor) {
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    // encoderInit(motor.getEncoder());
  }

  // Stops the motor
  public void stopMotor() {
    m_leftMotor.set(0);
    m_rightMotor.set(0);
  }

  // Initialize the pistons to be retracted
  private void pistonInit() {
    retractPistons();
  }

  // Retracts the pisons
  public void retractPistons() {
    m_piston1.set(kPistonRetractedValue);
    // m_piston2.set(kPistonRetractedValue);
  }

  // Extends the pistons
  public void extendPistons() {
    m_piston1.set(kPistonExtendedValue);
    // m_piston2.set(kPistonExtendedValue);
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  public double getControllerSetpoint() {
    return m_controller.getSetpoint();
  }

  @Override
  public void useOutput(double output, double setpoint) {
    output = MathUtil.clamp(output, -1, 1);

    // Use the output here
    m_leftMotor.set(output);
    m_rightMotor.set(output);
    // SmartDashboard.putNumber("PID Output", output);
    // SmartDashboard.putNumber("PID error", m_controller.getPositionError());
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measuremnt here
    return m_encoder.getVelocity();
  }

  public void manualSpin(double speed) {
    m_leftMotor.set(speed);
    m_rightMotor.set(speed);
  }

  @Override
  public void periodic() {
    super.periodic();
    // SmartDashboard.putNumber("Shooter Velocity", m_encoder.getVelocity());
    // SmartDashboard.putNumber("Shooter Setpoint", m_controller.getSetpoint());
    double setpoint = (SmartDashboard.getNumber("Shooter Setpoint", 0));

    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    // double iz = SmartDashboard.getNumber("I Zone", 0);
    // double ff = SmartDashboard.getNumber("Feed Forward", 0);
    // double max = SmartDashboard.getNumber("Max Output", 0);
    // double min = SmartDashboard.getNumber("Min Output", 0);
    // double maxV = SmartDashboard.getNumber("Max Velocity", 0);
    // double minV = SmartDashboard.getNumber("Min Velocity", 0);
    // double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
    // double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    getController().setP(p);
    getController().setI(i);
    getController().setD(d);

    // if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    // if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    // if((max != kMaxOutput) || (min != kMinOutput)) {
    // m_pidController.setOutputRange(min, max);
    // kMinOutput = min; kMaxOutput = max;
    // }
    // if((maxV != maxVel)) { m_pidController.setSmartMotionMaxVelocity(maxV,0);
    // maxVel = maxV; }
    // if((minV != minVel)) {
    // m_pidController.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
    // if((maxA != maxAcc)) { m_pidController.setSmartMotionMaxAccel(maxA,0); maxAcc
    // = maxA; }
    // if((allE != allowedErr)) {
    // m_pidController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr =
    // allE; }

    // setPoint = SmartDashboard.getNumber("Set Velocity", 0);
    // setSetpoint(setPoint);
    double processVariable = m_encoder.getVelocity();

    SmartDashboard.putNumber("READ SetPoint", getSetpoint());
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", m_leftMotor.getAppliedOutput());
  }

}
