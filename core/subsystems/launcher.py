from commands2 import Subsystem, Command
from rev import SparkBaseConfig, SparkLowLevel, SparkMax, ResetMode, PersistMode
from lib import logger, utils
from lib.classes import Position
import core.constants as constants

class Launcher(Subsystem):
  def __init__(self) -> None:
    super().__init__()
    self._constants = constants.Subsystems.Launcher

    self._motorCatapultLeft = SparkMax(16, SparkLowLevel.MotorType.kBrushless)
    self._motorCatapultRight = SparkMax(17, SparkLowLevel.MotorType.kBrushless)

    self._motorConfig = SparkBaseConfig()
    (self._motorConfig
      .smartCurrentLimit(200)
      .setIdleMode(SparkBaseConfig.IdleMode.kBrake)
      .inverted(False))
    (self._motorConfig.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0))
    (self._motorConfig.softLimit
      .reverseSoftLimitEnabled(True)
      .reverseSoftLimit(-0.1)
      .forwardSoftLimitEnabled(True)
      .forwardSoftLimit(9.0))
    
    utils.setSparkConfig(self._motorCatapultLeft.configure(self._motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters))
    utils.setSparkConfig(self._motorCatapultRight.configure(self._motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters))

    self._motorCatapultLeft.getEncoder().setPosition(0)
    self._motorCatapultRight.getEncoder().setPosition(0)

  def periodic(self) -> None:
    self._updateTelemetry()

  def run_(self, position: Position) -> Command:
    return self.startEnd(
      lambda: self._launch(position),
      lambda: self._reload(position)
    )

  def _launch(self, position: Position) -> None:
    match position:
      case Position.Left:
        self._motorCatapultLeft.set(0.5)
      case Position.Right:
        self._motorCatapultRight.set(0.5)
      case _:
        pass

  def _reload(self, position: Position) -> None:
    match position:
      case Position.Left:
        self._motorCatapultLeft.set(-0.5)
      case Position.Right:
        self._motorCatapultRight.set(-0.5)
      case _:
        pass

  def reset(self) -> None:
    self._motorCatapultLeft.stopMotor()
    self._motorCatapultRight.stopMotor()

  def _updateTelemetry(self) -> None:
    pass