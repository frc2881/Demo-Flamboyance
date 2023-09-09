// Copyright (c) 2022 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.catapult.CatapultOverride;
import frc.robot.commands.catapult.ScoreLeft;
import frc.robot.commands.catapult.ScoreRight;
import frc.robot.commands.drive.DriveWithJoysticks;
import frc.robot.commands.intake.ExtendIntake;
import frc.robot.commands.intake.RunIntake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LeftCatapult;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.PrettyLights;
import frc.robot.subsystems.RightCatapult;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Pneumatics m_pneumatics = new Pneumatics();
  private final DriveTrain m_driveTrain = new DriveTrain();
  private final Intake m_intake = new Intake();
  private final LeftCatapult m_leftCatapult = new LeftCatapult();
  private final RightCatapult m_rightCatapult = new RightCatapult();
  private final PrettyLights m_prettyLights = new PrettyLights();

  private final XboxController m_driver = new XboxController(0);
  private final XboxController m_manipulator = new XboxController(1);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_driveTrain.setDefaultCommand(
      new DriveWithJoysticks(m_driveTrain, () -> -m_driver.getLeftY(),
                             () -> m_driver.getRightX()));

    new Trigger(m_manipulator::getAButton).
      whileTrue(new ExtendIntake(m_intake));

    new Trigger(m_manipulator::getXButton).
      whileTrue(new RunIntake(m_intake));

    new Trigger(() -> m_manipulator.getLeftTriggerAxis() > 0.1).
      onTrue(new ScoreLeft(m_leftCatapult, m_intake));

    new Trigger(() -> m_manipulator.getRightTriggerAxis() > 0.1).
      onTrue(new ScoreRight(m_rightCatapult, m_intake));
                      
    new Trigger(m_manipulator::getBackButton).
      whileTrue(new CatapultOverride(m_leftCatapult, m_rightCatapult));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
