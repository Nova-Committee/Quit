package committee.nova.quit.mixin;

import committee.nova.quit.screen.GuiQuit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class MixinGuiIngameMenu extends GuiScreen implements GuiYesNoCallback {
    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void inject$actionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.id != 1) return;
        if (Utilities.fast()) return;
        ci.cancel();
        mc.displayGuiScreen(new GuiQuit(this, I18n.format("gui.quitLevel")));
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
