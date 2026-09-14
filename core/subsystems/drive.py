from typing import Callable
from commands2 import Subsystem, Command
from wpilib.drive import DifferentialDrive
from wpimath import units
from wpimath.filter import SlewRateLimiter
from phoenix5 import WPI_TalonSRX, NeutralMode, InvertType
from lib import logger, utils
import core.constants as constants

class Drive(Subsystem):
  def __init__(
      self
    ) -> None:
    super().__init__()
    self._constants = constants.Subsystems.Drive

    self._leftFrontMotor = WPI_TalonSRX(0)
    self._leftFrontMotor.setNeutralMode(NeutralMode.Brake)
    self._leftFrontMotor.configContinuousCurrentLimit(60)
    self._leftFrontMotor.configPeakCurrentLimit(0)
    self._leftFrontMotor.enableCurrentLimit(True)
    self._leftFrontMotor.configOpenloopRamp(0.08)

    self._rightFrontMotor = WPI_TalonSRX(1)
    self._rightFrontMotor.setNeutralMode(NeutralMode.Brake)
    self._rightFrontMotor.configContinuousCurrentLimit(60)
    self._rightFrontMotor.configPeakCurrentLimit(0)
    self._rightFrontMotor.enableCurrentLimit(True)
    self._rightFrontMotor.configOpenloopRamp(0.08)
    self._rightFrontMotor.setInverted(InvertType.InvertMotorOutput)

    self._leftRearMotor = WPI_TalonSRX(2)
    self._leftRearMotor.setNeutralMode(NeutralMode.Brake)
    self._leftRearMotor.configContinuousCurrentLimit(60)
    self._leftRearMotor.configPeakCurrentLimit(0)
    self._leftRearMotor.enableCurrentLimit(True)
    self._leftRearMotor.configOpenloopRamp(0.08)
    self._leftRearMotor.follow(self._leftFrontMotor)
    
    self._rightRearMotor = WPI_TalonSRX(3)
    self._rightRearMotor.setNeutralMode(NeutralMode.Brake)
    self._rightRearMotor.configContinuousCurrentLimit(60)
    self._rightRearMotor.configPeakCurrentLimit(0)
    self._rightRearMotor.enableCurrentLimit(True)
    self._rightRearMotor.configOpenloopRamp(0.08)
    self._rightFrontMotor.setInverted(InvertType.InvertMotorOutput)
    self._rightRearMotor.follow(self._rightFrontMotor)

    self._drivetrain = DifferentialDrive(
      self._leftFrontMotor,
      self._rightFrontMotor
    )

    self._drivetrain.setExpiration(0.1)

    self._translationInputLimiter = SlewRateLimiter(self._constants.INPUT_RATE_LIMIT_DEMO)
    self._rotationInputLimiter = SlewRateLimiter(self._constants.INPUT_RATE_LIMIT_DEMO)

  def periodic(self) -> None:
    self._updateTelemetry()

  def drive(self, getTranslationInput: Callable[[], units.percent], getRotationInput: Callable[[], units.percent]) -> Command:
    return self.run(
      lambda: self._runDrive(getTranslationInput(), getRotationInput())
    ).withName("Drive:Drive")

  def _runDrive(self, translationInput: units.percent, rotationInput: units.percent) -> None:
    translationInput = self._translationInputLimiter.calculate(translationInput * self._constants.INPUT_LIMIT_DEMO) if translationInput != 0 else 0
    rotationInput = self._rotationInputLimiter.calculate(rotationInput * self._constants.INPUT_LIMIT_DEMO) if rotationInput != 0 else 0

    self._drivetrain.arcadeDrive(translationInput, rotationInput, False)

  def reset(self) -> None:
    self._drivetrain.arcadeDrive(0, 0)
  
  def _updateTelemetry(self) -> None:
    pass
