package committee.nova.quit.mixin;

import com.mojang.realmsclient.RealmsMainScreen;
import committee.nova.quit.client.ClientInit;
import committee.nova.quit.screen.QuitConfirmScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;

@Mixin(PauseScreen.class)
public abstract class MixinPauseScreen extends Screen {
    protected MixinPauseScreen(Component title) {
        super(title);
    }

    @Redirect(method = "createPauseMenu",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/PauseScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
                    ordinal = 7
            ))
    public GuiEventListener onCreate(PauseScreen instance, GuiEventListener guiEventListener) {
        final var mc = this.minecraft;
        assert mc != null;
        final var component = mc.isLocalServer() ? new TranslatableComponent("menu.returnToMenu") : new TranslatableComponent("menu.disconnect");
        return this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 - 16, 204, 20, component, (button) -> {
            if (isAnyKeyDown(ClientInit.bossKey.getKey().getValue())) {
                mc.stop();
                return;
            }
            if (isAnyKeyDown(ClientInit.fastQuit.getKey().getValue())) {
                quitToTitle(mc, button);
                return;
            }
            mc.setScreen(new QuitConfirmScreen(yes -> {
                if (yes) {
                    quitToTitle(mc, button);
                } else mc.setScreen(new PauseScreen(true));
            }, new TranslatableComponent("menu.quit.quitLevel")));
        }));
    }

    private void quitToTitle(Minecraft mc, Button button) {
        boolean flag = mc.isLocalServer();
        boolean flag1 = mc.isConnectedToRealms();
        button.active = false;
        final var level = mc.level;
        if (level != null) level.disconnect();
        mc.clearLevel(flag ? new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")) : new ProgressScreen(true));
        final var titleScreen = new TitleScreen();
        mc.setScreen(flag ? titleScreen : (flag1 ? new RealmsMainScreen(titleScreen) : new JoinMultiplayerScreen(titleScreen)));
    }
}
