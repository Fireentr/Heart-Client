package heart.modules.impl.visual;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.Requirement;
import heart.modules.settings.impl.BoolSetting;
import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Hud extends Module {

    ArrayList<ArraylistModule> arraylistModules = new ArrayList<>();

    public Hud() {
        super("HUD", "Render the client HUD", Category.VISUAL);

        initmodule();
    }

    @Override
    public void onEnable() {
        arraylistModules.clear();
        Heart.getModuleManager().getModules().forEach((key, value) -> {
            arraylistModules.add(new ArraylistModule(value));
        });
        super.onEnable();
    }

    BoolSetting test = new BoolSetting("Test", "TESING", false);
    BoolSetting whow = (BoolSetting) new BoolSetting("WHEEEW", "fre", false).addRequirement(new Requirement(test, false));
    BoolSetting wdshow = new BoolSetting("WHEEEdfsdfW", "fre", false);
    BoolSetting wsdfasdhow = new BoolSetting("WHdsfsfddsfEEEW", "fre", false);

    String ClientName = "Heart";
    CFontRenderer fontRenderer = new CFontRenderer(new Font("Arial", Font.PLAIN, 18));
    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }


    @Override
    public void onRender2D(Render2DEvent e) {
        fontRenderer.drawStringWithShadow(ClientName.replace("&", "§"), 2, 2, Color.WHITE.getRGB());

        float i = 0;
        for(ArraylistModule module : arraylistModules) {
            module.setYOffset(i);
            module.draw();
            i += module.getHeight();
        }
        super.onRender2D(e);
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
