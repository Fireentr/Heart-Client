package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.settings.impl.IntSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class IntSettingPart extends Part {

    RoundedRectShader roundedRectShader;
    DynamicAnimation dynamicAnimation = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    CFontRenderer smallFontRenderer = new CFontRenderer(new Font("Product Sans", Font.PLAIN, 14));

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final IntSetting intSetting;

    private boolean hovered = false;
    private boolean selecting = false;

    public IntSettingPart(IntSetting intSetting) {
        super(20);
        this.intSetting = intSetting;
        dynamicAnimation.snapTo(getAsPercentile());
    }


    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        hovered = mouseX > x + 5 && mouseX < x + 105 && mouseY > y && mouseY < y + 20;

        roundedRectShader.drawRectWithShader(x + 5, y + 13, 100, 0.2f, 0, 1, new Color(30, 30, 30), new Color(30, 30, 30));
        roundedRectShader.drawRectWithShader(x + 5, y + 13, (float) dynamicAnimation.getValue(), 0.2f, 0, 1, Color.WHITE, Color.WHITE);
        roundedRectShader.drawRectWithShader((float) (x + dynamicAnimation.getValue() + 5), y + 11.8f, 3, 3, 0, 3, Color.WHITE, Color.WHITE);

        GlStateManager.color(255, 255, 255);
        smallFontRenderer.drawString(intSetting.getName(), x + 5, y + 1.5f, 0xff454545);
        GlStateManager.color(255, 255, 255);
        smallFontRenderer.drawCenteredString(Integer.toString(intSetting.getValue()), x + 115, y + 4.5f, 0xff454545);

        dynamicAnimation.setTarget(getAsPercentile());

        if (selecting && Mouse.isButtonDown(0)) {
            setValue(mouseX - (x + 5));
        }else
            selecting = false;
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(hovered && button == 0) {
            selecting = true;
        }
        super.onMouseClick(x, y, button);
    }

    @Override
    public boolean shouldShow() {
        return intSetting.shouldShow();
    }

    public float getAsPercentile(){
        return ((float) (intSetting.getValue() - intSetting.getMin()) / (intSetting.getMax() - intSetting.getMin())) * 100;
    }

    public void setValue(float value) {
        intSetting.setValue((int) (value/100 * (intSetting.getMax() - intSetting.getMin()) + intSetting.getMin()));
    }
}
