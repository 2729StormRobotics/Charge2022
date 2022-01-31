// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.VisionConstants.*;

public class Vision extends SubsystemBase {


  // Creates a new table to access shuffleboard
  private final NetworkTable m_limelightTable;
  private final NetworkTableEntry m_targetDetected;
  private final NetworkTableEntry m_targetDistance;

  private double m_xOffset; // horizontal offset from crosshair to target 
  private double m_yOffset; // vertical offset from crosshair to target
  private double m_percentArea; // target area
  private double m_targetValue; // whether the limelight has an valid targets 

  /** Creates a new Vision. */
  public Vision() {
    // Instantiate the network table
    m_limelightTable = NetworkTableInstance.getDefault().getTable("Limelight");

    m_targetDetected = m_limelightTable.getEntry("Target Dected");
    m_targetDistance = m_limelightTable.getEntry("Target Distance");

  }

  public boolean isTargetDetected(){
    return (m_targetValue > 0.0);
  }

  public boolean isHorizontallyAligned()
  {
    return (isTargetDetected() && m_xOffset > -k_horizontalRange && m_xOffset < k_horizontalRange);
  }

  public double getTargetAngle(){
    return (k_limelightAngle + m_yOffset);
  }

  public double getTargetArea(){
    return m_percentArea;
  }

  // Returns the distance along the floor from the robot to the target on the upper hub 
  public double getHorizontalDistanceToUpperHub(){
    return ( (k_upperTargetHeight - k_limelightHeight) / Math.tan(Math.toRadians(getTargetAngle())));
  }

  public void turnOnLED(){
    m_limelightTable.getEntry("LED Mode").setNumber(1);
  }
  public void turnOffLED(){
    m_limelightTable.getEntry("LED Mode").setNumber(0);
  }

  public void updateLimeLight(){
    m_targetDistance.setDouble(getHorizontalDistanceToUpperHub());
    m_targetDetected.setBoolean(isTargetDetected());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateLimeLight();
  }
}
