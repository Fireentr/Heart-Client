package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.settings.impl.DoubleSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class DoubleSettingPart extends Part {

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

    private final DoubleSetting doubleSetting;

    private boolean hovered = false;
    private boolean selecting = false;

    public DoubleSettingPart(DoubleSetting doubleSetting) {
        super(20);
        this.doubleSetting = doubleSetting;
        dynamicAnimation.snapTo((float) getAsPercentile());
    }


    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        hovered = mouseX > x + 5 && mouseX < x + 105 && mouseY > y && mouseY < y + 20;

        roundedRectShader.drawRectWithShader(x + 5, y + 13, 100, 0.2f, 0, 1, new Color(30, 30, 30), new Color(30, 30, 30));
        roundedRectShader.drawRectWithShader(x + 5, y + 13, (float) dynamicAnimation.getValue(), 0.2f, 0, 1, new Color(0xff8d0528), new Color(0xff8d0528));
        roundedRectShader.drawRectWithShader((float) (x + dynamicAnimation.getValue() + 5), y + 11.8f, 3, 3, 0, 3, new Color(0xff8d0528), new Color(0xff8d0528));

        GlStateManager.color(255, 255, 255);
        smallFontRenderer.drawString(doubleSetting.getName(), x + 5, y + 1.5f, 0xff454545);
        GlStateManager.color(255, 255, 255);
        smallFontRenderer.drawCenteredString(Double.toString(doubleSetting.getValue()), x + 117, y + 9, 0xff454545);

        dynamicAnimation.setTarget((float) getAsPercentile());

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
        return doubleSetting.shouldShow();
    }

    public double getAsPercentile(){
        return (doubleSetting.getValue() - doubleSetting.getMin()) / (doubleSetting.getMax() - doubleSetting.getMin()) * 100;
    }

    public void setValue(float value) {
        doubleSetting.setValue((value/100 * (doubleSetting.getMax() - doubleSetting.getMin()) + doubleSetting.getMin()));
    }
}
