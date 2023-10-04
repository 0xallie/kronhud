package io.github.darkkronicle.kronhud.mixins;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Inject(method = "shouldShowPacketSizeAndPingCharts()Z", at = @At("RETURN"), cancellable = true)
    public void shouldShowPacketSizeAndPingCharts(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
