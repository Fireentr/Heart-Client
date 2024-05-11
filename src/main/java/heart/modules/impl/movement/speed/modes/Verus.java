package heart.modules.impl.movement.speed.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.util.MotionUtil;
import net.minecraft.potion.Potion;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Verus extends Mode {
    public Verus() {
        super("Verus", "Bhop that bypasses Verus AntiCheat");
    }
    BoolSetting lowHopSetting = new BoolSetting("Low Hop", "", false);
    BoolSetting jumpBoostYportSetting = new BoolSetting("Jump Boost YPort", "", false);

    public void onTick(TickEvent e){
        if (mc.thePlayer.motionY > 0 && !mc.gameSettings.keyBindJump.isPressed() && !mc.thePlayer.onGround && lowHopSetting.getValue()) mc.thePlayer.motionY = MotionUtil.getChunkloadMotion();

        // Verus Bhop
        if (mc.thePlayer.onGround && MotionUtil.isMoving() && !mc.gameSettings.keyBindSneak.isPressed()) {mc.thePlayer.jump(); MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * (MotionUtil.getPotionStatus() ? 1.8 : 1.9));}
        if (!mc.thePlayer.onGround) {MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * (MotionUtil.getPotionStatus() ? 1.2:1.3));}

        // YPort
        if (jumpBoostYportSetting.getValue() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isPotionActive(Potion.jump) && MotionUtil.getPotionStatus() && mc.thePlayer.fallDistance < 0.05) {
            if (mc.thePlayer.onGround && mc.thePlayer.hurtTime < 10) { mc.thePlayer.motionY = 0.0051f;
                MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * (mc.thePlayer.isSprinting() ? 1.8:2.1)); }
            else if (mc.thePlayer.hurtTime < 10) MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * 2);
        }
    }
}
