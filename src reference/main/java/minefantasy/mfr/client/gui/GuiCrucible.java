package minefantasy.mfr.client.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.container.ContainerCrucible;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCrucible extends GuiContainer {
    private TileEntityCrucible tile;

    public GuiCrucible(InventoryPlayer user, TileEntityCrucible tile) {
        super(new ContainerCrucible(user, tile));
        this.ySize = 186;
        this.tile = tile;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource(getTex()));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (tile.getStackInSlot(tile.getSizeInventory() - 1) != null
                && !(tile.getStackInSlot(tile.getSizeInventory() - 1).getItem() instanceof ItemBlock)
                && ConfigHardcore.HCCreduceIngots && !tile.isAuto()) {
            this.drawTexturedModalRect(xPoint + 128, yPoint + 31, 225, 2, 18, 18);
        }

        if (this.tile.temperature > 0) {
            this.drawTexturedModalRect(xPoint + 81, yPoint + 75, 243, 0, 14, 12);
        }
        if (this.tile.progress > 0 && tile.progressMax > 0) {
            int value = (int) (54F / tile.progressMax * tile.progress);
            this.drawTexturedModalRect(xPoint + 61, yPoint + 70, 189, 0, value, 2);
        }
    }

    private String getTex() {
        if (tile.getTier() == 1) {
            return "textures/gui/crucible_advanced.png";
        }
        if (tile.getTier() >= 2) {
            return "textures/gui/crucible_mythic.png";
        }
        return "textures/gui/crucible.png";
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }
}