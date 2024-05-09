package heart.util;

import java.awt.*;

public class ColorUtil {

    static double startTime = System.currentTimeMillis();

    public static float getRainbowHue(int delay, int speed, float bright, float saturation) {
        return Color.HSBtoRGB((System.currentTimeMillis() + delay) % speed / speed, saturation, bright);
    }

    public static int getChillRainbow(int delay) {
        return Color.HSBtoRGB((System.currentTimeMillis() + delay) % (int) 4000.0F / 4000.0f, 0.55F, 1F);
    }

    public static int getAstolfoRainbow(int speed, int var2, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + ((long) var2 * speed)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }

    public static Color colorWave(Color color1, Color color2, double offset, int speed){

        double wavingColorFloat = (((Math.sin((System.currentTimeMillis() - startTime - offset)*speed/500))+1)/2);

        return new Color((int) (color1.getRed()*wavingColorFloat + color2.getRed()*(1.0 - wavingColorFloat)),
                (int) (color1.getGreen()*wavingColorFloat + color2.getGreen()*(1.0 - wavingColorFloat)),
                (int) (color1.getBlue()*wavingColorFloat + color2.getBlue()*(1.0 - wavingColorFloat)));
    }

    public static Color mixColor(Color color1, Color color2, double mixamount){
        return new Color((int) (color1.getRed()*mixamount + color2.getRed()*(1.0 - mixamount)),
                (int) (color1.getGreen()*mixamount + color2.getGreen()*(1.0 - mixamount)),
                (int) (color1.getBlue()*mixamount + color2.getBlue()*(1.0 - mixamount)));
    }

    public static Color brandColor = new Color(255, 0, 62);
}