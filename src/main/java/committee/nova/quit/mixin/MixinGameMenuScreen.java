package committee.nova.quit.mixin;

import committee.nova.quit.client.Quit;
import committee.nova.quit.util.Utilities;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;
import static committee.nova.quit.util.Utilities.quitToTitle;

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {
    protected MixinGameMenuScreen(Text title) {
        super(title);
    }

    @Redirect(method = "initWidgets",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/ClickableWidget;I)Lnet/minecraft/client/gui/widget/ClickableWidget;",
                    ordinal = 0
            ))
    public <T extends ClickableWidget> T onCreate(GridWidget.Adder adder, T widget, int occupiedColumns) {
        final var mc = this.client;
        assert mc != null;
        final var component = mc.isInSingleplayer() ? Text.translatable("menu.returnToMenu") : Text.translatable("menu.disconnect");
        return (T) adder.add(ButtonWidget.builder(component, (button) -> {
            button.active = false;
            if (isAnyKeyDown(KeyBindingHelper.getBoundKeyOf(Quit.bossKey()).getCode())) {
                mc.stop();
                return;
            }
            if (isAnyKeyDown(KeyBindingHelper.getBoundKeyOf(Quit.fastQuit()).getCode())) {
                quitToTitle(mc, button);
                return;
            }
            mc.setScreen(Utilities.quit2Title.apply(mc, button));
        }).width(204).build(), 2);
    }
}
