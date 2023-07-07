package net.pl3x.lib.gui.icon;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.lib.gui.GL;
import net.pl3x.lib.gui.animation.Animation;
import net.pl3x.lib.gui.animation.Easing;
import org.jetbrains.annotations.NotNull;

public class AnimatedIcon extends Icon {
    private final float modWidth;
    private final float modHeight;
    private final int frames;

    private Animation animation;
    private boolean animate;

    public AnimatedIcon(@NotNull ResourceLocation texture, int width, int height) {
        super(texture);
        this.modWidth = width;
        this.modHeight = (float) height / width;
        this.frames = Math.round(this.modHeight) - 1;
    }

    @Override
    public void render(GuiGraphics gfx, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        if (this.animation == null || this.animation.isFinished()) {
            this.animate = !this.animate;
            this.animation = new Animation(0, this.frames, this.animate ? 60 : 100, new Easing.Function("normal", t -> t));
        }

        float frame = this.animate ? (int) this.animation.getValue() * textureWidth / (this.modHeight * textureWidth) : 0;
        float w = this.modWidth * textureWidth;
        float h = this.modHeight * textureHeight;

        GL.drawTexture(gfx, getTexture(), x, y, width, height, u / w, v / h + frame, (u + width) / w, (v + height) / h + frame);
    }
}
