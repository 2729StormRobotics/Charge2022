// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.VisionConstants.*;

public class Vision extends SubsystemBase {

  // Creates a new table to access shuffleboard
  private final NetworkTable m_limelightTable;
  
  private final NetworkTableEntry m_targetDistance;
  private final NetworkTableEntry m_targetDetected;

  private double m_xOffset; // horizontal offset from crosshair to target (-27 degrees to 27 degrees)
  private double m_yOffset; // vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
  private double m_percentArea; // target area
  private double m_targetValue; // whether the limelight has an valid targets (0 or 1)

  /** Creates a new Vision. */
  public Vision() {
    // Instantiate the network table
    m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    // Initialize the network table entries for target distance and target detected
    m_targetDistance = m_limelightTable.getEntry("Target Distance");
    m_targetDetected = m_limelightTable.getEntry("Target Detected");

    // Set the limelight to default pipeline 
    setPipeline(kdefaultPipeline);

    turnOnLED();

  }

  // Returns the horizontal offset to the target 
  public double getXOffset(){
    return m_xOffset;
  }

  // Returns the vertical offset to the target 
  public double getYOffset(){
    return m_yOffset;
  }

  public double getpercentArea(){
    return m_percentArea;
  }

  public double getTargetValue(){
    return m_targetValue;
  }
  
  public boolean isTargetDetected(){
    return (m_targetValue > 0.0);
  }

  public boolean isHorizontallyAligned() {
    return (isTargetDetected() && m_xOffset < khorizontalRange && m_xOffset > -khorizontalRange);
  }

  // Returns the distance along the floor from the robot to the target on the upper hub 
  public double getHorizontalDistanceToUpperHub(){
    return ((kUpperTargetHeight - klimelightHeight) / Math.tan(Math.toRadians(getTargetAngle())));
  }

  public double getTargetAngle(){
    return (klimelightAngle + m_yOffset);
  }

  public double getTargetArea(){
    return m_percentArea;
  }

  // Sets which pipeline to use on the limelight 
  public void setPipeline(int pipeline){
    if (pipeline < 0){
      pipeline = 0;
    }
    else if (pipeline > 9){
      pipeline = 9;
    }

    m_limelightTable.getEntry("pipeline").setNumber(pipeline);
  }

  // Returns the value of the pipeline to the network table
  public double getPipeline(){
    return m_limelightTable.getEntry("Pipeline").getDouble(0.0);
  }

  public void turnOnLED(){
    m_limelightTable.getEntry("LED Mode").setNumber(3);
  }

  public void turnOffLED(){
    m_limelightTable.getEntry("LED Mode").setNumber(1);
  }

  public void updateLimeLight(){
    m_xOffset = m_limelightTable.getEntry("tx").getDouble(0.0);
    m_yOffset = m_limelightTable.getEntry("ty").getDouble(0.0);
    m_percentArea = m_limelightTable.getEntry("ta").getDouble(0.0);
    m_targetValue = m_limelightTable.getEntry("tv").getDouble(0.0);

    m_targetDistance.setDouble(getHorizontalDistanceToUpperHub());
    m_targetDetected.setBoolean(isTargetDetected());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateLimeLight();
    // SmartDashboard.putNumber("x offset", m_xOffset);
    SmartDashboard.putBoolean("Limelight Aligned", isHorizontallyAligned());
  }
}
