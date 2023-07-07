package net.pl3x.lib.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.lib.util.Mathf;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class GL {
    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x, float y, float size) {
        drawTexture(gfx, texture, x, y, size, size);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x, float y, float width, float height) {
        drawTexture(gfx, texture, x, y, width, height, 0xFFFFFFFF);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x, float y, float width, float height, int color) {
        drawTexture(gfx, texture, x, y, width, height, color, color);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x, float y, float width, float height, int colorTop, int colorBottom) {
        drawTexture(gfx, texture, x, y, width, height, colorTop, colorTop, colorBottom, colorBottom);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x, float y, float width, float height, int colorTopLeft, int colorTopRight, int colorBottomRight, int colorBottomLeft) {
        drawTexture(gfx, texture, x, y, width, height, 0, 0, 1, 1, colorTopLeft, colorTopRight, colorBottomRight, colorBottomLeft);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x0, float y0, float width, float height, float u0, float v0, float u1, float v1) {
        drawTexture(gfx, texture, x0, y0, width, height, u0, v0, u1, v1, 0xFFFFFFFF);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x0, float y0, float width, float height, float u0, float v0, float u1, float v1, int color) {
        drawTexture(gfx, texture, x0, y0, width, height, u0, v0, u1, v1, color, color);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x0, float y0, float width, float height, float u0, float v0, float u1, float v1, int colorTop, int colorBottom) {
        drawTexture(gfx, texture, x0, y0, width, height, u0, v0, u1, v1, colorTop, colorTop, colorBottom, colorBottom);
    }

    public static void drawTexture(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x0, float y0, float width, float height, float u0, float v0, float u1, float v1, int colorTopLeft, int colorTopRight, int colorBottomRight, int colorBottomLeft) {
        drawTextureInternal(gfx, texture, x0, y0, x0 + width, y0 + height, u0, v0, u1, v1, colorTopLeft, colorTopRight, colorBottomRight, colorBottomLeft);
    }

    private static void drawTextureInternal(@NotNull GuiGraphics gfx, @NotNull ResourceLocation texture, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int colorTopLeft, int colorTopRight, int colorBottomRight, int colorBottomLeft) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Matrix4f model = gfx.pose().last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buf.vertex(model, x0, y0, 0).uv(u0, v0).color(colorTopLeft).endVertex();
        buf.vertex(model, x0, y1, 0).uv(u0, v1).color(colorBottomLeft).endVertex();
        buf.vertex(model, x1, y1, 0).uv(u1, v1).color(colorBottomRight).endVertex();
        buf.vertex(model, x1, y0, 0).uv(u1, v0).color(colorTopRight).endVertex();
        BufferUploader.drawWithShader(buf.end());
    }

    public static void drawSolidRect(@NotNull GuiGraphics gfx, float x0, float y0, float width, float height, int color) {
        drawSolidRect(gfx, x0, y0, width, height, color, color);
    }

    public static void drawSolidRect(@NotNull GuiGraphics gfx, float x0, float y0, float width, float height, int colorTop, int colorBottom) {
        drawSolidRect(gfx, x0, y0, width, height, colorTop, colorTop, colorBottom, colorBottom);
    }

    public static void drawSolidRect(@NotNull GuiGraphics gfx, float x0, float y0, float width, float height, int colorTopLeft, int colorTopRight, int colorBottomRight, int colorBottomLeft) {
        drawSolidRectInternal(gfx, x0, y0, x0 + width, y0 + height, colorTopLeft, colorTopRight, colorBottomRight, colorBottomLeft);
    }

    private static void drawSolidRectInternal(@NotNull GuiGraphics gfx, float x0, float y0, float x1, float y1, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        Matrix4f model = gfx.pose().last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(model, x1, y0, 0).color(colorTopRight).endVertex();
        buf.vertex(model, x0, y0, 0).color(colorTopLeft).endVertex();
        buf.vertex(model, x0, y1, 0).color(colorBottomLeft).endVertex();
        buf.vertex(model, x1, y1, 0).color(colorBottomRight).endVertex();
        BufferUploader.drawWithShader(buf.end());
    }

    public static void drawSolidCirc(@NotNull GuiGraphics gfx, float centerX, float centerY, float radius, int color) {
        drawSolidCirc(gfx, centerX, centerY, radius, (int) radius * 2, color);
    }

    public static void drawSolidCirc(@NotNull GuiGraphics gfx, float centerX, float centerY, float radius, int resolution, int color) {
        drawSolidCirc(gfx, centerX, centerY, radius, resolution, color, color);
    }

    public static void drawSolidCirc(@NotNull GuiGraphics gfx, float centerX, float centerY, float radius, int resolution, int innerColor, int outerColor) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        Matrix4f model = gfx.pose().last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        buf.vertex(model, centerX, centerY, 0).color(innerColor).endVertex();
        float mod = 2F * Mathf.PI / resolution;
        for (int i = 0; i <= resolution; i++) {
            float angle = mod * i;
            float x = centerX + (Mathf.sin(angle) * radius);
            float y = centerY + (Mathf.cos(angle) * radius);
            buf.vertex(model, x, y, 0).color(outerColor).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());
    }

    public static void drawLine(@NotNull GuiGraphics gfx, float x0, float y0, float x1, float y1, float width, int color) {
        drawLine(gfx, x0, y0, x1, y1, width, color, color);
    }

    public static void drawLine(@NotNull GuiGraphics gfx, float x0, float y0, float x1, float y1, float width, int startColor, int endColor) {
        // I'm not sure what this is about, but it puts it in the correct "zIndex"
        gfx.pose().translate(0, 0, -7.8431);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.lineWidth(width);

        PoseStack.Pose pose = gfx.pose().last();
        Matrix4f model = pose.pose();
        Matrix3f normal = pose.normal();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        if (x0 < x1) {
            buf.vertex(model, x0, y0, 0).color(startColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, x1, y1, 0).color(endColor).normal(normal, 1, 0, 0).endVertex();
        } else {
            buf.vertex(model, x1, y1, 0).color(endColor).normal(normal, 1, 0, 0).endVertex();
            buf.vertex(model, x0, y0, 0).color(startColor).normal(normal, 1, 0, 0).endVertex();
        }
        BufferUploader.drawWithShader(buf.end());
    }

    public static void rotateScene(@NotNull GuiGraphics gfx, float pivotX, float pivotY, float width, float height, float degrees) {
        rotateScene(gfx, (int) (pivotX + width / 2F), (int) (pivotY + height / 2F), degrees);
    }

    public static void rotateScene(@NotNull GuiGraphics gfx, float pivotX, float pivotY, float degrees) {
        gfx.pose().translate(pivotX, pivotY, 0);
        gfx.pose().mulPose((new Quaternionf()).rotateZ(degrees * Mathf.DEG_TO_RAD));
        gfx.pose().translate(-pivotX, -pivotY, 0);
    }

    public static void scaleScene(@NotNull GuiGraphics gfx, float pivotX, float pivotY, float width, float height, float scale) {
        scaleScene(gfx, (int) (pivotX + width / 2F), (int) (pivotY + height / 2F), scale);
    }

    public static void scaleScene(@NotNull GuiGraphics gfx, float pivotX, float pivotY, float scale) {
        gfx.pose().translate(pivotX, pivotY, 0D);
        gfx.pose().scale(scale, scale, scale);
        gfx.pose().translate(-pivotX, -pivotY, 0D);
    }
}
