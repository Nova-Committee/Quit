package committee.nova.quit.util;

import committee.nova.quit.key.KeyInit;
import org.lwjgl.input.Keyboard;

public class Utilities {
    private static boolean isClosing = false;

    private static boolean isAnyKeyDown(int... keys) {
        for (int k : keys) if (Keyboard.isKeyDown(k)) return true;
        return false;
    }

    public static boolean fast() {
        return isAnyKeyDown(KeyInit.fastQuit.getKeyCode());
    }

    public static void close() {
        isClosing = true;
    }

    public static boolean isClosing() {
        return isClosing;
    }
}
