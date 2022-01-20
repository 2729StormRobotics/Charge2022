// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
  
  /** Creates a new Shooter. */
  public Shooter() {

    // Instantiate the motor
    m_motor = new CANSparkMax(kLeftMotorPort, MotorType.kBrushless);

    // Instantiate the encoders
    m_encoder = m_motor.getEncoder();

    // Instantiate the pistons
    m_bottomAnglePiston1 = new DoubleSolenoid(kBottomExtendedChannel, kBottomRetractedChannel);
    m_bottomAnglePiston2 = new DoubleSolenoid(kBottomExtendedChannel, kBottomRetractedChannel);
    m_sideAnglePiston1 = new DoubleSolenoid(kSideExtendedChannel, kSideRetractedChannel);
    m_sideAnglePiston2 = new DoubleSolenoid(kSideExtendedChannel, kSideRetractedChannel);

    // Initialize the motor
    motorInit(m_motor);

    // Initialize the pistons 
    pistonInit();

}

  // Intialize the motor
  private void motorInit(CANSparkMax motor){
    motor.restoreFactoryDefaults(); // Reset motor parameters to defaults
    motor.setIdleMode(IdleMode.kCoast); // Motor does not lose momentum when not being used
    encoderInit(motor.getEncoder());
  }

    // Intialize the encoders
    private void encoderInit(RelativeEncoder encoder){
      encoder.setVelocityConversionFactor(kVelocityConversion); // Sets encoder units to be the desired ones
      resetEncoder(encoder); 
    }
  
    // Reset encoder distance
    private void resetEncoder(RelativeEncoder encoder){
      encoder.setPosition(0);
    }
  
  // Intialize the pistons to be retracted
  private void pistonInit(){
    setRetractedAngle();
  }

  // Retract all pistons
  private void setRetractedAngle(){
    retractBottomPistons();
    retractSidePistons();
  }

  // Retract bottom pistons
  private void retractBottomPistons(){
    m_bottomAnglePiston1.set(kPistonRetractedValue);
    m_bottomAnglePiston2.set(kPistonRetractedValue);
  }

  // Extends bottom pistons
  private void extendBottomPistons(){
    m_bottomAnglePiston1.set(kPistonExtendedValue);
    m_bottomAnglePiston2.set(kPistonExtendedValue);
  }

  // Retract side pistons
  private void retractSidePistons(){
    m_sideAnglePiston1.set(kPistonRetractedValue);
    m_sideAnglePiston2.set(kPistonRetractedValue);
  }

  // Extend side pistons
  private void extendSidePistons(){
    m_sideAnglePiston1.set(kPistonExtendedValue);
    m_sideAnglePiston2.set(kPistonExtendedValue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}
