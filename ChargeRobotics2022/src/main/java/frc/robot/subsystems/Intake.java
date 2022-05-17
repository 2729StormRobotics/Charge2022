// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants.*;

public class Intake extends SubsystemBase {

  private final CANSparkMax m_intakeMotor;
  private final DoubleSolenoid m_leftIntakePiston;
  private final DoubleSolenoid m_rightIntakePiston;
  
//creates a new intake
  public Intake() {
    m_intakeMotor = new CANSparkMax(kIntakeMotorPort, MotorType.kBrushless);

    m_leftIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, kLeftIntakeExtendChannel, kLeftIntakeRetractChannel);
    m_rightIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, kRightIntakeExtendChannel, kRightIntakeRetractChannel);
   
    motorInit(m_intakeMotor);

  }

  private void motorInit(CANSparkMax motor) {
    motor.restoreFactoryDefaults(); 
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kCurrentLimit);
  }

//run the motor with given speed
  public void runIntake() {
    m_intakeMotor.set(kIntakeMotorSpeed);
  }

//reverse the motor so that the intake will spit out the ball
  public void ejectIntake() {
    m_intakeMotor.set(kEjectMotorSpeed);
  }
//stops the motor of intake
  public void stopIntake() {
    m_intakeMotor.set(kIntakeMotorStopSpeed);
  }

// extends the motors so that the intake can reach the ball
  public void extendIntake() {
    m_leftIntakePiston.set(kIntakeExtendValue);
    m_rightIntakePiston.set(kIntakeExtendValue);
  }
//retracts the motors
  public void retractIntake() {
    m_leftIntakePiston.set(kIntakeRetractValue);
    m_rightIntakePiston.set(kIntakeRetractValue);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Motor Speed", m_intakeMotor.get());
    SmartDashboard.putString("Piston State", (m_leftIntakePiston.get().equals(DoubleSolenoid.Value.kForward)) ? "Extended":"Retracted");
  }
  
}
