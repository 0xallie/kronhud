package io.github.darkkronicle.kronhud.mixins;

import io.github.darkkronicle.kronhud.gui.HudManager;
import io.github.darkkronicle.kronhud.gui.hud.simple.PingHud;
import io.github.darkkronicle.kronhud.gui.hud.simple.TPSHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinNetworkPlayHandler {

    @Inject(method = "onWorldTimeUpdate", at = @At("HEAD"))
    private void onWorldUpdate(WorldTimeUpdateS2CPacket packet, CallbackInfo ci) {
        PingHud pingHud = (PingHud) HudManager.getInstance().get(PingHud.ID);
        TPSHud tpsHud = (TPSHud) HudManager.getInstance().get(TPSHud.ID);
        pingHud.updateTime(packet.getTime());
        tpsHud.updateTime(packet.getTime());
    }

}
