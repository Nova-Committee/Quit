package committee.nova.quit.mixin;

import committee.nova.quit.screen.GuiQuit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void inject$actionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.id != 4 || Utilities.fast()) return;
        this.mc.displayGuiScreen(new GuiQuit((GuiMainMenu) (Object) this, I18n.format("gui.quitGame")));
        ci.cancel();
    }

    @Inject(method = "confirmClicked", at = @At("HEAD"), cancellable = true)
    private void inject$confirmClicked(boolean yes, int id, CallbackInfo ci) {
        if (id != 13468) return;
        if (yes) {
            mc.shutdown();
        } else mc.displayGuiScreen(new GuiMainMenu());
        ci.cancel();
    }
}
