package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.settings.impl.BoolSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.ColorUtil;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;

public class BooleanSettingPart extends Part {

    RoundedRectShader roundedRectShader;
    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 500);

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final BoolSetting boolSetting;

    private boolean hovered = false;

    public BooleanSettingPart(BoolSetting boolSetting) {
        super(20);
        this.boolSetting = boolSetting;
        dynamicAnimation.snapTo((float) (boolSetting.getValue() ? 1.0 : 0.0));
    }


    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        dynamicAnimation.setTarget((float) (boolSetting.getValue() ? 0.0 : 1.0));
        hovered = mouseX > x && mouseX < x + 125 && mouseY > y && mouseY < y + 20;

        roundedRectShader.drawRectWithShader(x + 7, y + 6, 6, 6, 1, 3, ColorUtil.mixColor(new Color(20, 20, 20), new Color(0xff8d0528), dynamicAnimation.getValue()), new Color(0x1F1F1F));
        GlStateManager.color(255, 255, 255);
        fontRenderer.drawString(boolSetting.getName(), x + 20, y + 4.5f, 0xff454545);
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(hovered && button == 0) {
            boolSetting.setValue(!boolSetting.getValue());
        }
        super.onMouseClick(x, y, button);
    }

    @Override
    public boolean shouldShow() {
        return boolSetting.shouldShow();
    }
}
