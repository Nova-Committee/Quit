package committee.nova.quit.client.key

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.fml.client.registry.ClientRegistry
import org.lwjgl.input.Keyboard

object KeyInit {
  val fastQuit = new KeyBinding("key.quit.fastQuit", KeyConflictContext.GUI, Keyboard.KEY_Q, "key.categories.quit")
  val bossKey = new KeyBinding("key.quit.bossKey", KeyConflictContext.GUI, Keyboard.KEY_B, "key.categories.quit")

  def init(): Unit = {
    ClientRegistry.registerKeyBinding(fastQuit)
    ClientRegistry.registerKeyBinding(bossKey)
  }
}
