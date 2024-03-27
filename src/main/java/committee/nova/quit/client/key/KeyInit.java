package committee.nova.quit.client.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyInit {
    public static final KeyBinding fastQuit = new KeyBinding(
            "key.quit.fastQuit",
            KeyConflictContext.GUI, Keyboard.KEY_Q,
            "key.categories.quit"
    );
    public static final KeyBinding bossKey = new KeyBinding(
            "key.quit.bossKey",
            KeyConflictContext.GUI, Keyboard.KEY_B,
            "key.categories.quit"
    );

    public static void init() {
        ClientRegistry.registerKeyBinding(fastQuit);
        ClientRegistry.registerKeyBinding(bossKey);
    }
}
