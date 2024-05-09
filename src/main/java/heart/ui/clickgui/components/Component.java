package heart.ui.clickgui.components;

import heart.ui.clickgui.components.impl.Part;
import heart.ui.clickgui.components.impl.partimpl.ModulePart;
import heart.util.CFontRenderer;
import heart.util.animation.Animation;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.DropshadowShader;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Component {
    private final String label;
    private final char icon;
    private int x, y, w, h, xOff, yOff;
    public CFontRenderer fontRenderer = new CFontRenderer(new Font(new ResourceLocation("Product Sans").getResourcePath(), Font.PLAIN, 17));
    public boolean open = false;
    boolean move = false;
    public ArrayList<Part> parts = new ArrayList<>();

    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.BackOut, 300);

    RoundedRectShader roundedRectShader;

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    DropshadowShader dropshadowShader;

    {
        try {
            dropshadowShader = new DropshadowShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Component(String label, char icon, int x, int y, int w, int h) {
        this.label = label;
        this.icon = icon;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void DrawComponent(int mouseX, int mouseY) {
        //TODO: replace with theme color
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();


        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        dropshadowShader.drawRectWithShader(x - 2 , y - 18, w, (float) (20 + h * Math.max(0, dynamicAnimation.getValue())/1000),15, 6, new Color(0x60101010, true), true);
        roundedRectShader.drawRectWithShader(x, y - 20, w,  (float) (20 + h * Math.max(0, dynamicAnimation.getValue())/1000),0, 3, new Color(0x111111), new Color(0x111111));
        roundedRectShader.drawRectWithShader(x + 3, y - 17, 14, 14,0, 3, new Color(0x070707), new Color(0x070707));
        GlStateManager.popAttrib();


        if (dynamicAnimation.getValue() != 0){
            int h = 0;
            for (Part part : parts){
                part.drawPart(x, (int) (y - 20 + (20 + h) * Math.max(0, dynamicAnimation.getValue())/1000), mouseX, mouseY);
                h += part.height;
                part.open = open;
            }
            this.setH(h);
        }

        roundedRectShader.drawRectWithShader(x, y - 20, w, 20,0, 3, new Color(0x111111), new Color(0x111111));
        GlStateManager.color(255, 255, 255);
        fontRenderer.drawString(label.toUpperCase().substring(0, 1) + label.toLowerCase().substring(1), x + 20, y - 15, -1);
        GlStateManager.color(255, 255, 255);
        Gui.drawRect(x, y - 1, x + 125, y, 0xff8d0528);

        Gui.drawRect(x + 110, y - 10, x + 120, y - 11, 0xff404040);
        Gui.drawRect(x + 110, y - 10 - 4 * Math.max(0, (int) dynamicAnimation.getValue())/1000, x + 120, y - 11 - 4 * Math.max(0, (int) dynamicAnimation.getValue())/1000, 0xff404040);
        Gui.drawRect(x + 110, y - 10 + 4 * Math.max(0, (int) dynamicAnimation.getValue())/1000, x + 120, y - 11 + 4 * Math.max(0, (int) dynamicAnimation.getValue())/1000, 0xff404040);

        if(Mouse.isButtonDown(0) && move){
            setX(mouseX - xOff);
            setY(mouseY - yOff);
        }else{
            move = false;
        }
    }

    public void DrawComponentContent(int mouseX, int mouseY) {

    }

    public void onMouseClicked(int mouseX, int mouseY, int mouseButton){
        if(open)
            for(Part part : parts){
                part.onMouseClick(mouseX, mouseY, mouseButton);
            }

        if(mouseX > x && mouseX < x + w && mouseY > y - 20 && mouseY < y) {
            if(mouseButton == 0){
                xOff = mouseX - getX();
                yOff = mouseY - getY();
                move = true;
            }

            if(mouseButton == 1) {
                open = !open;
                dynamicAnimation.setTarget(open ? 1000 : 0);
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setW(int w) {
        this.w = w;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getW() {
        return w;
    }
    public int getH() {
        return h;
    }
}