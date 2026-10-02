from commands2 import Subsystem, Command
from rev import SparkBaseConfig, SparkLowLevel
from lib import logger, telemetry, utils
from lib.classes import Position
import core.constants as constants

class Launcher(Subsystem):
  def __init__(self) -> None:
    super().__init__()
    self._constants = constants.Subsystems.Launcher

    self._telemetryName = "Robot/Subsystems/Launcher"

    self._catapultLeft = utils.getSparkController(16, SparkLowLevel.SparkModel.kSparkMax, SparkLowLevel.MotorType.kBrushless)
    sparkConfig = SparkBaseConfig()
    (sparkConfig
      .smartCurrentLimit(200)
      .setIdleMode(SparkBaseConfig.IdleMode.kBrake)
      .inverted(False)
    )
    (sparkConfig.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0)
    )
    (sparkConfig.softLimit
      .reverseSoftLimitEnabled(True)
      .reverseSoftLimit(-0.1)
      .forwardSoftLimitEnabled(True)
      .forwardSoftLimit(9.0)
    )
    utils.configureSparkController(self._catapultLeft, sparkConfig)
    self._catapultLeft.getEncoder().setPosition(0)

    self._catapultRight = utils.getSparkController(17, SparkLowLevel.SparkModel.kSparkMax, SparkLowLevel.MotorType.kBrushless)
    sparkConfig = SparkBaseConfig()
    (sparkConfig
      .smartCurrentLimit(200)
      .setIdleMode(SparkBaseConfig.IdleMode.kBrake)
      .inverted(True)
    )
    (sparkConfig.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0)
    )
    (sparkConfig.softLimit
      .reverseSoftLimitEnabled(True)
      .reverseSoftLimit(-0.1)
      .forwardSoftLimitEnabled(True)
      .forwardSoftLimit(8.0)
    )
    utils.configureSparkController(self._catapultRight, sparkConfig)
    self._catapultRight.getEncoder().setPosition(0)

  def periodic(self) -> None:
    self._updateTelemetry()

  def run_(self, position: Position) -> Command:
    return self.startEnd(
      lambda: self._launch(position),
      lambda: self._reload(position)
    )

  def _launch(self, position: Position) -> None:
    match position:
      case Position.LEFT:
        self._catapultLeft.set(0.5)
      case Position.RIGHT:
        self._catapultRight.set(0.5)
      case _:
        pass

  def _reload(self, position: Position) -> None:
    match position:
      case Position.LEFT:
        self._catapultLeft.set(-0.1)
      case Position.RIGHT:
        self._catapultRight.set(-0.1)
      case _:
        pass

  def reset(self) -> None:
    self._catapultLeft.stopMotor()
    self._catapultRight.stopMotor()

  def _updateTelemetry(self) -> None:
    pass