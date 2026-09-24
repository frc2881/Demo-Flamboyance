from wpimath import units
from lib import logger, telemetry, utils
from lib.classes import (
  RobotType
)

class Subsystems:
  class Drive:
    INPUT_LIMIT_DEMO: units.percent = 0.5
    INPUT_RATE_LIMIT_DEMO: units.percent = 0.5

  class Intake:
    pass

  class Launcher:
    pass

class Controllers:
  DRIVER_CONTROLLER_PORT: int = 0
  OPERATOR_CONTROLLER_PORT: int = 1
  INPUT_DEADBAND: units.percent = 0.1

class Game:
  class Robot:
    TYPE = RobotType.Demo
    NAME: str = "Flamboyance (Demo)"

  class Commands:
    pass
