// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import static frc.robot.Constants.ShooterConstants.*;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter extends PIDSubsystem {

  private final CANSparkMax m_leftMotor;
  private final CANSparkMax m_rightMotor;

  private final RelativeEncoder m_encoder;

  private final DoubleSolenoid m_piston1;
  private final DoubleSolenoid m_piston2;

  private final ShuffleboardTab m_shooterTab;
  private final ShuffleboardLayout m_shooterLayout;

  /** Creates a new Shooter2. */
  public Shooter() {
    super(
        // The PIDController used by the subsystem
        new PIDController(kP, kI, kD));

    // Instantiate the motor
    m_leftMotor = new CANSparkMax(kLeftMotorPort, MotorType.kBrushless);
    m_rightMotor = new CANSparkMax(kRightMotorPort, MotorType.kBrushless);

    // Instantiate the encoder
    m_encoder = m_leftMotor.getEncoder();

    // Instantiate the pLeftistons
    m_piston1 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);
    m_piston2 = new DoubleSolenoid(kPistonModuleType, kBottomExtendedChannel, kBottomRetractedChannel);

    // Initialize the motor
    motorInit(m_leftMotor);

    // Initialize the pistons
    pistonInit();

    m_shooterTab = Shuffleboard.getTab(kShooterShuffleboardTabName);
    m_shooterLayout = m_shooterTab.getLayout("Hood Position", BuiltInLayouts.kList).withProperties(Map.of("Lable position", "TOP"));

    shuffleboardInit();
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


  // Intialize the motor
  private void motorInit(CANSparkMax motor){
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    encoderInit(motor.getEncoder());
  }

  // Stops the motor
  public void stopMotor(){
    m_leftMotor.set(0);
  }

  // ILeftntialize the pistons to be retracted
  private void pistonInit(){
    retractPistons();
  }

  // Retracts the pisons
  public void retractPistons(){
      m_piston1.set(kPistonRetractedValue);
      m_piston2.set(kPistonRetractedValue); 
  }

  // Extends the pistons
  public void extendPistons(){
      m_piston1.set(kPistonExtendedValue);
      m_piston2.set(kPistonExtendedValue);    
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    m_leftMotor.set(output);

  }

  @Override
 public double getMeasurement() {
    // Return the process variable measurement here
    return m_encoder.getVelocity();
  }

  public String getPistonStatus(){
      if (m_piston1.get().equals(kPistonExtendedValue) && m_piston2.get().equals(kPistonExtendedValue)){
        return "Extended";
      }else if (m_piston1.get().equals(kPistonRetractedValue) && m_piston2.get().equals(kPistonRetractedValue)){
        return "Retracted";
      }else {
        return "Indeterminate";
      }
  }


  private void shuffleboardInit(){
    m_shooterLayout.addString("Hood Position", () -> getPistonStatus());
  }
}
