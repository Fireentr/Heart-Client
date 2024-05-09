package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.ModeSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.ColorUtil;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;

import java.awt.*;
import java.io.IOException;

public class ModeSettingPart extends Part {

    RoundedRectShader roundedRectShader;
    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 500);

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hovered = false;

    public ModeSettingPart(ModeSetting modeSetting) {
        super(20);
    }


    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        hovered = mouseX > x && mouseX < x + 125 && mouseY > y && mouseY < y + 20;

        if(open){

        }

    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(hovered && button == 1) {

        }
        super.onMouseClick(x, y, button);
    }
}
