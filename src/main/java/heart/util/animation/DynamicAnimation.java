package heart.util.animation;


public class DynamicAnimation {
    Animation anim;
    public float currentValue = 1f, targetValue = 1f;
    public DynamicAnimation(EasingStyle easingStyle, long lengthInMs) {
        anim = new Animation(easingStyle, lengthInMs);
    }

    public void setTarget(float newTarget) {
        this.currentValue = (float) getValue();
        anim.reset();
        this.targetValue = newTarget;
    }

    public void snapTo(float newTarget) {
        currentValue = newTarget;
        targetValue = newTarget;
    }

    public boolean isDone() {
        return anim.done;
    }

    public double getValue() {
        if(anim.done) {
            if(currentValue != targetValue)
                currentValue = targetValue;
            return currentValue;
        }
        return currentValue+((targetValue-currentValue)*anim.getValue());
    }
}