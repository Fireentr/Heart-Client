package heart.ui.clickgui.components.impl;

import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Part {

    public CategoryCompontenent categoryCompontenent;
    public int height;

    public CFontRenderer fontRenderer = new CFontRenderer(new Font(new ResourceLocation("Product Sans").getResourcePath(), Font.PLAIN, 17));

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

}