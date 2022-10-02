package committee.nova.quit.util

import committee.nova.quit.client.key.KeyInit
import org.lwjgl.input.Keyboard

object Utilities {
  var isClosing = false

  private def isAnyKeyDown(keys: Int*): Boolean = {
    keys.foreach(id => {
      if (Keyboard.isKeyDown(id)) return true
    })
    false
  }

  def direct: Boolean = isAnyKeyDown(KeyInit.fastQuit.getKeyCode, KeyInit.bossKey.getKeyCode)

  def boss: Boolean = isAnyKeyDown(KeyInit.bossKey.getKeyCode)

  def fast: Boolean = isAnyKeyDown(KeyInit.fastQuit.getKeyCode)
}
