package committee.nova.quit.mixin;

import committee.nova.quit.client.Quit;
import committee.nova.quit.util.Utilities;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft {

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Shadow
    @Final
    private Window window;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;scheduleStop()V"), cancellable = true)
    public void onRunTick(boolean b, CallbackInfo ci) {
        if (isAnyKeyDown(KeyBindingHelper.getBoundKeyOf(Quit.bossKey()).getCode(), KeyBindingHelper.getBoundKeyOf(Quit.fastQuit()).getCode()))
            return;
        setScreen(Utilities.quitGame.apply((MinecraftClient) (Object) this));
        GLFW.glfwSetWindowShouldClose(window.getHandle(), false);
        ci.cancel();
    }
}
