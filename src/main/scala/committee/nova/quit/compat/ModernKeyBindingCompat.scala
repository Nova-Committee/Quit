package committee.nova.quit.compat

import committee.nova.mkb.api.IKeyBinding
import committee.nova.mkb.keybinding.{KeyConflictContext, KeyModifier}
import committee.nova.quit.Quit
import committee.nova.quit.client.key.KeyInit
import org.lwjgl.input.Keyboard

object ModernKeyBindingCompat {
  def init(): Unit = {
    try {
      val fastQuitExtended = KeyInit.fastQuit.asInstanceOf[IKeyBinding]
      fastQuitExtended.setKeyConflictContext(KeyConflictContext.GUI)
      fastQuitExtended.setInitialKeyModifierAndCode(KeyModifier.NONE, Keyboard.KEY_Q)
      val bossKeyExtended = KeyInit.bossKey.asInstanceOf[IKeyBinding]
      bossKeyExtended.setKeyConflictContext(KeyConflictContext.GUI)
      bossKeyExtended.setInitialKeyModifierAndCode(KeyModifier.NONE, Keyboard.KEY_B)
      Quit.LOGGER.info("Modern KeyBinding compatibility succeeded!")
    } catch {
      case e: Exception => {
        Quit.LOGGER.error("Modern KeyBinding mod found but compatibility failed")
        e.printStackTrace()
      }
    }
  }
}
