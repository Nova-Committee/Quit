package committee.nova.quit.util;

import committee.nova.quit.screen.QuitConfirmScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Utilities {
    public static BiFunction<MinecraftClient, ButtonWidget, QuitConfirmScreen> quit2Title = (mc, button) -> new QuitConfirmScreen(yes -> {
        if (yes) {
            quitToTitle(mc, button);
        } else mc.setScreen(new GameMenuScreen(true));
    }, Text.translatable("menu.quit.quit_level"));

    public static Function<MinecraftClient, QuitConfirmScreen> quitGame = mc -> new QuitConfirmScreen(yes -> {
        if (yes) {
            mc.stop();
        } else mc.setScreen(null);
    }, Text.translatable("menu.quit.quit_game"));

    public static void quitToTitle(MinecraftClient mc, ButtonWidget button) {
        boolean flag = mc.isInSingleplayer();
        boolean flag1 = mc.isConnectedToRealms();
        button.active = false;
        final var level = mc.world;
        if (level != null) level.disconnect();
        mc.disconnect(flag ? new MessageScreen(Text.translatable("menu.savingLevel")) : new ProgressScreen(true));
        final var titleScreen = new TitleScreen();
        mc.setScreen(flag ? titleScreen : (flag1 ? new RealmsMainScreen(titleScreen) : new MultiplayerScreen(titleScreen)));
    }

    public static boolean isAnyKeyDown(int... keys) {
        return Arrays.stream(keys).anyMatch(key -> InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), key));
    }
}
