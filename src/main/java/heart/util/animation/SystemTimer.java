package heart.util.animation;

public class SystemTimer {
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long getElapsed() {
        return System.currentTimeMillis()-lastMS;
    }

    public boolean hasElapsed(long time, boolean reset) {
        if(System.currentTimeMillis()-lastMS>time) {
            if(reset)
                reset();
            return true;
        }

        return false;
    }
}