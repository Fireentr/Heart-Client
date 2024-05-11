package heart.modules.impl.movement.speed.modes;

import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.util.MotionUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Vanilla extends Mode {
    public Vanilla() {
        super("Vanilla", "Vanilla speed");
    }

    DoubleSetting speedSetting = new DoubleSetting("Speed", "Motion Speed", 0.1f, 5f, 1f, 10);
    BoolSetting bhopSetting = new BoolSetting("BunnyHop", "Automatically jump on ground", false);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * speedSetting.getValue());
        if (mc.thePlayer.onGround && bhopSetting.getValue() && MotionUtil.isMoving()) mc.thePlayer.motionY = 0.42f;
    }
}
