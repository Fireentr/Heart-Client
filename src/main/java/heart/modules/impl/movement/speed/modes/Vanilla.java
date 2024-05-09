package heart.modules.impl.movement.speed.modes;

import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Vanilla extends Mode {
    public Vanilla() {
        super("Vanilla", "Vanilla speed");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * 1.5);
    }
}
