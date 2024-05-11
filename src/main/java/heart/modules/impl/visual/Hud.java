package heart.modules.impl.visual;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.Requirement;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.ColorSetting;
import heart.modules.settings.impl.EnumSetting;
import heart.util.CFontRenderer;
import heart.util.ColorUtil;
import heart.util.PacketUtil;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.DropshadowShader;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

enum watermarkOptions {
    SIMPLE, WORDART, MODERN, COUNTERSTRIKE
}

enum colorOptions {
    STATIC, WAVE, DUAL, RAINBOW, ASTOLFO
}

public class Hud extends Module {
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

    ArrayList<ArraylistModule> arraylistModules = new ArrayList<>();

    public Hud() {
        super("HUD", "Render the client HUD", Category.VISUAL);

        initmodule();
    }

    @Override
    public void onEnable() {
        arraylistModules.clear();
        Heart.getModuleManager().getModules().forEach((key, value) -> arraylistModules.add(new ArraylistModule(value))
        );
        super.onEnable();
    }

    BoolSetting watermarkBool = new BoolSetting("Watermark", "Enables the client watermark.", true);
    EnumSetting<watermarkOptions> watermark = (EnumSetting<watermarkOptions>) new EnumSetting<>("Watermark Mode", "Sets the client watermark.", watermarkOptions.values()).addRequirement(new Requirement(watermarkBool, true));

    EnumSetting<colorOptions> color = new EnumSetting<>("Color Mode", "Sets the huds color mode.", colorOptions.values());

    ColorSetting color1 = new ColorSetting("Theme Color", "Sets the theme color.", new Color(255, 40, 40));
    ColorSetting color2 = new ColorSetting("Accent Color", "Sets the accent color.", new Color(173, 33, 255));

    String ClientName = "Heart";
    CFontRenderer fontRenderer = new CFontRenderer(new Font("Product Sans", Font.PLAIN, 18));
    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }


    @Override
    public void onRender2D(Render2DEvent e) {
        if(watermarkBool.getValue()) {
            switch (watermark.getValue()) {
                case SIMPLE:
                    fontRenderer.drawStringWithShadow(ClientName.replace("&", "§"), 2, 2, getColor(0, 1).getRGB());
                    break;
                case WORDART:
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("heart/image/wordart.png"));
                    Gui.drawModalRectWithCustomSizedTexture(5, 5, 0, 0, 150, 80, 150, 80);
                    break;
                case MODERN:
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    int width = fontRenderer.getStringWidth(ClientName.replace("&", "§") + "§f  |  " + Minecraft.getDebugFPS() + "fps  |  " + PacketUtil.getCurrentPing() + "ms");
                    roundedRectShader.drawRectWithShader(8, 8, width + 8, 16, 1, 10, new Color(20, 20, 20, 110), new Color(255, 255, 255, 60));
                    dropshadowShader.drawRectWithShader(8, 8, width + 8, 16, 70, 10, new Color(0, 0, 0, 100), false);
                    GlStateManager.popAttrib();
                    fontRenderer.drawString(ClientName.replace("&", "§") + "§f  |  " + Minecraft.getDebugFPS() + "fps  |  " + PacketUtil.getCurrentPing() + "ms", 11, 11, 0xb0ffffff);
                    break;
                case COUNTERSTRIKE:

                    break;

            }
        }

        arraylistModules.sort((o1, o2) -> Float.compare(o2.getWidth(), o1.getWidth()));

        float i = 0;
        for(ArraylistModule module : arraylistModules) {
            module.setYOffset(i);
            module.draw();
            i += module.getHeight();
        }
        super.onRender2D(e);
    }

    public Color getColor(int offset, int speed){
        switch (color.getValue()) {
            default:
                return color1.getValue();
            case WAVE:
                return ColorUtil.colorWave(color1.getValue(), color1.getValue().darker(), offset, speed);
            case DUAL:
                return ColorUtil.colorWave(color1.getValue(), color2.getValue(), offset, speed);
            case RAINBOW:
                return new Color(ColorUtil.getChillRainbow(0));
            case ASTOLFO:
                return new Color( ColorUtil.getAstolfoRainbow(offset, speed, 1f, 0.45f));

        }
    }
}


class ArraylistModule {
    Module module;
    public DynamicAnimation animationX = new DynamicAnimation(EasingStyle.ExpoOut, 1000);
    public DynamicAnimation animationY = new DynamicAnimation(EasingStyle.ExpoOut, 1000);

    float yOffset = 0;

    public ArraylistModule(Module m){
        this.module = m;
        if(m.isEnabled()){
            animationX.snapTo(getWidth());
            animationY.snapTo(0);
        }else{
            animationX.snapTo(0);
            animationY.snapTo(12);
        }
    }

    public float getWidth(){
        return Heart.getHud().fontRenderer.getWidth(module.getName() + (Objects.equals(module.getSuffix(), "") ? "" : " " + module.getSuffix())) + 2;
    }

    public float getHeight(){
        return (float) (12 - animationY.getValue());
    }

    public void setYOffset(float yOffset){
        this.yOffset = yOffset;
    }

    public void draw(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if(module.isEnabled()){
            animationY.setTarget(0);
            if(animationY.getValue() < 2)
                animationX.setTarget(0);
        }else {

            animationX.setTarget(getWidth());
            if(animationX.getValue() > animationX.targetValue - 4)
                animationY.setTarget(12);
        }

        Heart.getHud().fontRenderer.drawStringWithShadow(module.getName(), (float) (sr.getScaledWidth() - getWidth() + animationX.getValue()), 2 + yOffset, Color.WHITE.getRGB());
    }
}
