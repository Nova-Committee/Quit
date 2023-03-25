package committee.nova.quit.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyInit {
    public static final KeyBinding fastQuit = new KeyBinding("key.quit.fastQuit", Keyboard.KEY_X, "key.categories.quit");

    public static void init() {
        ClientRegistry.registerKeyBinding(fastQuit);
    }
}
