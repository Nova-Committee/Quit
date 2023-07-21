package committee.nova.quit.mixin;

import committee.nova.quit.client.ClientInit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
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
                    target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;"
            ))
    public Button.Builder onCreate(Component component, Button.OnPress f) {
        if (!(component.getContents() instanceof TranslatableContents t)) return new Button.Builder(component, f);
        if (!t.getKey().equals("menu.returnToMenu") && !t.getKey().equals("menu.disconnect"))
            return new Button.Builder(component, f);
        final var mc = this.minecraft;
        if (mc == null) return new Button.Builder(component, f);
        return new Button.Builder(component, b -> {
            if (isAnyKeyDown(ClientInit.bossKey.getKey().getValue())) {
                mc.stop();
                return;
            }
            if (isAnyKeyDown(ClientInit.fastQuit.getKey().getValue())) {
                f.onPress(b);
                return;
            }
            mc.setScreen(Utilities.quit2Title.apply(mc, b));
        });
    }
}
