package heart.util.animation;


public class EasingUtil {
    static float PI = (float) Math.PI;



    /////////// INFO ///////////
    // Big Thanks to https://easings.net/ for the Animation Functions
    // We don't own them, translated from Typescript to Java
    //  All animations
    //  preview can be find at https://easings.net/
    // Simple Usage:
    //   Example: AnimationUtil.easeInSine(0.3);
    //   Means 30% of total render time
    //   Will output the thing you want
    //   Which is 0.1089934758116321
    //   Means 0.10899...% of the total value you want to display
    // Translated by fan87, Animations by easings.net
    ///////////////////////////



    public static float easeInSine(float t) {
        return (float) (1 - Math.cos((t * Math.PI) / 2));
    }

    public static float easeOutSine(float t) {
        return (float) Math.cos((t * Math.PI) / 2);
    }

    public static float easeInOutSine(float t) {
        return (float) (-(Math.cos(PI * t) - 1) / 2);
    }

    public static float easeInQuad(float t) {
        return t*t;
    }

    public static float easeOutQuad(float t) {
        return 1 - (1 - t) * (1 - t);
    }

    public static float easeInOutQuad(float t) {
        return t < 0.5 ? 2 * t * t : (float) (1 - Math.pow(-2 * t + 2, 2) / 2);
    }

    public static float easeInCubic(float t) {
        return t * t * t;
    }

    public static float easeOutCubic(float t) {
        return (float) (1 - Math.pow(1 - t, 3));
    }

    public static float easeInOutCubic(float t) {
        return t < 0.5 ? 4 * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 3) / 2);
    }

    public static float easeInQuart(float t) {
        return t * t * t * t;
    }

    public static float easeOutQuart(float t) {
        return (float) (1 - Math.pow(1 - t, 4));
    }

    public static float easeInOutQuart(float t) {
        return t < 0.5 ? 8 * t * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 4) / 2);
    }

    public static float easeInQuint(float t) {
        return t * t * t * t * t;
    }

    public static float easeOutQuint(float t) {
        return (float) (1 - Math.pow(1 - t, 5));
    }

    public static float easeInOutQuint(float t) {
        return t < 0.5 ? 16 * t * t * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 5) / 2);
    }

    public static float easeInExpo(float t) {
        return t == 0 ? 0 : (float) Math.pow(2, 10 * t - 10);
    }

    public static float easeOutExpo(float t) {
        return t == 1 ? 1 : (float) (1 - Math.pow(2, -10 * t));
    }

    public static float easeInOutExpo(float t) {
        return t == 0
                ? 0
                : (float) (t == 1
                ? 1
                : t < 0.5 ? Math.pow(2, 20 * t - 10) / 2
                : (2 - Math.pow(2, -20 * t + 10)) / 2);
    }

    public static float easeInCirc(float t) {
        return (float) (1 - Math.sqrt(1 - Math.pow(t, 2)));
    }

    public static float easeOutCirc(float t) {
        return (float) Math.sqrt(1 - Math.pow(t - 1, 2));
    }

    public static float easeInOutCirc(float t) {
        return (float) (t < 0.5
                ? (1 - Math.sqrt(1 - Math.pow(2 * t, 2))) / 2
                : (Math.sqrt(1 - Math.pow(-2 * t + 2, 2)) + 1) / 2);
    }

    public static float easeInBack(float t) {
        float c1 = 1.70158F;
        float c3 = c1 + 1;
        return c3 * t * t * t - c1 * t * t;
    }

    public static float easeOutBack(float t) {
        float c1 = 1.70158F;
        float c3 = c1 + 1;
        return (float) (1 + c3 * Math.pow(t - 1, 3) + c1 * Math.pow(t - 1, 2));
    }

    public static float easeInOutBack(float t) {
        float c1 = 1.70158F;
        float c2 = (float) (c1 * 1.525);

        return (float) (t < 0.5
                ? (Math.pow(2 * t, 2) * ((c2 + 1) * 2 * t - c2)) / 2
                : (Math.pow(2 * t - 2, 2) * ((c2 + 1) * (t * 2 - 2) + c2) + 2) / 2);
    }

    public static float easeInElastic(float t) {
        float c4 = (float) ((2 * Math.PI) / 3);

        return t == 0
                ? 0
                : (float) (t == 1
                ? 1
                : -Math.pow(2, 10 * t - 10) * Math.sin((t * 10 - 10.75) * c4));
    }

    public static float easeOutElastic(float t) {
        float c4 = (float) ((2 * Math.PI) / 3);

        return t == 0
                ? 0
                : (float) (t == 1
                ? 1
                : Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * c4) + 1);
    }

    public static float easeInOutElastic(float t) {
        float c5 = (float) ((2 * Math.PI) / 4.5);
        return t == 0
                ? 0
                : (float) (t == 1
                ? 1
                : t < 0.5
                ? -(Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125) * c5)) / 2
                : (Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125) * c5)) / 2 + 1);
    }

    public static float easeInBounce(float t) {
        return 1 - easeOutBounce(1 - t);
    }

    public static float easeOutBounce(float t) {
        float n1 = 7.5625F;
        float d1 = 2.75F;
        if (t < 1 / d1) {
            return n1 * t * t;
        } else if (t < 2 / d1) {
            return (float) (n1 * (t -= 1.5 / d1) * t + 0.75);
        } else if (t < 2.5 / d1) {
            return (float) (n1 * (t -= 2.25 / d1) * t + 0.9375);
        } else {
            return (float) (n1 * (t -= 2.625 / d1) * t + 0.984375);
        }
    }

    public static float easeInOutBounce(float t) {
        return t < 0.5
                ? (1 - easeOutBounce(1 - 2 * t)) / 2
                : (1 + easeOutBounce(2 * t - 1)) / 2;
    }
}