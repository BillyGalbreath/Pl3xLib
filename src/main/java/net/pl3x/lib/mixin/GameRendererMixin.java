package net.pl3x.lib.mixin;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.pl3x.lib.animation.Animation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
@SuppressWarnings("unused")
public class GameRendererMixin {
    @Inject(method = "render(FJZ)V", at = @At(value = "HEAD"))
    public void render(float f, long l, boolean bl, @NotNull CallbackInfo ci) {
        Iterator<Animation> iter = Animation.ANIMATIONS.iterator();
        while (iter.hasNext()) {
            Animation animation = iter.next();
            animation.tickAnimation(Minecraft.getInstance().getDeltaFrameTime());
            if (animation.isFinished()) {
                iter.remove();
            }
        }
    }
}
