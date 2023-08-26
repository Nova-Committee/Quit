package committee.nova.quit.mixin;

import committee.nova.quit.client.ClientInit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;
import static committee.nova.quit.util.Utilities.quitToTitle;

@Mixin(PauseScreen.class)
public abstract class MixinPauseScreen extends Screen {
    protected MixinPauseScreen(Component title) {
        super(title);
    }

    @SuppressWarnings("unchecked")
    @Redirect(method = "createPauseMenu",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/PauseScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"
            ))
    public <T extends GuiEventListener & Widget & NarratableEntry> T onCreate(PauseScreen instance, T t) {
        if (!(t instanceof Button button)) return this.addRenderableWidget(t);
        if (!(button.getMessage() instanceof MutableComponent m)) return this.addRenderableWidget(t);
        if (!(m.getContents() instanceof TranslatableContents r)) return this.addRenderableWidget(t);
        if (!r.getKey().equals("menu.returnToMenu") && !r.getKey().equals("menu.disconnect"))
            return this.addRenderableWidget(t);
        final var mc = this.minecraft;
        assert mc != null;
        final var component = mc.isLocalServer() ? Component.translatable("menu.returnToMenu") : Component.translatable("menu.disconnect");
        return (T) this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 - 16, 204, 20, component, (b) -> {
            if (isAnyKeyDown(ClientInit.bossKey.getKey().getValue())) {
                mc.stop();
                return;
            }
            if (isAnyKeyDown(ClientInit.fastQuit.getKey().getValue())) {
                quitToTitle(mc, b);
                return;
            }
            mc.setScreen(Utilities.quit2Title.apply(mc, b));
        }));
    }
}
