package heart.ui.clickgui.components.impl;

import heart.util.CFontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Part {

    public int height;

    public CFontRenderer fontRenderer = new CFontRenderer(new Font(new ResourceLocation("Product Sans").getResourcePath(), Font.PLAIN, 16));
    public CFontRenderer smallFontRenderer = new CFontRenderer(new Font(new ResourceLocation("Product Sans").getResourcePath(), Font.PLAIN, 12));

    int yOffset = 0;

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public Part(int height) {
        this.height = height;
    }

    public boolean open = false;


    public void drawPart(int x, int y, int mouseX, int mouseY) {

    }

    public void onMouseClick(int x, int y, int button) {

    }

    public void forceclose(){

    }

    public boolean shouldShow(){
        return true;
    }

}