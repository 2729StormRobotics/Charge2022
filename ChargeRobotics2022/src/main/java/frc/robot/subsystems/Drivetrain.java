// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants.*;

public class Drivetrain extends SubsystemBase {

  /** Creates a new Drivetrain. */

  // Declare Motor Variables
  private final CANSparkMax m_leftLeaderMotor;
  private final CANSparkMax m_rightLeaderMotor;
  private final CANSparkMax m_leftFollowerMotor;
  private final CANSparkMax m_rightFollowerMotor;

  // Declare Encoder Variables
  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  private final DifferentialDrive m_drive;



  private final SimpleMotorFeedforward m_leftFeedforward;
  private final SimpleMotorFeedforward m_rightFeedforward;

  private final SparkMaxPIDController m_leftPIDController;
  private final SparkMaxPIDController m_rightPIDController;

  private final DifferentialDriveKinematics m_kinematics;



  public Drivetrain() {

    // Set Motor Ports and Motor Type
    m_leftLeaderMotor = new CANSparkMax(kLeftLeaderMotorPort, MotorType.kBrushless);
    m_rightLeaderMotor = new CANSparkMax(kRightLeaderMotorPort, MotorType.kBrushless);
    m_leftFollowerMotor = new CANSparkMax(kLeftFollowerMotorPort, MotorType.kBrushless);
    m_rightFollowerMotor = new CANSparkMax(kRightFollowerMotorPort, MotorType.kBrushless);

    // Initialize Motors
    motorInit(m_leftLeaderMotor, kLeftLeaderMotorReversedDefault);
    motorInit(m_rightLeaderMotor, kRightLeaderMotorReversedDefault);
    motorInit(m_leftFollowerMotor, kLeftFollowerMotorReversedDefault);
    motorInit(m_rightFollowerMotor, kRightFollowerMotorReversedDefault);

    // Set Motor Followers
    m_leftFollowerMotor.follow(m_leftLeaderMotor);
    m_rightFollowerMotor.follow(m_rightLeaderMotor);

    // Set Encoder to Leader Motor Encoder
    m_leftEncoder = m_leftLeaderMotor.getEncoder();
    m_rightEncoder = m_rightLeaderMotor.getEncoder();

    m_drive = new DifferentialDrive(m_leftLeaderMotor, m_rightLeaderMotor);

    // Set The Feedforward Values
    m_leftFeedforward = new SimpleMotorFeedforward(kLeftS, kLeftV, kLeftA);
    m_rightFeedforward = new SimpleMotorFeedforward(kRightS, kRightV, kRightA);
    
    // Get PIDController From SparkMax
    m_leftPIDController = m_leftLeaderMotor.getPIDController();
    m_rightPIDController = m_rightLeaderMotor.getPIDController();

    m_kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(22.));





  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // Initializes Motors by Setting Defaults
  private void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults(); 
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(kCurrentLimit);
    motor.setInverted(invert);
  }

  public void encoderInit() {
    m_leftEncoder.setPositionConversionFactor(kDriveDistancePerRev);
    m_leftEncoder.setVelocityConversionFactor(kDriveSpeedPerRev);

    m_rightEncoder.setPositionConversionFactor(kDriveDistancePerRev);
    m_rightEncoder.setVelocityConversionFactor(kDriveSpeedPerRev);
  }
  // Reset Encoder
  private void encoderReset(RelativeEncoder encoder){
    encoder.setPosition(0);
  }

  // Reset Left Encoder
  public void leftEncoderReset(){
    encoderReset(m_leftEncoder);
  }

  // Reset Right Encoder
  public void rightEncoderReset(){
    encoderReset(m_rightEncoder);
  }

  // Reset all Encoders 
  public void resetAllEncoders(){
    encoderReset(m_leftEncoder);
    encoderReset(m_rightEncoder);
  }

  // Get the position of the left encoders
  public double getLeftDistance(){
    return m_leftEncoder.getPosition();
  }

  // Get the position of the right encoders
  public double getRightDistance(){
    return m_rightEncoder.getPosition();
  }

  // Averages the left and right encoder distance
  public double getAverageDistance(){
   double distLeft = getLeftDistance();
   double distRight = getRightDistance();
   
   encoderInit();
   printPositionConversionFactor();   
   
   
   System.out.println("Left:  " + distLeft);
    System.out.println("Right:  " + distRight);
    System.out.println("velocity:  " + getAverageVelocity());
    
  
    return (distLeft + distRight) / 2;

  }

  // feedback of encoder conversion factor on the Driver Station Console
  public void printPositionConversionFactor() {
    System.out.println("Left Conversion Factor:  " + m_leftEncoder.getPositionConversionFactor());
    System.out.println("Right Conversion Factor:  " + m_rightEncoder.getPositionConversionFactor());
  }

  // Get the velocity of the left encoder
  private double getLeftSpeed(){
    return m_leftEncoder.getVelocity();
  }

  // Get the velocity of the right encoder
  private double getRightSpeed(){
    return m_rightEncoder.getVelocity();
  }

  public double getAverageVelocity(){
    return (getLeftSpeed() + getRightSpeed()) / 2;
  }


  // Drives Using Tank Drive
  public void tankDrive(double leftPower, double rightPower, boolean squareInputs){
    // double outputLeft = m_leftPIDController.calculate(getLeftSpeed(), setpoint);
    // double outputRight = pid.calculate(encoder.getDistance(), setpoint);
    m_drive.tankDrive(leftPower, rightPower, squareInputs);

  }
  
  // Drives Using Arcade Drive
  public void arcadeDrive(double speed, double turn, boolean squareInputs){
    m_drive.arcadeDrive(speed, turn, squareInputs);

    SmartDashboard.putNumber("forward power", speed);

  }
  // Stop All Drive Motors
  public void stopDrive(){
    m_drive.tankDrive(0, 0);
  }
  // Set Distance PID Values for Left Motors
  public void setLeftDistancePID(){
    m_leftPIDController.setP(kLeftP);
    m_leftPIDController.setI(kLeftI);
    m_leftPIDController.setD(kLeftD);
  }

    // Set Distance PID Values for Right Motors
  public void setRightDistancePID(){
    m_rightPIDController.setP(kRightP);
    m_rightPIDController.setI(kRightI);
    m_rightPIDController.setD(kRightD);
  
  }

  // Combine Right and Left Distance PID Values
  public void setDistancePID(){
    setLeftDistancePID();
    setRightDistancePID();
  }

  
  

}