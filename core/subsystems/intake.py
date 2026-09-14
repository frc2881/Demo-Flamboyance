from commands2 import Subsystem, Command
from wpilib import Compressor, Solenoid, PneumaticsModuleType
from phoenix5 import WPI_TalonSRX, NeutralMode
from lib import logger, utils
import core.constants as constants

class Intake(Subsystem):
  def __init__(self) -> None:
    super().__init__()
    self._constants = constants.Subsystems.Intake

    self._rollers = WPI_TalonSRX(5)
    self._rollers.setNeutralMode(NeutralMode.Brake)
    self._rollers.configContinuousCurrentLimit(30)
    self._rollers.configPeakCurrentLimit(0)
    self._rollers.enableCurrentLimit(True)

    self._compressor = Compressor(PneumaticsModuleType.CTREPCM)
    self._compressor.enableDigital()

    self._solenoid = Solenoid(PneumaticsModuleType.CTREPCM, 0)

  def periodic(self) -> None:
    self._updateTelemetry()

  def run_(self) -> Command:
    return self.startEnd(
      lambda: self._run(),
      lambda: self.reset()
    )

  def retract(self) -> Command:
    return self.runOnce(
      lambda: self._retract()
    )

  def _run(self) -> None:
    self._extend()
    self._rollers.set(0.75)

  def _extend(self) -> None:
    self._solenoid.set(True)

  def _retract(self) -> None:
    self._solenoid.set(False)
    self._rollers.set(0)

  def isExtended(self) -> bool:
    return self._solenoid.get()

  def reset(self) -> None:
    self._rollers.stopMotor()

  def _updateTelemetry(self) -> None:
    pass