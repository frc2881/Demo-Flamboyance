from wpimath import units
from lib import logger, telemetry, utils
from lib.classes import (
  RobotType,
  XboxControllerConfig
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
  DRIVER_CONTROLLER_CONFIG = XboxControllerConfig(port = 0, inputDeadband = 0.1, telemetryName = "Robot/Controllers/Driver")
  # OPERATOR_CONTROLLER_CONFIG = XboxControllerConfig(port = 1, inputDeadband = 0.1, telemetryName = "Robot/Controllers/Operator")
  INPUT_DEADBAND: units.percent = 0.1

class Game:
  class Robot:
    TYPE = RobotType.DEMO
    NAME: str = "Flamboyance (Demo)"

  class Commands:
    pass
