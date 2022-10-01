package committee.nova.quit.mixin;

import committee.nova.quit.client.ClientInit;
import committee.nova.quit.screen.QuitConfirmScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {
    protected MixinTitleScreen(Component title) {
        super(title);
    }

    @Redirect(method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
                    ordinal = 3
            ))
    public GuiEventListener onInit(TitleScreen instance, GuiEventListener guiEventListener) {
        final int l = this.height / 4 + 48;
        return this.addRenderableWidget(new Button(this.width / 2 + 2, l + 72 + 12, 98, 20, new TranslatableComponent("menu.quit"), (button) -> {
            final var mc = this.minecraft;
            if (mc == null) return;
            if (isAnyKeyDown(ClientInit.fastQuit.getKey().getValue(), ClientInit.bossKey.getKey().getValue()))
                mc.stop();
            mc.setScreen(new QuitConfirmScreen(yes -> {
                if (yes) {
                    mc.stop();
                } else mc.popGuiLayer();
            }, new TranslatableComponent("menu.quit.quitGame")));
        }));
    }
}
