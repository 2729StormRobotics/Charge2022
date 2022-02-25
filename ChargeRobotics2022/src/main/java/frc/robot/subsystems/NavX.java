// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NavX extends SubsystemBase {
  /** Creates a new NavX. */
      public final AHRS m_navX;

  public NavX() {
    m_navX = new AHRS(SerialPort.Port.kUSB2);

    m_navX.zeroYaw();
    System.out.println("ZERO NavX");


  }


  @Override
  public void periodic() {
  
    // This method will be called once per scheduler run


  }

  // Gets the angle from the navX
  public float getAngleNavX() {
    System.out.println(m_navX.getYaw());
    return m_navX.getYaw();

  }

  // Resets the angle reading
  public void resetYawNavX(){
    m_navX.zeroYaw();
    System.out.println("YAW IS ZEROED");
  }

}

}

