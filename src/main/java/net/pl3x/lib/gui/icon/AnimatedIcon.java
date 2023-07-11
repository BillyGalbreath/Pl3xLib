package net.pl3x.lib.gui.icon;

import com.google.gson.JsonObject;
import java.util.Arrays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.lib.gui.GL;
import net.pl3x.lib.gui.animation.Animation;
import net.pl3x.lib.gui.animation.Easing;
import org.jetbrains.annotations.NotNull;

public class AnimatedIcon extends Icon {
    private final FrameSize size;
    private final int[] frames;

    private Animation animation = new Animation(0, 0, 0, Easing.Linear.flat);
    private int cur = -1;

    public AnimatedIcon(@NotNull ResourceLocation texture, @NotNull JsonObject animation, int size) {
        super(texture);

        AnimationMetadataSection meta = AnimationMetadataSection.SERIALIZER.fromJson(animation);

        this.size = meta.calculateFrameSize(size, size);
        this.frames = new int[this.size.width() * this.size.height()];

        Arrays.fill(this.frames, meta.getDefaultFrameTime());

        // todo - reorder frames from mcmeta
        for (AnimationFrame frame : meta.frames) {
            this.frames[frame.getIndex()] = frame.getTime(meta.getDefaultFrameTime());
        }
    }

    @Override
    public void render(GuiGraphics gfx, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        if (this.animation.isFinished()) {
            if (++this.cur >= this.frames.length) {
                this.cur = 0;
            }
            this.animation = new Animation(0, 1, this.frames[this.cur] - 0.5F, Easing.Linear.flat);
        }

        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        float u0 = (this.cur / this.size.height()) / (float) this.size.width();
        float v0 = (this.cur % this.size.height()) / (float) this.size.height();
        float u1 = u0 + textureWidth / (float) (textureWidth * this.size.width());
        float v1 = v0 + textureHeight / (float) (textureHeight * this.size.height());

        GL.drawTexture(gfx, getTexture(), x, y, width, height, u0, v0, u1, v1);
    }
}
