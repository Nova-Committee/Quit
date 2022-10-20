package committee.nova.quit.mixin;

import committee.nova.quit.client.Quit;
import committee.nova.quit.util.Utilities;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {
    protected MixinTitleScreen(Text title) {
        super(title);
    }

    @Redirect(method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/TitleScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",
                    ordinal = 2
            ))
    public Element onInit(TitleScreen instance, Element guiEventListener) {
        final int l = this.height / 4 + 48;
        return this.addDrawableChild(new ButtonWidget(this.width / 2 + 2, l + 72 + 12, 98, 20, Text.translatable("menu.quit"), (button) -> {
            final var mc = this.client;
            if (mc == null) return;
            if (isAnyKeyDown(KeyBindingHelper.getBoundKeyOf(Quit.bossKey()).getCode(), KeyBindingHelper.getBoundKeyOf(Quit.fastQuit()).getCode()))
                mc.stop();
            mc.setScreen(Utilities.quitGame.apply(mc));
        }));
    }
}
