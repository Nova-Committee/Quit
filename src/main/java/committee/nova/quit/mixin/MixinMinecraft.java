package committee.nova.quit.mixin;

import com.mojang.blaze3d.platform.Window;
import committee.nova.quit.client.ClientInit;
import committee.nova.quit.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static committee.nova.quit.util.Utilities.isAnyKeyDown;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Final
    private Window window;

    @Shadow
    public abstract void setScreen(@org.jetbrains.annotations.Nullable Screen p_91153_);

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;stop()V"), cancellable = true)
    public void onRunTick(boolean b, CallbackInfo ci) {
        if (isAnyKeyDown(ClientInit.fastQuit.getKey().getValue(), ClientInit.bossKey.getKey().getValue())) return;
        setScreen(Utilities.quitGame.apply((Minecraft) (Object) this));
        GLFW.glfwSetWindowShouldClose(window.getWindow(), false);
        ci.cancel();
    }
}
