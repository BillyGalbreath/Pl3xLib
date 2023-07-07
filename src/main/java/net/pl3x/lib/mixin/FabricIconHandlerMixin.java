package net.pl3x.lib.mixin;

import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = FabricIconHandler.class, remap = false)
public class FabricIconHandlerMixin {
    /**
     * Don't let ModMenu kill our icon if it's not a square (animations can't be square).
     */
    @ModifyArg(method = "createIcon(Lnet/fabricmc/loader/api/ModContainer;Ljava/lang/String;)Lnet/minecraft/client/renderer/texture/DynamicTexture;", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/Validate;validState(ZLjava/lang/String;[Ljava/lang/Object;)V"), index = 0)
    private boolean createIcon(boolean validate) {
        return true;
    }
}
