package committee.nova.quit.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Quit implements ClientModInitializer {
    private static KeyBinding fastQuit;
    private static KeyBinding bossKey;

    @Override
    public void onInitializeClient() {
        fastQuit = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.quit.fast_quit", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.categories.quit"));
        bossKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.quit.boss_key", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.categories.quit"));
    }

    public static KeyBinding bossKey() {
        return bossKey;
    }

    public static KeyBinding fastQuit() {
        return fastQuit;
    }
}
