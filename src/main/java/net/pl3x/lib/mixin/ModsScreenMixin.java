package net.pl3x.lib.mixin;

import com.terraformersmc.modmenu.gui.ModsScreen;
import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.lib.gui.icon.ModIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(value = ModsScreen.class)
public class ModsScreenMixin {
    @Shadow
    private ModListEntry selected;

    /**
     * We'll draw the mod icons, thank you very much.
     */
    @Redirect(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V",
                    ordinal = 0
            )
    )
    private void render(GuiGraphics gfx, ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight,
                        GuiGraphics gfx2, int mouseX, int mouseY, float delta) {
        FabricLoader.getInstance().getModContainer(this.selected.mod.getId()).ifPresent(mod ->
                ModIcon.getOrCreate(mod, texture).render(gfx, x, y, u, v, width, height, textureWidth, textureHeight)
        );
    }
}
