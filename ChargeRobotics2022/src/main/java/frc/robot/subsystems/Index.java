// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IndexConstants.*;

import java.util.Map;

public class Index extends SubsystemBase {

  private final CANSparkMax m_lowerIndexMotor;
  private final CANSparkMax m_upperIndexMotor;
  private final DigitalInput m_lowerBeamBraker;
  private final DigitalInput m_upperBeamBraker;

  private final ShuffleboardTab m_indexTab;
  private final ShuffleboardLayout m_indexLayout;

  /**
  Creates a new Index.
  The index system has two motors and two beam brakers.
  */

  public Index() {
    m_lowerIndexMotor = new CANSparkMax(kLowerIndexMotorPort, MotorType.kBrushless);
    m_upperIndexMotor = new CANSparkMax(kUpperIndexMotorPort, MotorType.kBrushless);
    m_lowerBeamBraker = new DigitalInput(kLowerIndexBeamBrakerPort);
    m_upperBeamBraker = new DigitalInput(kUpperIndexBeamBrakerPort);

    m_indexTab = Shuffleboard.getTab(kIndexShuffleboardTabName);
    m_indexLayout = m_indexTab.getLayout("Beam Breaks", BuiltInLayouts.kList).withProperties(Map.of("Lable position", "TOP"));
    
    shuffleboardInit();
  }

  //run lower motor
  public void runLowerIndexMotor() {
    m_lowerIndexMotor.set(kLowerIndexMotorSpeed);
  }

  //run upper motor
  public void runUpperIndexMotor() {
    m_upperIndexMotor.set(kLowerIndexMotorSpeed);
  }

  //reverse both motors
  public void ejectIndex() {
    m_lowerIndexMotor.set(kEjectIndexMotorSpeed);
    m_upperIndexMotor.set(kEjectIndexMotorSpeed);
  }

  //stop one motor
  public void stopLowerMotor() {
    m_lowerIndexMotor.set(kIndexMotorStopSpeed);
  }

  public void stopUpperMotor() {
    m_upperIndexMotor.set(kIndexMotorStopSpeed);
  }

  //stop both motors
  public void stopIndexMotors() {
    m_lowerIndexMotor.set(kIndexMotorStopSpeed);
    m_upperIndexMotor.set(kIndexMotorStopSpeed);
  }

  //returns true if there
  public boolean hasLowerBall() {
    return !m_lowerBeamBraker.get();
  }
  
  public boolean hasUpperBall() {
    return !m_upperBeamBraker.get();
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Lower BB", hasLowerBall());
    SmartDashboard.putBoolean("Upper BB", hasUpperBall());
  }

  private void shuffleboardInit(){
    m_indexLayout.add(m_lowerBeamBraker);
    m_indexLayout.add(m_upperBeamBraker);
  }
}
