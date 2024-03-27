package committee.nova.quit.util;

import committee.nova.quit.client.key.KeyInit;
import org.lwjgl.input.Keyboard;

public class Utilities {
    private static boolean isClosing = false;

    private static boolean isAnyKeyDown(int... keys) {
        for (final int key : keys) if (Keyboard.isKeyDown(key)) return true;
        return false;
    }

    public static boolean direct() {
        return isAnyKeyDown(KeyInit.fastQuit.getKeyCode(), KeyInit.bossKey.getKeyCode());
    }

    public static boolean boss() {
        return isAnyKeyDown(KeyInit.bossKey.getKeyCode());
    }

    public static boolean fast() {
        return isAnyKeyDown(KeyInit.fastQuit.getKeyCode());
    }

    public static void markClosing() {
        isClosing = true;
    }

    public static boolean isClosing() {
        return isClosing;
    }
}
