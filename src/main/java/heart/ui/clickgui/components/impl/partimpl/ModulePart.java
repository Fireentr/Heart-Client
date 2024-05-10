package heart.ui.clickgui.components.impl.partimpl;

import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.modules.settings.impl.IntSetting;
import heart.modules.settings.impl.ModeSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.ui.clickgui.components.impl.partimpl.settings.BooleanSettingPart;
import heart.ui.clickgui.components.impl.partimpl.settings.DoubleSettingPart;
import heart.ui.clickgui.components.impl.partimpl.settings.IntSettingPart;
import heart.ui.clickgui.components.impl.partimpl.settings.ModeSettingPart;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModulePart extends Part {

    RoundedRectShader roundedRectShader;

    ArrayList<Part> subParts = new ArrayList<>();

    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    DynamicAnimation enableAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 600);

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Module module;

    public ModulePart(int height, Module module) {
        super(height);
        dynamicAnimation.snapTo(0);
        dynamicAnimation.setTarget(0);
        enableAnimation.snapTo(module.isEnabled() ? 255 : 100);
        this.module = module;
        module.getSettings().forEach((key, value) -> {
            switch (value.getClass().getSimpleName()) {
                default:
                    System.out.println("not a setting");
                    break;
                case "BoolSetting":
                    subParts.add(new BooleanSettingPart((BoolSetting) value));
                    break;
                case "ModeSetting":
                    subParts.add(new ModeSettingPart((ModeSetting) value));
                    break;
                case "IntSetting":
                    subParts.add(new IntSettingPart((IntSetting) value));
                    break;
                case "DoubleSetting":
                    subParts.add(new DoubleSettingPart((DoubleSetting) value));
                    break;
            }
        });
    }


    boolean hoveringModule = false;
    boolean hoveringKeybind = false;

    public boolean open2 = false;

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(button == 0){
            if(hoveringModule){
                module.setEnabled(!module.isEnabled());
            }
        }
        if(button == 1){
            if(hoveringModule){
                open2 = !open2;
                dynamicAnimation.setTarget(open2 ? 100 : 0);
                for (Part part : subParts) {
                    part.open = open2;
                }
            }
        }

        if(open2){
            for (Part part : subParts) {
                if(part.shouldShow())
                    part.onMouseClick(x, y, button);
            }
        }

        super.onMouseClick(x, y, button);
    }

    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {

        enableAnimation.setTarget(module.isEnabled() ? 190 : 100);

        hoveringKeybind = mouseX > x && mouseX < x + 14 && mouseY > y && mouseY < y + 20;
        hoveringModule = mouseX > x + 14 && mouseX < x + 111 && mouseY > y && mouseY < y + 20;

        int i = 0;
        for (Part part : subParts) {
            if(part.shouldShow()) {
                if (dynamicAnimation.getValue() > 25 || open2) {
                    part.drawPart(x, (int) (y + (20 + i) * ((dynamicAnimation.getValue()) / 100)), mouseX, mouseY);
                }
                i += (int) (part.height * dynamicAnimation.getValue() / 100);
            }
        }
        height = 20 + (int) ((dynamicAnimation.getValue()/100)* i);
        if (!open2 || !open){
            dynamicAnimation.setTarget(0);
            for (Part part : subParts){
                part.forceclose();
            }
        }


        Gui.drawRect(x + 1, y + 1, x + 124, y + 15, 0xff111111);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        roundedRectShader.drawRectWithShader(x + 4, y + 3, 12, 12,0, 3, new Color(0x070707), new Color(0x070707));
        GlStateManager.color(255, 255, 255);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        fontRenderer.drawString(module.getName(), x + 20, y + 4.5f, new Color(255, 255, 255, (int) enableAnimation.getValue()).getRGB());
        fontRenderer.drawCenteredString(module.getKeycode() == 0 || module.getKeycode() == -1 ? "." : (Keyboard.getKeyName(module.getKeycode()).length() > 2 ? Keyboard.getKeyName(module.getKeycode()).substring(0, 2) : Keyboard.getKeyName(module.getKeycode())), x + 10.5f, y + 4.5f, new Color((int) enableAnimation.getValue(), (int) enableAnimation.getValue(), (int) enableAnimation.getValue()).getRGB());

        if(!module.getSettings().isEmpty())
            fontRenderer.drawCenteredString("+", x + 117, y + 4f, 0x40ffffff);

        super.drawPart(x, y, mouseX, mouseY);
    }
}
