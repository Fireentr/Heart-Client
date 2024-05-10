package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.modes.Mode;
import heart.modules.settings.impl.ModeSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;

public class ModeSettingPart extends Part {

    RoundedRectShader roundedRectShader;
    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 500);
    DynamicAnimation selectAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 500);

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hovered = false;

    ModeSetting modeSetting;

    public ModeSettingPart(ModeSetting modeSetting) {
        super(20);
        this.modeSetting = modeSetting;
    }

    int xpos = 0;
    int ypos = 0;

    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        xpos = x;
        ypos = y;
        hovered = mouseX > x && mouseX < x + 125 && mouseY > y && mouseY < y + 20;

        roundedRectShader.drawRectWithShader(x + 2, y + 20, 120, (float) dynamicAnimation.getValue() - 5, 0, 3, new Color(20, 20, 20, 255), new Color(30, 30, 30, 255));


        if(dynamicAnimation.getValue() > 2){

            int i = 0;
            for (Mode m : modeSetting.getModes()){
                if(modeSetting.getSelected().equals(m)) {
                    selectAnimation.setTarget((float) ((i * 20 + 20)));
                    roundedRectShader.drawRectWithShader(x + 3, (float) (1 + y + selectAnimation.getValue()* dynamicAnimation.getValue()/(modeSetting.getModes().size() * 20)), 118, 14, 2, 3, new Color(30, 30, 30, 0), new Color(40, 40, 40, 255));
                }

                fontRenderer.drawString(m.getName(), (float) (x + 5), (float) ((y + 3 + (20 + i * 20)* dynamicAnimation.getValue()/(modeSetting.getModes().size() * 20))), 0xff454545);
                GlStateManager.color(255, 255, 255);
                i++;
            }
        }
        height = (int) dynamicAnimation.getValue() + 20;
        dynamicAnimation.setTarget(open ? modeSetting.getModes().size() * 20 : 0);

        Gui.drawRect( x + 1, y, x + 124, y + 16, 0xff111111);
        fontRenderer.drawString(modeSetting.getName() + " - " + modeSetting.getSelected().getName(), x + 5, y + 3, new Color(255, 255, 255, 100).getRGB());
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(hovered && button == 1 && dynamicAnimation.getValue() > dynamicAnimation.targetValue - 1) {
            open = !open;
        }

        if(button == 0) {
            int i = 1;
            for (Mode m : modeSetting.getModes()) {
                if(x > xpos && x < xpos + 124 && y > ypos + i * 20 && y < ypos + i * 20 + 20) {

                    for(Mode mo : modeSetting.getModes()) {
                        if(mo.isEnabled()){
                            mo.setEnabled(false);
                        }
                        if(m.equals(mo)) {
                            modeSetting.setSelected(m);
                        }
                    }

                    modeSetting.setSelected(m);
                    System.out.println(m.getName());
                }
                i++;
            }
        }
        super.onMouseClick(x, y, button);
    }

    @Override
    public void forceclose(){
        open = false;
    }

    @Override
    public boolean shouldShow() {
        return modeSetting.shouldShow();
    }
}
