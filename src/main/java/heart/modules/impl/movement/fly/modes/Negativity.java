package heart.modules.impl.movement.fly.modes;

import heart.events.impl.PacketEvent;
import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.util.MotionUtil;
import heart.util.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Negativity extends Mode {
    public Negativity() {
        super("Negativity", "Negativity when positivity walks in");
    }

    public double speed = 0.1;

    public static BoolSetting boostSetting = new BoolSetting("Boost", "Boost Fly Speed", false);
    public static BoolSetting mushSetting = new BoolSetting("MushMC", "Bypass MushMC", false);

    @Override
    public void onEnable() {
        if (mushSetting.getValue()) mc.thePlayer.jump();
        super.onEnable();
    }

    public void onTick(TickEvent e) {
        if (mushSetting.getValue()) {
            mc.timer.timerSpeed = 0.8f;
            MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * 4);
        } else {
            if (boostSetting.getValue() && MotionUtil.isMoving() && mc.thePlayer.ticksExisted % 2 == 0 && (mc.thePlayer.onGround || mc.thePlayer.motionY == 0)) MotionUtil.strafe(9D);
            else MotionUtil.strafe(0.65);
        }
        if ((!mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) || mushSetting.getValue()) mc.thePlayer.motionY = 0;
        if (!mushSetting.getValue()) {
            if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.ticksExisted % (MotionUtil.isMoving() ? 2 : 4) == 0) mc.thePlayer.motionY = 0.35f;
            else if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.5f;
        }
    }

    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (!mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.motionY <= 0) {
                event.setCancelled(true);
                if (mc.thePlayer.ticksExisted % 16 == 0) speed = mc.thePlayer.posY;
                else {
                    speed = speed - 0.02f;
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, speed, mc.thePlayer.posZ, mc.thePlayer.rotationYawHead, mc.thePlayer.rotationPitchHead, mc.thePlayer.onGround));
                }
            }
            else speed = mc.thePlayer.posY;
        }
    }
}
