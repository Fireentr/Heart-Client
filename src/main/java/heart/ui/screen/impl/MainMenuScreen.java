package heart.ui.screen.impl;

import heart.Heart;
import heart.ui.screen.Screen;
import heart.util.CFontRenderer;
import heart.util.shader.impl.BackgroundShader;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class MainMenuScreen extends Screen {

    public BackgroundShader bgShader;

    {
        try {
            bgShader = new BackgroundShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    CFontRenderer titleFontRenderer = new CFontRenderer(new Font("Product Sans", Font.BOLD, 30));

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        titleFontRenderer.drawCenteredString("Heart", 20, 20, -1);
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Gui.drawRect(0, 0, this.width, this.height, -1);
        GlStateManager.enableBlend();

        bgShader.drawRectWithShader(0, 0, this.width, this.height);

        GlStateManager.enableBlend();
        GlStateManager.resetColor();

        GlStateManager.popAttrib();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        mc.displayGuiScreen(new GuiSelectWorld(Heart.getMainMenuScreen()));
    }
}
