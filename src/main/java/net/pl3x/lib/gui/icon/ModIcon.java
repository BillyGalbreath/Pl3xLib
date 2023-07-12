package net.pl3x.lib.gui.icon;

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
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ModIcon {
    public static final Map<String, ModIcon> MOD_ICONS = new HashMap<>();

    public static @NotNull ModIcon getOrCreate(@NotNull ModContainer mod, @NotNull ResourceLocation texture) {
        return MOD_ICONS.computeIfAbsent(mod.getMetadata().getId(), k -> new ModIcon(createIcon(mod, texture)));
    }

    public static @NotNull Icon createIcon(@NotNull ModContainer mod, @NotNull ResourceLocation texture) {
        int size = 64 * Minecraft.getInstance().options.guiScale().get();

        try {
            ModMetadata modMeta = mod.getMetadata();
            String iconPath = modMeta.getIconPath(size).orElse("assets/" + modMeta.getId() + "/icon.png");
            try (InputStream inputStream = Files.newInputStream(mod.findPath(iconPath + ".mcmeta").orElseThrow())) {
                return new AnimatedIcon(texture, JsonParser.parseReader(new JsonReader(new InputStreamReader(inputStream))).getAsJsonObject().getAsJsonObject("animation"), size);
            }
        } catch (Throwable t) {
            return new Icon(texture);
        }
    }

    private final Icon icon;

    public ModIcon(@NotNull Icon icon) {
        this.icon = icon;
    }

    public void render(@NotNull GuiGraphics gfx, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        this.icon.render(gfx, x, y, u, v, width, height, textureWidth, textureHeight);
    }
}
