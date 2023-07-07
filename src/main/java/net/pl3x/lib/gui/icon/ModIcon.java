package net.pl3x.lib.gui.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ModIcon {
    public static final Map<String, ModIcon> MOD_ICONS = new HashMap<>();

    public static @NotNull ModIcon getOrCreate(@NotNull ModContainer mod, @NotNull ResourceLocation texture) {
        return MOD_ICONS.computeIfAbsent(mod.getMetadata().getId(), k -> new ModIcon(createIcon(mod, texture)));
    }

    public static Icon createIcon(@NotNull ModContainer mod, @NotNull ResourceLocation texture) {
        int size = 64 * Minecraft.getInstance().options.guiScale().get();

        try {
            // test for icon image first
            ModMetadata modMeta = mod.getMetadata();
            String iconPath = modMeta.getIconPath(size).orElse("assets/" + modMeta.getId() + "/icon.png");
            mod.findPath(iconPath).orElseThrow();

            // try to load image mcmeta next
            try (InputStream inputStream = Files.newInputStream(mod.findPath(iconPath + ".mcmeta").orElseThrow())) {
                JsonElement element = JsonParser.parseReader(new JsonReader(new InputStreamReader(inputStream)));
                JsonObject animation = element.getAsJsonObject().getAsJsonObject("animation");
                AnimationMetadataSection animationMeta = AnimationMetadataSection.SERIALIZER.fromJson(animation);
                FrameSize frameSize = animationMeta.calculateFrameSize(size, size);
                return new AnimatedIcon(texture, frameSize.width(), frameSize.height());
            }
        } catch (Throwable t) {
            return new Icon(texture);
        }
    }

    private final Icon icon;

    public ModIcon(Icon icon) {
        this.icon = icon;
    }

    public void render(GuiGraphics gfx, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        this.icon.render(gfx, x, y, u, v, width, height, textureWidth, textureHeight);
    }
}
