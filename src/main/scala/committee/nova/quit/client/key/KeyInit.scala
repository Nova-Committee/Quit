package committee.nova.quit.client.key

import cpw.mods.fml.client.registry.ClientRegistry
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

object KeyInit {
  val fastQuit = new KeyBinding("key.quit.fastQuit", Keyboard.KEY_X, "key.categories.quit")
  val bossKey = new KeyBinding("key.quit.bossKey", Keyboard.KEY_B, "key.categories.quit")

  def init(): Unit = {
    ClientRegistry.registerKeyBinding(fastQuit)
    ClientRegistry.registerKeyBinding(bossKey)
  }
}
