package heart.ui.clickgui.components.impl.partimpl.settings;

import heart.modules.settings.impl.ColorSetting;
import heart.ui.clickgui.components.impl.Part;
import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class ColorSettingPart extends Part {

    RoundedRectShader roundedRectShader;
    DynamicAnimation dynamicAnimationHue = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    DynamicAnimation dynamicAnimationSat = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    DynamicAnimation dynamicAnimationBrig = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    DynamicAnimation dynamicAnimationOpen = new DynamicAnimation(EasingStyle.ExpoOut, 300);
    CFontRenderer smallFontRenderer = new CFontRenderer(new Font("Product Sans", Font.PLAIN, 14));

    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final ColorSetting colorSetting;

    private boolean hoveredhue = false;
    private boolean hoveredsat = false;
    private boolean hoveredbrig = false;
    private boolean selectinghue = false;
    private boolean selectingsat = false;
    private boolean selectingbrig = false;

    public ColorSettingPart(ColorSetting colorSetting) {
        super(60);
        this.colorSetting = colorSetting;
        dynamicAnimationHue.snapTo((float) getHueAsPercentile());
        dynamicAnimationSat.snapTo((float) getSaturationAsPercentile());
        dynamicAnimationBrig.snapTo((float) getBrightnessAsPercentile());
        dynamicAnimationOpen.snapTo(0);
    }


    @Override
    public void drawPart(int x, int y, int mouseX, int mouseY) {
        if(open) {
            dynamicAnimationOpen.setTarget(40);
        }else {
            dynamicAnimationOpen.setTarget(0);
        }


        hoveredhue = mouseX > x + 5 && mouseX < x + 105 && mouseY > y && mouseY < y + 20;
        hoveredsat = mouseX > x + 5 && mouseX < x + 105 && mouseY > y + 20 && mouseY < y + 40;
        hoveredbrig = mouseX > x + 5 && mouseX < x + 105 && mouseY > y + 40 && mouseY < y + 60;


        height = (int) dynamicAnimationOpen.getValue() + 20;
        if(dynamicAnimationOpen.getValue() > 5) {

            roundedRectShader.drawRectWithShader(x + 2, y, 120, height - 2, 0, 1, new Color(20, 20, 20, 255), new Color(20, 20, 20, 255));

            //hue
            roundedRectShader.drawRectWithShader(x + 5, (float) (y + (19) * (dynamicAnimationOpen.getValue() / 40)), 100, 0.2f, 0, 1, new Color(30, 30, 30), new Color(30, 30, 30));
            Color hueCol = new Color(Color.HSBtoRGB((float) (getHueAsPercentile() / 100f), 1, 1));
            roundedRectShader.drawRectWithShader(x + 5, (float) (y + (19) * (dynamicAnimationOpen.getValue() / 40)), (float) dynamicAnimationHue.getValue(), 0.2f, 0, 1, hueCol, hueCol);
            roundedRectShader.drawRectWithShader((float) (x + dynamicAnimationHue.getValue() + 5), (float) (y + (18) * (dynamicAnimationOpen.getValue() / 40)), 3, 3, 0, 3, hueCol, hueCol);

            //saturation
            roundedRectShader.drawRectWithShader(x + 5, (float) (y + (16 + 18) * (dynamicAnimationOpen.getValue() / 40)), 100, 0.2f, 0, 1, new Color(30, 30, 30), new Color(30, 30, 30));
            Color satCol = new Color(Color.HSBtoRGB((float) (getHueAsPercentile() / 100f), (float) (getSaturationAsPercentile() / 100f), 1));
            roundedRectShader.drawRectWithShader(x + 5, (float) (y + (16 + 18) * (dynamicAnimationOpen.getValue() / 40)), (float) dynamicAnimationSat.getValue(), 0.2f, 0, 1, satCol, satCol);
            roundedRectShader.drawRectWithShader((float) (x + dynamicAnimationSat.getValue() + 5), (float) (y + (13 + 20) * (dynamicAnimationOpen.getValue() / 40)), 3, 3, 0, 3, satCol, satCol);

            //brightness
            roundedRectShader.drawRectWithShader(x + 5, (float) (y + (13 + 36) * (dynamicAnimationOpen.getValue() / 40)), 100, 0.2f, 0, 1, new Color(30, 30, 30), new Color(30, 30, 30));
            Color brigCol = new Color(Color.HSBtoRGB((float) (getHueAsPercentile() / 100f), 1, (float) (getBrightnessAsPercentile() / 100f)));
            roundedRectShader.drawRectWithShader((float) (x + 5), (float) (y + (13 + 36) * (dynamicAnimationOpen.getValue() / 40)), (float) dynamicAnimationBrig.getValue(), 0.2f, 0, 1, brigCol, brigCol);
            roundedRectShader.drawRectWithShader((float) (x + dynamicAnimationBrig.getValue() + 5), (float) (y + (13 + 35) * (dynamicAnimationOpen.getValue() / 40)), 3, 3, 0, 3, brigCol, brigCol);


            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawString("Hue", x + 5, (float) (y + 8.8f * (dynamicAnimationOpen.getValue() / 40)), 0xff252525);

            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawCenteredString(Integer.toString((int) (getHueAsPercentile())), x + 117, y + (float) (14.8f * (dynamicAnimationOpen.getValue() / 40)), 0xff404040);

            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawCenteredString(Integer.toString((int) (getSaturationAsPercentile())), x + 117, y + (float) (30.5f * (dynamicAnimationOpen.getValue() / 40)), 0xff404040);

            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawCenteredString(Integer.toString((int) getBrightnessAsPercentile()), x + 117, y + (float) (45.5f * (dynamicAnimationOpen.getValue() / 40)), 0xff404040);

            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawString("Saturation", (float) (x + 5), (float) (y + 24 * (dynamicAnimationOpen.getValue() / 40)), 0xff252525);


            GlStateManager.color(255, 255, 255);
            smallFontRenderer.drawString("Brightness", (float) (x + 5), (float) (y + 4 + 35 * (dynamicAnimationOpen.getValue() / 40)), 0xff252525);

            roundedRectShader.drawRectWithShader(x + 2, y, 120, 9, 0, 1, new Color(14, 14, 14), new Color(14, 14, 14));

        }
        GlStateManager.color(255, 255, 255);

        fontRenderer.drawString(colorSetting.getName(), x + 5, y + 5, new Color(70, 70, 70, (int) (255*(1 - (dynamicAnimationOpen.getValue()/40)))).getRGB());
        GlStateManager.color(255, 255, 255);

        smallFontRenderer.drawString(colorSetting.getName(), x + 5, y + 1, new Color(70, 70, 70, (int) (255*((dynamicAnimationOpen.getValue()/40)))).getRGB());

        roundedRectShader.drawRectWithShader(x + 109 - (float) (1 * (1 - dynamicAnimationOpen.getValue())) /40, y + 2, 12 + (float) ((1 - dynamicAnimationOpen.getValue())) /40, 12 + (float)( (1 - dynamicAnimationOpen.getValue())) /40, 0, 1, colorSetting.getValue(), Color.white);


        dynamicAnimationHue.setTarget((float) getHueAsPercentile());
        dynamicAnimationSat.setTarget((float) getSaturationAsPercentile());
        dynamicAnimationBrig.setTarget((float) getBrightnessAsPercentile());

        if(hoveredbrig){
            System.out.println("hue");
        }

        if (selectinghue && Mouse.isButtonDown(0)) {
            setValue(mouseX - (x + 5), (float) getSaturationAsPercentile(), (float) getBrightnessAsPercentile());
        }else
            selectinghue = false;

        if (selectingsat && Mouse.isButtonDown(0)) {
            setValue((float) getHueAsPercentile(), mouseX - (x + 5), (float) getBrightnessAsPercentile());
            System.out.println(mouseX - (x + 5));
        }else
            selectingsat = false;

        if (selectingbrig && Mouse.isButtonDown(0)) {
            setValue((float) getHueAsPercentile(), (float) getSaturationAsPercentile(), mouseX - (x + 5));
        }else
            selectingbrig = false;
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        if(button == 0  && dynamicAnimationOpen.getValue() > 5) {
            selectinghue = hoveredhue;
            selectingsat = hoveredsat;
            selectingbrig = hoveredbrig;
        }

        if(button == 1 && hoveredhue && (dynamicAnimationOpen.getValue() > 5 ||  dynamicAnimationOpen.getValue() < 60 - 5))
            open = !open;

        super.onMouseClick(x, y, button);
    }

    @Override
    public boolean shouldShow() {
        return colorSetting.shouldShow();
    }

    public double getHueAsPercentile(){
        return Color.RGBtoHSB(colorSetting.getValue().getRed(), colorSetting.getValue().getGreen(), colorSetting.getValue().getBlue(), null)[0] * 100;
    }

    public double getSaturationAsPercentile(){
        return Color.RGBtoHSB(colorSetting.getValue().getRed(), colorSetting.getValue().getGreen(), colorSetting.getValue().getBlue(), null)[1] * 100;
    }

    public double getBrightnessAsPercentile(){
        return Color.RGBtoHSB(colorSetting.getValue().getRed(), colorSetting.getValue().getGreen(), colorSetting.getValue().getBlue(), null)[2] * 100;
    }

    public void setValue(float hue, float saturation, float brightness) {
        colorSetting.setValue(new Color(Color.HSBtoRGB(Math.min(Math.max(hue/100, 0), 0.996f), Math.min(Math.max(saturation/100, 0), 0.996f), Math.min(Math.max(brightness/100, 0), 0.996f))));
    }

    @Override
    public void forceclose() {
        open = false;
        super.forceclose();
    }
}
