// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveWithJoysticks extends CommandBase {
  private final DriveTrain m_driveTrain;
  private final DoubleSupplier m_speed;
  private final DoubleSupplier m_rotation;

  /** Creates a new DriveWithJoysticks. */
  public DriveWithJoysticks(DriveTrain driveTrain, DoubleSupplier speed,
                            DoubleSupplier rotation) {
    m_driveTrain = driveTrain;
    m_speed = speed;
    m_rotation = rotation;

    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveTrain.arcadeDrive(m_speed.getAsDouble(), m_rotation.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
