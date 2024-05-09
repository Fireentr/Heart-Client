package heart.util.animation;

public class Animation {
    public EasingStyle easing = EasingStyle.ExpoOut;
    public long max;
    long cTime; // current time
    public boolean done = false;
    SystemTimer tim;

    public Animation(EasingStyle easingStyle, long lengthInMs) {
        easing = easingStyle;
        max = lengthInMs;
        tim = new SystemTimer();
        tim.reset();
    }

    public void reset() {
        done = false;
        tim.reset();
        cTime = 0;
    }

    public void updateTime() {
        if(cTime < max)
            cTime = tim.getElapsed();
        else {
            cTime = max;
            done = true;
        }
    }

    /*
     * This will get the value percentage in a decimal.
     * all you have to do is * the getValue() by the end result that you want.
     */
    public float getValue() {
        updateTime();
        float finVal = 0;
        float bufVal = ((float) cTime)/((float)max);

        switch (easing) {

            case SineIn:
                finVal = EasingUtil.easeInSine(bufVal);
                break;
            case SineOut:
                finVal = EasingUtil.easeOutSine(bufVal);
                break;
            case SineInOut:
                finVal = EasingUtil.easeInOutSine(bufVal);
                break;
            case QuadIn:
                finVal = EasingUtil.easeInQuad(bufVal);
                break;
            case QuadOut:
                finVal = EasingUtil.easeOutQuad(bufVal);
                break;
            case QuadInOut:
                finVal = EasingUtil.easeInOutQuad(bufVal);
                break;
            case CubicIn:
                finVal = EasingUtil.easeInCubic(bufVal);
                break;
            case CubicOut:
                finVal = EasingUtil.easeOutCubic(bufVal);
                break;
            case CubicInOut:
                finVal = EasingUtil.easeInOutCubic(bufVal);
                break;
            case QuartIn:
                finVal = EasingUtil.easeInQuart(bufVal);
                break;
            case QuartOut:
                finVal = EasingUtil.easeOutQuart(bufVal);
                break;
            case QuartInOut:
                finVal = EasingUtil.easeInOutQuart(bufVal);
                break;
            case QuintIn:
                finVal = EasingUtil.easeInQuint(bufVal);
                break;
            case QuintOut:
                finVal = EasingUtil.easeOutQuint(bufVal);
                break;
            case QuintInOut:
                finVal = EasingUtil.easeInOutQuint(bufVal);
                break;
            case ExpoIn:
                finVal = EasingUtil.easeInExpo(bufVal);
                break;
            case ExpoOut:
                finVal = EasingUtil.easeOutExpo(bufVal);
                break;
            case ExpoInOut:
                finVal = EasingUtil.easeInOutExpo(bufVal);
                break;
            case CircIn:
                finVal = EasingUtil.easeInCirc(bufVal);
                break;
            case CircOut:
                finVal = EasingUtil.easeOutCirc(bufVal);
                break;
            case CircInOut:
                finVal = EasingUtil.easeInOutCirc(bufVal);
                break;
            case BackIn:
                finVal = EasingUtil.easeInBack(bufVal);
                break;
            case BackOut:
                finVal = EasingUtil.easeOutBack(bufVal);
                break;
            case BackInOut:
                finVal = EasingUtil.easeInOutBack(bufVal);
                break;
            case ElasticIn:
                finVal = EasingUtil.easeInElastic(bufVal);
                break;
            case ElasticOut:
                finVal = EasingUtil.easeOutElastic(bufVal);
                break;
            case ElasticInOut:
                finVal = EasingUtil.easeInOutElastic(bufVal);
                break;
            case BounceIn:
                finVal = EasingUtil.easeInBounce(bufVal);
                break;
            case BounceInOut:
                finVal = EasingUtil.easeOutBounce(bufVal);
                break;
            case BounceOut:
                finVal = EasingUtil.easeInOutBounce(bufVal);
                break;
        }
        return finVal;
    }
}