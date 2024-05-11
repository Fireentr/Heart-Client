package heart.modules.impl.movement.fly.modes.verus;

import heart.events.impl.CollisionEvent;
import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;

public class VerusDrop extends Mode {
    public VerusDrop() {
        super("VerusDrop", "Damageless fast fly");
    }

    public double speed = 0.1;
    double startY;
    public int airTime;
    double jumpTick;

    @Override
    public void onEnable() {
        startY = Math.round(mc.thePlayer.posY - 0.5);
        speed = 0;
        airTime = 0;
        mc.thePlayer.jump();
    }

    @Override
    public void onTick(TickEvent e) {
        if (airTime > 0) airTime--;
        if (mc.thePlayer.onGround) mc.timer.timerSpeed = 1;
        if (!mc.gameSettings.keyBindSneak.isPressed() && !mc.gameSettings.keyBindSprint.isPressed() && MotionUtil.isMoving()) {
            // Handle Jump
            if (mc.thePlayer.posY > startY + 0.5 && mc.thePlayer.hurtTime == 0) {
                startY = startY + 0.5;
                mc.thePlayer.motionY = MotionUtil.getChunkloadMotion();
            }
            // Stable Fly
            if (mc.thePlayer.ticksExisted % 12 == 0) {
                setSpeed(MotionUtil.getBaseMoveSpeed() * 1.8);
                jumpTick = mc.thePlayer.ticksExisted;
                startY = startY - 0.5;
            } else setSpeed(MotionUtil.getBaseMoveSpeed() * 1.2);
            // Fall & Jump
            if (mc.thePlayer.onGround && mc.thePlayer.ticksExisted > jumpTick) {
                jumpTick = mc.thePlayer.ticksExisted * 256;
                setSpeed(MotionUtil.getBaseMoveSpeed() * 1.8);
                mc.thePlayer.jump();
            }
        } else if (mc.gameSettings.keyBindSneak.isPressed()) {
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                setSpeed(MotionUtil.getBaseMoveSpeed() * 1.2);
                mc.thePlayer.motionY = 0.42f;
                jumpTick = mc.thePlayer.ticksExisted;
                startY = Math.round(mc.thePlayer.posY - 0.5);
            }
        } else if (mc.gameSettings.keyBindSprint.isPressed()) {
            if (!mc.thePlayer.onGround) setSpeed(0.3);
            jumpTick = mc.thePlayer.ticksExisted;
            if (mc.thePlayer.onGround) startY = Math.round(mc.thePlayer.posY - 2.5);
        } else {
            MotionUtil.strafe(0D);
            jumpTick = mc.thePlayer.ticksExisted;
            if (mc.thePlayer.onGround) mc.thePlayer.jump();
            if (mc.thePlayer.posY > startY + 0.3) mc.thePlayer.motionY = MotionUtil.getChunkloadMotion();
        }
    }

    @Override
    public void onCollide(CollisionEvent e) {
        e.collisionX = mc.thePlayer.posX;
        e.collisionY = startY - 1;
        e.collisionZ = mc.thePlayer.posZ;
        e.override = true;
    }

    public void setSpeed(double speed) {
        if (mc.thePlayer.hurtTime > 0 && airTime < 5) airTime = 30;
        else MotionUtil.strafe(speed);
    }
}
