package heart.modules.impl.movement.step.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.util.MotionUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Legit extends Mode {
    public Legit() {
        super("Legit", "Automatically jump on edge of block like 1.9+");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e) {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.movementInput.moveForward > 0 && mc.thePlayer.onGround) mc.thePlayer.jump();
    }
}
