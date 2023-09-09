// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.commands.catapult;

import static frc.robot.Constants.Catapult.kResetTimeout;
import static frc.robot.Constants.Catapult.kShootTimeout;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LeftCatapult;

public class ScoreLeft extends SequentialCommandGroup {
  public ScoreLeft(LeftCatapult leftCatapult, Intake intake) {
    addCommands(new InstantCommand(() -> intake.shootExtend(), intake),
                new ShootLeft(leftCatapult).withTimeout(kShootTimeout),
                new ResetLeft(leftCatapult).withTimeout(kResetTimeout),
                new InstantCommand(() -> intake.shootRetract(), intake));
  }
}
