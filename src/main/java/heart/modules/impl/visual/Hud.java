package heart.modules.impl.visual;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.Requirement;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.ColorSetting;
import heart.modules.settings.impl.EnumSetting;
import heart.modules.settings.impl.IntSetting;
import heart.util.CFontRenderer;
import heart.util.ColorUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

enum watermarkOptions {
    SIMPLE, BAR, IMAGE, WORDART
}

enum colorOptions {
    STATIC, WAVE, DUAL, RAINBOW, ASTOLFO
}

enum arraylistAnimation {
    DEFAULT, NONE, LINEAR, NOVOLINE
}

enum arraylistLine {
    NONE, RIGHT, LEFT, WRAP
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
    public void initmodule() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                arraylistModules.clear();
                Heart.getModuleManager().getModules().forEach((key, value) -> {
                    ArraylistModule arval = new ArraylistModule(value);
                    arraylistModules.add(new ArraylistModule(value));
                });
            }
        }, 1000);

        super.initmodule();
    }

    BoolSetting watermarkBool = new BoolSetting("Watermark", "Enables the client watermark.", true);
    EnumSetting<watermarkOptions> watermark = (EnumSetting<watermarkOptions>) new EnumSetting<>("Watermark Mode", "Sets the client watermark.", watermarkOptions.values()).addRequirement(new Requirement(watermarkBool, true));

    EnumSetting<colorOptions> color = new EnumSetting<>("Color Mode", "Sets the huds color mode.", colorOptions.values());
    ColorSetting color1 = new ColorSetting("Theme Color", "Sets the theme color.", new Color(255, 40, 40));
    ColorSetting color2 = new ColorSetting("Accent Color", "Sets the accent color.", new Color(173, 33, 255));

    EnumSetting<heart.modules.impl.visual.arraylistLine> arraylistLine = new EnumSetting<>("Arraylist Line", "Changes the arraylist line.", heart.modules.impl.visual.arraylistLine.values());

    BoolSetting arraylistCustomFont = new BoolSetting("Arraylist Custom Font", "Gives the arraylist a custom font.", false);
    IntSetting arraylistMargin = new IntSetting("Arraylist Margin", "Detaches the arraylist from the corner of the screen.", 0, 15, 0);

    EnumSetting<heart.modules.impl.visual.arraylistAnimation> arraylistAnimation = new EnumSetting<>("Arraylist Animation", "Changes the arraylist animations.", heart.modules.impl.visual.arraylistAnimation.values());

    BoolSetting arraylistBackgroundSetting = new BoolSetting("Arraylist Background", "Draws an background behind the arraylist.", false);

    String ClientName = "Heart";
    CFontRenderer fontRenderer = new CFontRenderer(new Font("Product Sans",  Font.PLAIN, 18));
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
                case BAR:
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                    String timeString = dateFormat.format(new Date());
                    String boxString = ClientName.replace("&", "§") + "§7 | §f" + mc.session.getUsername() + " §7| §f" + Minecraft.getDebugFPS() + "fps" + " §7| §f" + timeString;
                    fontRenderer.drawString(boxString, 8, 8.3f, Color.WHITE.getRGB(), true);
                    GlStateManager.popAttrib();
                    break;
                case IMAGE:
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("heart/image/icon.png"));
                    Gui.drawModalRectWithCustomSizedTexture(-10, -10, 0, 0, 80, 80, 80, 80);
                    break;
                case WORDART:
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("heart/image/wordart.png"));
                    Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 150, 80, 150, 80);
                    break;

            }
        }

        arraylistModules.sort((o1, o2) -> Float.compare(o2.getWidth(), o1.getWidth()));

        float i = 0;
        int ind = 0;
        for(ArraylistModule module : arraylistModules) {
            module.setYOffset(i);
            module.draw(ind);
            i += module.getHeight();
            ind++;
        }
        super.onRender2D(e);
    }

    public Color getColor(int offset, int speed){
        switch (color.getValue()) {
            default:
                return color1.getValue();
            case WAVE:
                return ColorUtil.colorWave(color1.getValue(), color1.getValue().darker().darker(), offset, speed);
            case DUAL:
                return ColorUtil.colorWave(color1.getValue(), color2.getValue(), offset, speed);
            case RAINBOW:
                return new Color(ColorUtil.getChillRainbow(-offset));
            case ASTOLFO:
                return new Color( ColorUtil.getAstolfoRainbow(-offset, speed, 1f, 0.45f));

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
        return ((Hud) Heart.getModuleManager().getModule("hud")).arraylistCustomFont.getValue() ?
                Heart.getHud().fontRenderer.getWidth(module.getName() + (Objects.equals(module.getSuffix(), "") ? "" : " " + module.getSuffix())) + 2 :
                Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName() + (Objects.equals(module.getSuffix(), "") ? "" : " " + module.getSuffix())) + 4;
    }

    void drawString(String s, float x, float y, int color) {
        if (((Hud) Heart.getModuleManager().getModule("hud")).arraylistCustomFont.getValue())
            Heart.getHud().fontRenderer.drawStringWithShadow(s, x, y, color);
        else {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, 0);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(s, 1, 0, color);
            GlStateManager.popMatrix();
        }
    }

    public float getHeight(){
        return (float) (12 - animationY.getValue());
    }

    public void setYOffset(float yOffset){
        this.yOffset = yOffset;
    }

    public void draw(int index){
        animate();
        if(module.isEnabled() || !(animationY.getValue() > animationX.targetValue - 1)) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            if (((Hud) Heart.getModuleManager().getModule("hud")).arraylistBackgroundSetting.getValue()) {
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();

                GlStateManager.pushMatrix();
                GlStateManager.translate((animationX.getValue()), yOffset + ((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue(), 0);
                Gui.drawRect((int) (sr.getScaledWidth()  - getWidth()), 0, (sr.getScaledWidth()), 12, 0x60000000);
                GlStateManager.popMatrix();
            }


            Minecraft.getMinecraft().fontRendererObj.drawString(".", -10, -10, ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());
            drawString(module.getName() + "§7 " + module.getSuffix(), (float) (sr.getScaledWidth() - getWidth() + animationX.getValue()) + 1, 2 + yOffset + ((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue(), ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());
            if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistLine.getValue().equals(arraylistLine.RIGHT) || ((Hud) Heart.getModuleManager().getModule("hud")).arraylistLine.getValue().equals(arraylistLine.WRAP)){
                GlStateManager.pushMatrix();
                GlStateManager.translate((animationX.getValue()), yOffset + ((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue(), 0);
                Gui.drawRect((int) (sr.getScaledWidth()), 0, sr.getScaledWidth() + 1, 12, ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());
                GlStateManager.popMatrix();
            }

            if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistLine.getValue().equals(arraylistLine.LEFT) || ((Hud) Heart.getModuleManager().getModule("hud")).arraylistLine.getValue().equals(arraylistLine.WRAP)){
                GlStateManager.pushMatrix();
                GlStateManager.translate((animationX.getValue()), yOffset + ((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue(), 0);
                Gui.drawRect((int) (sr.getScaledWidth()  - getWidth() - 1), 0, (int) (sr.getScaledWidth()  - getWidth()), 12, ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());
                GlStateManager.popMatrix();
            }

            if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistLine.getValue().equals(arraylistLine.WRAP)){
                GlStateManager.pushMatrix();
                GlStateManager.translate((animationX.getValue()), yOffset + ((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue(), 0);

                System.out.println(index + 1 + " | " + (((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.size()));
                Gui.drawRect((int) (sr.getScaledWidth() - getWidth() - 1), 12, (int) (sr.getScaledWidth() + getWidth() - getnextsize(index + 1) - getWidth() - 1), 13, ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());

                if(isFirst(index))
                    Gui.drawRect((int) (sr.getScaledWidth() - getWidth() - 1), -1, (sr.getScaledWidth()) + 1, 0, ((Hud) Heart.getModuleManager().getModule("hud")).getColor((int) yOffset * 10, 1).getRGB());

                GlStateManager.popMatrix();
            }
        }
    }

    int getnextsize(int index){
        for (int i = index; i < ((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.size(); i++){
            if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.get(i).module.isEnabled()){
                return (int) ((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.get(i).getWidth();
            }
        }
        return -1;
    }

    boolean isFirst(int index){
        for (int i = 0; i < ((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.size(); i++){
            if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.get(i).module.isEnabled() && i == index){
                return true;
            }else if(((Hud) Heart.getModuleManager().getModule("hud")).arraylistModules.get(i).module.isEnabled()) {
                return false;
            }
        }
        return false;
    }

    void init(){
        animationX.getAnim().setEasing(EasingStyle.ExpoOut);
        animationY.getAnim().setEasing(EasingStyle.ExpoOut);
        animationY.setTarget(this.module.isEnabled() ? 0 : 12);
        animationX.setTarget(this.module.isEnabled() ? 0 : getWidth());

        animationX.snapTo(this.animationX.targetValue);
        animationY.snapTo(this.animationY.targetValue);
    }

    void animate(){
        switch (((Hud) Heart.getModuleManager().getModule("hud")).arraylistAnimation.getValue()){
            case DEFAULT:
                animationX.getAnim().setEasing(EasingStyle.ExpoOut);
                animationY.getAnim().setEasing(EasingStyle.ExpoOut);
                if(this.module.isEnabled()){
                    animationY.setTarget(0);
                    animationX.setTarget(-((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue());
                }else {
                    animationX.setTarget(getWidth() + 4);
                    animationY.setTarget(12);
                }
                break;
            case LINEAR:
                animationX.getAnim().setEasing(EasingStyle.Linear);
                animationY.getAnim().setEasing(EasingStyle.Linear);
                if(this.module.isEnabled()){
                    animationY.setTarget(0);
                    animationX.setTarget(-((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue());
                }else {
                    animationX.setTarget(getWidth());
                    animationY.setTarget(12);
                }
                break;
            case NOVOLINE:
                animationX.getAnim().setEasing(EasingStyle.ExpoOut);
                animationY.getAnim().setEasing(EasingStyle.ExpoOut);
                if(this.module.isEnabled()){
                    animationY.setTarget(0);
                    if(animationY.getValue() < 2)
                        animationX.setTarget(-((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue());
                }else {
                    animationX.setTarget(getWidth() + 4);
                    if(animationX.getValue() > animationX.targetValue - 4)
                        animationY.setTarget(12);
                }
                break;
            case NONE:
                animationX.getAnim().setEasing(EasingStyle.ExpoOut);
                animationY.getAnim().setEasing(EasingStyle.ExpoOut);
                animationY.setTarget(this.module.isEnabled() ? 0 : 12);
                animationX.setTarget(this.module.isEnabled() ? -((Hud) Heart.getModuleManager().getModule("hud")).arraylistMargin.getValue() : getWidth());

                animationX.snapTo(this.animationX.targetValue);
                animationY.snapTo(this.animationY.targetValue);
                break;
        }
    }
}
