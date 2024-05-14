package heart.modules.impl.movement.speed.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.util.MotionUtil;

public class Negativity extends Mode {

    BoolSetting boostSetting = new BoolSetting("Boost", "zoom zoom", false);
    BoolSetting mushSetting = new BoolSetting("MushMC", "Bypass MushMC", false);

    public Negativity() {
        super("Negativity", "Negativity speed");
    }
    public void onTick(TickEvent event) {
        if (mc.thePlayer.onGround && MotionUtil.isMoving() && !boostSetting.getValue()) mc.thePlayer.motionY = 0.42f;
        if (MotionUtil.isMoving() && mc.thePlayer.ticksExisted % 2 == 0 && (mc.thePlayer.onGround || mc.thePlayer.motionY == 0) && boostSetting.getValue()) MotionUtil.strafe(9D);
        else MotionUtil.strafe(mushSetting.getValue() ? 0.45 : 0.65);
    }

}
