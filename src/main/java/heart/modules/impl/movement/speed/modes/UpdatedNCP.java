package heart.modules.impl.movement.speed.modes;

import heart.Heart;
import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;

public class UpdatedNCP extends Mode {

    public UpdatedNCP() {
        super("UpdatedNCP", "Speed for Latest NCP");
    }

    public boolean speed = false;
    boolean boost;
    public int boostCycles;

    public void onDisable() {
        boostCycles = 0;
    }

    public void onTick(TickEvent e) {
        if(mc.thePlayer.onGround && MotionUtil.isMoving()) {
            mc.thePlayer.motionY = 0.42f;
            MotionUtil.strafe(0.45);
        }

        if(mc.thePlayer.hurtTime == 10 && !Heart.getModuleManager().getModule("velocity").isEnabled()){
            MotionUtil.strafe(0.8);
        }

        if (!MotionUtil.getPotionStatus() || mc.thePlayer.hurtTime > 0) {
            MotionUtil.strafeCurrentSpeed(0);
        } else {
            MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() / 1.1);
        }
    }
}
