package net.pl3x.lib.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.lib.gui.animation.Animation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    /*
     * Let's tick all the animations.
     */
    @Inject(method = "render(FJZ)V", at = @At(value = "HEAD"))
    public void render(float delta, long startTime, boolean ticking, @NotNull CallbackInfo ci) {
        Animation.tick(Minecraft.getInstance().getDeltaFrameTime());
    }
}
