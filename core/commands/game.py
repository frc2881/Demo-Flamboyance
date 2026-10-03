from typing import TYPE_CHECKING
from wpilib import RobotBase
from wpimath import units
from commands2 import Command, cmd
from lib import logger, telemetry, utils
from lib.classes import Position, ControllerRumbleMode, ControllerRumblePattern
import core.constants as constants
if TYPE_CHECKING: from core.robot import RobotCore

class Game:
  def __init__(self, robot: "RobotCore") -> None:
    self._robot = robot

  def runIntake(self) -> Command:
    return (
      self._robot.intake.run_()
      .withName("Game:RunIntake")
    )
  
  def retractIntake(self) -> Command:
    return (
      self._robot.intake.retract()
      .withName("Game:RetractIntake")
    ) 

  def launch(self, position: Position, speed: units.percent) -> Command:
    return ( 
      self._robot.launcher.run_(position, speed)
      .andThen(self.rumbleControllers(ControllerRumbleMode.DRIVER))
      .onlyIf(lambda: self._robot.intake.isExtended())
      .withName(f'Game:Launch:{ position.name }')
    )

  def rumbleControllers(
    self, 
    mode: ControllerRumbleMode = ControllerRumbleMode.BOTH, 
    pattern: ControllerRumblePattern = ControllerRumblePattern.SHORT
  ) -> Command:
    return cmd.parallel(
      self._robot.driver.rumble(pattern).onlyIf(lambda: mode != ControllerRumbleMode.OPERATOR),
      # self._robot.operator.rumble(pattern).onlyIf(lambda: mode != ControllerRumbleMode.DRIVER)
    ).onlyIf(
      lambda: RobotBase.isReal() and not utils.isAutonomousMode()
    ).withName(f'Game:RumbleControllers:{ mode.name }:{ pattern.name }')
