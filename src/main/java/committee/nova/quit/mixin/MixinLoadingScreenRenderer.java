package committee.nova.quit.mixin;

import committee.nova.quit.util.Utilities;
import net.minecraft.client.LoadingScreenRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingScreenRenderer.class)
public abstract class MixinLoadingScreenRenderer {
    @Inject(method = "displayString", at = @At("HEAD"), cancellable = true)
    public void onDisplayString(String msg, CallbackInfo ci) {
        if (Utilities.isClosing()) ci.cancel();
    }

    @Inject(method = "displayLoadingString", at = @At("HEAD"), cancellable = true)
    public void onDisplayLoadingString(String msg, CallbackInfo ci) {
        if (Utilities.isClosing()) ci.cancel();
    }
}
