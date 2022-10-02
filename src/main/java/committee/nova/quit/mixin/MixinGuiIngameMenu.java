package committee.nova.quit.mixin;

import committee.nova.quit.client.screen.GuiQuit;
import committee.nova.quit.util.Utilities$;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class MixinGuiIngameMenu extends GuiScreen implements GuiYesNoCallback {
    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    public void onActionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.id != 1) return;
        if (Utilities$.MODULE$.boss()) {
            Utilities$.MODULE$.isClosing_$eq(true);
            mc.shutdown();
            return;
        }
        if (Utilities$.MODULE$.fast()) return;
        this.mc.displayGuiScreen(new GuiQuit(this, I18n.format("gui.quitLevel")));
        ci.cancel();
    }

    @Override
    public void confirmClicked(boolean yes, int id) {
        if (id != 13468) return;
        if (!yes) {
            this.mc.displayGuiScreen((GuiIngameMenu) (Object) this);
            return;
        }
        this.mc.theWorld.sendQuittingDisconnectingPacket();
        this.mc.loadWorld(null);
        this.mc.displayGuiScreen(new GuiMainMenu());
    }
}