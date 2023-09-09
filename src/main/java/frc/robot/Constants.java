// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose.  All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner
 * classes) wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  /**
   * Configuration of the catapult subsystems.
   */
  public static final class Catapult {
    /**
     * The CAN ID of the left catapult motor.
     */
    public static final int kLeftMotor = 16;

    /**
     * The CAN ID of the right catapult motor.
     */
    public static final int kRightMotor = 17;

    /**
     * The color of the red cargo, as detected by the REV Color Sensor V3.
     */
    public static final Color kRedCargo = new Color(0.5720, 0.3222, 0.1062);

    /**
     * The color of the blue cargo, as detected by the REV Color Sensor V3.
     */
    public static final Color kBlueCargo = new Color(0.1436, 0.4070, 0.4499);

    /**
     * The minimum distance to the cargo in order to consider it to be present,
     * as detected by the REV Color Sensor V3.
     */
    public static final int kDistance = 200;

    /**
     * The maximum current to send to the catapult motors.
     */
    public static final int kCurrentLimit = 200;

    /**
     * The position (in rotations) that the catapult gets reset to after the
     * override sequence.
     */
    public static final double kResetPosition = -0.3;

    /**
     * The maximum distance (in rotations) that the left catapult motor can
     * move in the forward direction.  Adjusting this limit adjusts the launch
     * angle of the shot.
     */
    public static final double kForwardLimitLeft = 10.5;

    /**
     * The maximum distance (in rotations) that the right catapult motor can
     * move in the forward direction.  Adjusting this limit adjusts the launch
     * angle of the shot.
     */
    public static final double kForwardLimitRight = 9.0;

    /**
     * The minimum distance (in rotations) that the catapult motor can move in
     * the reverse direction.
     */
    public static final double kReverseLimit = 0.0;

    /**
     * The maximum amount of time to wait for the catapult to shoot a cargo.
     */
    public static final double kShootTimeout = 1.0;

    /**
     * The voltage used to shoot the cargo.
     */
    public static final double kShootVoltage = 6;

    /**
     * The maximum amount of time to wait for the catapult to eject a cargo.
     */
    public static final double kEjectTimeout = 0.25;

    /**
     * The voltage used to eject the cargo.
     */
    public static final double kEjectVoltage = 5.5;

    /**
     * The maximum amount of time to wait for the catapult to reset.
     */
    public static final double kResetTimeout = 2.0;

    /**
     * The voltage used to reset the catapults after shooting or ejecting a
     * cargo.
     */
    public static final double kResetVoltage = -1.0;

    /**
     * The time delay between the left and right catapult.
     */
    public static final double kShootTimeDelay = 1.0;
  }

  /**
   * Configuration of the drive subsystem.
   */
  public static final class Drive {
    /**
     * The CAN ID of the left front motor.
     */
    public static final int kLeftFrontMotor = 0;

    /**
     * The CAN ID of the left rear motor.
     */
    public static final int kLeftRearMotor = 2;

    /**
     * The CAN ID of the right front motor.
     */
    public static final int kRightFrontMotor = 1;

    /**
     * The CAN ID of the right rear motor.
     */
    public static final int kRightRearMotor = 3;

    /**
     * The maximum current to send to the drive motors.
     */
    public static final int kCurrentLimit = 60;

    /**
     * The maximum rate to ramp the motors from full stop to full speed.
     */
    public static final double kRampRate = 0.08;

    /**
     * The maximum amount of time that can pass between updates of the motors
     * before the motor safety kicks in and stops the motors (helping to
     * prevent a runaway robot).
     */
    public static final double kMotorSafetyTime = 0.1;
  }

  /**
   * Configuration of the intake subsystem.
   */
  public static final class Intake {
    /**
     * The CAN ID of the intake motor.
     */
    public static final int kMotor = 5;

    /**
     * The pneumatic hub channel ID of the intake solenoid.
     */
    public static final int kSolenoid = 0;

    /**
     * The maximum current to send to the intake motor.
     */
    public static final int kCurrentLimit = 30;

    /**
     * The maximum speed that the intake motor runs.
     */
    public static final double kMaxSpeed = 0.75;
  }
}
