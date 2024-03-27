package committee.nova.quit.mixin;

import committee.nova.quit.Quit;
import committee.nova.quit.client.gui.GuiQuit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    public abstract void displayGuiScreen(GuiScreen p_147108_1_);

    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    public abstract void shutdown();


    @Redirect(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shutdown()V"))
    public void onRunGameLoop(Minecraft instance) {
        Quit.preventWindowFromClosing.run();
        if (Utilities.direct()) {
            shutdown();
            return;
        }
        final GuiScreen current = currentScreen;
        displayGuiScreen(new GuiQuit((yes, i) -> {
            final Minecraft mc = (Minecraft) (Object) MixinMinecraft.this;
            if (yes) mc.shutdown();
            else mc.displayGuiScreen(current);
        }, I18n.format("gui.quitGame")));
    }
}
