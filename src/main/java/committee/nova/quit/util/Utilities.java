package committee.nova.quit.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

import java.util.Arrays;

public class Utilities {
    public static boolean isAnyKeyDown(int... keys) {
        return Arrays.stream(keys).anyMatch(key -> InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key));
    }
}
