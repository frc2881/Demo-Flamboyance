// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Catapult.kCurrentLimit;
import static frc.robot.Constants.Catapult.kEjectVoltage;
import static frc.robot.Constants.Catapult.kForwardLimitLeft;
import static frc.robot.Constants.Catapult.kLeftMotor;
import static frc.robot.Constants.Catapult.kResetPosition;
import static frc.robot.Constants.Catapult.kResetVoltage;
import static frc.robot.Constants.Catapult.kReverseLimit;
import static frc.robot.Constants.Catapult.kShootVoltage;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LeftCatapult extends SubsystemBase {
  private final CANSparkMax m_catapult;
  private final RelativeEncoder m_encoder;

  public LeftCatapult() {
    m_catapult = new CANSparkMax(kLeftMotor, MotorType.kBrushless);
    m_catapult.restoreFactoryDefaults();
    m_catapult.setInverted(false);
    m_catapult.setIdleMode(IdleMode.kBrake);
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    m_catapult.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,
                            (float)kForwardLimitLeft);
    m_catapult.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,
                            (float)kReverseLimit);
    m_catapult.setSmartCurrentLimit(kCurrentLimit);

    m_encoder = m_catapult.getEncoder();
    m_encoder.setPosition(0);

    SmartDashboard.setDefaultNumber("CatapultAdjust", 0.0);
  }

  public void reset() {
    run(0.0);
  }

  public void disableEncoderSoftLimit() {
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
  }

  public void enableEncoderSoftLimit() {
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    m_catapult.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
  }

  public void resetEncoder() {
    m_encoder.setPosition(kResetPosition);
  }

  public boolean reachedUpperSoftLimit() {
    return (kForwardLimitLeft - m_encoder.getPosition()) < 0.25;
  }

  public boolean reachedLowerSoftLimit() {
    return Math.abs(kReverseLimit - m_encoder.getPosition()) < 0.1;
  }

  public void run(double voltage) {
    m_catapult.setVoltage(voltage);
  }

  public void score() {
    m_catapult.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,
                            (float)(kForwardLimitLeft +
                            SmartDashboard.getNumber("CatapultAdjust", 0.0)));
    run(kShootVoltage);
  }

  public void eject() {
    m_catapult.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward,
                            (float)kForwardLimitLeft);
    run(kEjectVoltage);
  }

  public void stop() {
    run(0);
  }

  public void down() {
    run(kResetVoltage);
  }
}
