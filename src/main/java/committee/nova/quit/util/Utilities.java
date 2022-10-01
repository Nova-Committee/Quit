package committee.nova.quit.util;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.realmsclient.RealmsMainScreen;
import committee.nova.quit.screen.QuitConfirmScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Utilities {
    public static BiFunction<Minecraft, Button, QuitConfirmScreen> quit2Title = (mc, button) -> new QuitConfirmScreen(yes -> {
        if (yes) {
            quitToTitle(mc, button);
        } else mc.setScreen(new PauseScreen(true));
    }, Component.translatable("menu.quit.quitLevel"));

    public static Function<Minecraft, QuitConfirmScreen> quitGame = mc -> new QuitConfirmScreen(yes -> {
        if (yes) {
            mc.stop();
        } else mc.popGuiLayer();
    }, Component.translatable("menu.quit.quitGame"));

    public static void quitToTitle(Minecraft mc, Button button) {
        boolean flag = mc.isLocalServer();
        boolean flag1 = mc.isConnectedToRealms();
        button.active = false;
        final var level = mc.level;
        if (level != null) level.disconnect();
        mc.clearLevel(flag ? new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")) : new ProgressScreen(true));
        final var titleScreen = new TitleScreen();
        mc.setScreen(flag ? titleScreen : (flag1 ? new RealmsMainScreen(titleScreen) : new JoinMultiplayerScreen(titleScreen)));
    }

    public static boolean isAnyKeyDown(int... keys) {
        return Arrays.stream(keys).anyMatch(key -> InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key));
    }
}
