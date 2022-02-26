// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants.*;

public class Intake extends SubsystemBase {

  private final CANSparkMax m_intakeMotor;
  private final DoubleSolenoid m_leftIntakePiston;
  
//creates a new intake
  public Intake() {
    m_intakeMotor = new CANSparkMax(kIntakeMotorPort, MotorType.kBrushless);

    m_leftIntakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, kIntakeExtendChannel, kIntakeRetractChannel);
   
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
  }
//retracts the motors
  public void retractIntake() {
    m_leftIntakePiston.set(kIntakeRetractValue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
}
