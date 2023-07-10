package net.pl3x.lib.mixin;

import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.lib.gui.icon.ModIcon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(value = ModListEntry.class, remap = false)
public class ModListEntryMixin {
    @Shadow
    @Final
    public Mod mod;

    /**
     * We'll draw the mod icons, thank you very much.
     */
    @Redirect(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V",
                    ordinal = 0
            )
    )
    private void render(GuiGraphics gfx, ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight,
                        GuiGraphics gfx2, int index, int rowY, int rowX, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        FabricLoader.getInstance().getModContainer(this.mod.getId()).ifPresent(mod ->
                ModIcon.getOrCreate(mod, texture).render(gfx, x, y, u, v, width, height, textureWidth, textureHeight)
        );
    }
}
