// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Intake.kCurrentLimit;
import static frc.robot.Constants.Intake.kMaxSpeed;
import static frc.robot.Constants.Intake.kMotor;
import static frc.robot.Constants.Intake.kSolenoid;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX m_intake = new WPI_TalonSRX(kMotor);
  private final Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, kSolenoid);
  private boolean shootExtend = false;
  private boolean buttonExtend = false;

  /** Creates a new Intake. */
  public Intake() {
    m_intake.setNeutralMode(NeutralMode.Brake);
    m_intake.configContinuousCurrentLimit(kCurrentLimit);
    m_intake.configPeakCurrentLimit(0);
    m_intake.enableCurrentLimit(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void reset() {
    run(0.0);
    shootExtend = false;
    buttonExtend = false;
    retract();
  }

  public void run(double speed) {
    if(speed > kMaxSpeed) {
      speed = kMaxSpeed;
    }
    m_intake.set(-speed);
  }

  public void runReverse(double speed) {
    if(speed > kMaxSpeed) {
      speed = kMaxSpeed;
    }
    m_intake.set(speed);
  }

  public void extend() {
    buttonExtend = true;
    m_solenoid.set(true);
  }

  public void retract() {
    buttonExtend = false;
    if(shootExtend == false) {
      m_solenoid.set(false);
    }
  }

  public void shootExtend() {
    shootExtend = true;
    m_solenoid.set(true);
  }

  public void shootRetract() {
    shootExtend = false;
    if(buttonExtend == false) {
      m_solenoid.set(false);
    }
  }
}
