from wpilib import AddressableLED
from lib import logger, utils
from core.classes import LightsMode

class Lights():
  def __init__(
      self
    ) -> None:

    self._ledChannel = 5
    self._ledLength = 146

    self._rainbowFirstPixelHue = 0

    self._led = AddressableLED(self._ledChannel)
    self._ledData = [AddressableLED.LEDData() for _ in range(self._ledLength)]
    self._led.setLength(self._ledLength)
    self._led.setData(self._ledData)
    self._led.start()

    utils.addRobotPeriodic(self._periodic)

  def _periodic(self) -> None:
    self._updateLights()

  def _updateLights(self) -> None:
    for i in range(self._ledLength):
      hue = (self._rainbowFirstPixelHue + (i * 180 / self._ledLength)) % 180
      self._ledData[i].setHSV(int(hue), 255, 255)
    self._rainbowFirstPixelHue += 3
    self._rainbowFirstPixelHue %= 180