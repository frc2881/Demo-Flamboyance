// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Drive.kCurrentLimit;
import static frc.robot.Constants.Drive.kLeftFrontMotor;
import static frc.robot.Constants.Drive.kLeftRearMotor;
import static frc.robot.Constants.Drive.kMotorSafetyTime;
import static frc.robot.Constants.Drive.kRampRate;
import static frc.robot.Constants.Drive.kRightFrontMotor;
import static frc.robot.Constants.Drive.kRightRearMotor;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private final WPI_TalonSRX m_leftFront = new WPI_TalonSRX(kLeftFrontMotor);
  private final WPI_TalonSRX m_leftRear = new WPI_TalonSRX(kLeftRearMotor);
  private final WPI_TalonSRX m_rightFront = new WPI_TalonSRX(kRightFrontMotor);
  private final WPI_TalonSRX m_rightRear = new WPI_TalonSRX(kRightRearMotor);
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftFront, m_rightFront);

  /** Creates a new ExampleSubsystem. */
  public DriveTrain() {
    m_leftFront.setNeutralMode(NeutralMode.Brake);
    m_leftFront.configContinuousCurrentLimit(kCurrentLimit);
    m_leftFront.configPeakCurrentLimit(0);
    m_leftFront.enableCurrentLimit(true);
    m_leftFront.configOpenloopRamp(kRampRate);
  
    m_leftRear.setNeutralMode(NeutralMode.Brake);
    m_leftRear.configContinuousCurrentLimit(kCurrentLimit);
    m_leftRear.configPeakCurrentLimit(0);
    m_leftRear.enableCurrentLimit(true);
    m_leftRear.configOpenloopRamp(kRampRate);
    m_leftRear.follow(m_leftFront);
  
    m_rightFront.setNeutralMode(NeutralMode.Brake);
    m_rightFront.configContinuousCurrentLimit(kCurrentLimit);
    m_rightFront.configPeakCurrentLimit(0);
    m_rightFront.enableCurrentLimit(true);
    m_rightFront.configOpenloopRamp(kRampRate);
    m_rightFront.setInverted(InvertType.InvertMotorOutput);

    m_rightRear.setNeutralMode(NeutralMode.Brake);
    m_rightRear.configContinuousCurrentLimit(kCurrentLimit);
    m_rightRear.configPeakCurrentLimit(0);
    m_rightRear.enableCurrentLimit(true);
    m_rightRear.configOpenloopRamp(kRampRate);
    m_rightRear.setInverted(InvertType.InvertMotorOutput);
    m_rightRear.follow(m_rightFront);

    m_drive.setExpiration(kMotorSafetyTime);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }

  public void tankDrive(double left, double right) {
    m_drive.tankDrive(left, right);
  }
}
