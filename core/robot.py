from wpilib import DriverStation
from commands2 import Command, cmd
from lib import logger, telemetry, utils
from lib.classes import Position
from lib.controllers.xbox import XboxController
from core.commands.auto import Auto
from core.commands.game import Game
from core.subsystems.drive import Drive
from core.subsystems.intake import Intake
from core.subsystems.launcher import Launcher
from core.services.lights import Lights
import core.constants as constants

class RobotCore:
  def __init__(self) -> None:
    self._initSensors()
    self._initSubsystems()
    self._initServices()
    self._initCommands()
    self._initControllers()
    self._initTriggers()
    self._initTelemetry()
    utils.addRobotPeriodic(self._periodic)

  def _initSensors(self) -> None:
    pass

  def _initSubsystems(self) -> None:
    self.drive = Drive()
    self.intake = Intake()
    self.launcher = Launcher()

  def _initServices(self) -> None:
    self.lights = Lights()

  def _initCommands(self) -> None:
    self.game = Game(self)
    self.auto = Auto(self)

  def _initControllers(self) -> None:
    DriverStation.silenceJoystickConnectionWarning(not utils.isCompetitionMode())
    self.driver = XboxController(constants.Controllers.DRIVER_CONTROLLER_CONFIG)
    # self.operator = XboxController(constants.Controllers.OPERATOR_CONTROLLER_CONFIG)

  def _initTriggers(self) -> None:
    self._setupDriver()
    # self._setupOperator()

  def _setupDriver(self) -> None:
    self.drive.setDefaultCommand(self.drive.drive(self.driver.getLeftY, self.driver.getRightX))
    # self.driver.leftStick().whileTrue(cmd.none())
    # self.driver.rightStick().whileTrue(cmd.none())
    self.driver.leftTrigger().and_((self.driver.x()).not_()).whileTrue(self.game.launch(Position.LEFT, 0.5))
    self.driver.leftTrigger().and_((self.driver.x())).whileTrue(self.game.launch(Position.LEFT, 1.0))
    self.driver.rightTrigger().and_((self.driver.x()).not_()).whileTrue(self.game.launch(Position.RIGHT, 0.5))
    self.driver.rightTrigger().and_((self.driver.x())).whileTrue(self.game.launch(Position.RIGHT, 1.0))
    self.driver.leftBumper().whileTrue(self.game.retractIntake())
    self.driver.rightBumper().whileTrue(self.game.runIntake())
    # self.driver.a().whileTrue(cmd.none())
    # self.driver.b().whileTrue(cmd.none())
    # self.driver.x().whileTrue(cmd.none())
    # self.driver.y().whileTrue(cmd.none())
    # self.driver.povUp().whileTrue(cmd.none())
    # self.driver.povDown().whileTrue(cmd.none())
    # self.driver.povLeft().whileTrue(cmd.none())
    # self.driver.povRight().whileTrue(cmd.none())
    # self.driver.start().onTrue(cmd.none())
    # self.driver.back().whileTrue(cmd.none())

  def _initTelemetry(self) -> None:
    telemetry.log("Game/Robot/Type", constants.Game.Robot.TYPE.name)
    telemetry.log("Game/Robot/Name", constants.Game.Robot.NAME)

  def _periodic(self) -> None:
    self._updateTelemetry()

  def disabledInit(self) -> None:
    self.reset()

  def autoInit(self) -> None:
    self.reset()

  def autoExit(self) -> None: 
    pass

  def teleopInit(self) -> None:
    self.reset()

  def testInit(self) -> None:
    self.reset()

  def simulationInit(self) -> None:
    self.reset()

  def reset(self) -> None:
    self.drive.reset()
    self.launcher.reset()
    self.intake.reset()

  def isHoming(self) -> bool:
    return False

  def isHomed(self) -> bool:
    return True

  def _updateTelemetry(self) -> None:
    telemetry.log("Robot/Status/IsHoming", self.isHoming())
    telemetry.log("Robot/Status/IsHomed", self.isHomed())
