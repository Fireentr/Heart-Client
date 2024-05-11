package heart.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MotionUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float getSpeed() {
        return (float) (Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ));
    }

    public static void vClip(double offset) {
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ);
    }

    public static double getBPS() {
        Vec3 lastPos = new Vec3(mc.thePlayer.lastTickPosX, 0, mc.thePlayer.lastTickPosZ);
        Vec3 pos = new Vec3(mc.thePlayer.posX, 0, mc.thePlayer.posZ);
        return roundToPlace(Math.abs(lastPos.distanceTo(pos) * 20) * mc.timer.timerSpeed, 2);
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isMoving() {
        if (mc.thePlayer == null || mc.thePlayer.movementInput == null) {
            return false;
        }
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0f || mc.thePlayer.movementInput.moveStrafe != 0f);
    }

    public static void strafe(Double speed) {
        if (!isMoving()){
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
            return;
        };
        float yaw = (float) getDirection();
        EntityPlayerSP thePlayer = mc.thePlayer;
        thePlayer.motionX = -Math.sin(yaw) * speed;
        thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    static double prevModifier = 0;

    public static void strafeCurrentSpeed(double modifier) {
        double currentVel = Math.sqrt((mc.thePlayer.motionX * mc.thePlayer.motionX) + (mc.thePlayer.motionZ * mc.thePlayer.motionZ));

        strafe(currentVel + modifier - prevModifier*2);

        prevModifier = modifier;
    }

    public static void hClip(Double distance) {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(
                (x + Math.sin(3.14 + MotionUtil.getDirection()) * distance),
                y,
                (z + Math.cos(MotionUtil.getDirection()) * distance));
    }

    public static double getDirection() {
        EntityPlayerSP thePlayer = mc.thePlayer;
//        Float rotationYaw = mc.renderManager.playerViewY;
        Float rotationYaw = thePlayer.rotationYaw;
        if (thePlayer.moveForward < 0f) rotationYaw += 180f;
        Float forward = 1f;
        if (thePlayer.moveForward < 0f) forward = -0.5f; else if (thePlayer.moveForward > 0f) forward = 0.5f;
        if (thePlayer.moveStrafing > 0f) rotationYaw -= 90f * forward;
        if (thePlayer.moveStrafing < 0f) rotationYaw += 90f * forward;
        return Math.toRadians(rotationYaw);
    }

    public static boolean canSprint(Boolean allDirections) {
        return (mc.thePlayer.movementInput.moveForward >= 0.8 || allDirections) && !mc.thePlayer.isCollidedHorizontally && (mc.thePlayer.getFoodStats().getFoodLevel() > 6) && !mc.thePlayer.isSneaking() && (!mc.thePlayer.isUsingItem()) && MotionUtil.isMoving();
    }

    public static double getBaseMoveSpeed() {
        double base = mc.thePlayer.isSneaking() ? 0.0663 : (canSprint(false) ? 0.2873 : 0.221);
        final PotionEffect moveSpeed = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed);
        final PotionEffect moveSlowness = mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown);
        if (moveSpeed != null) {
            base *= 1.0 + 0.2 * (moveSpeed.getAmplifier() + 1);
        }
        if (moveSlowness != null) {
            base *= 1.0 + 0.2 * (moveSlowness.getAmplifier() + 1);
        }
        if (mc.thePlayer.isInWater()) {
            base *= 0.52;
        }
        if (mc.thePlayer.isInLava()) {
            base *= 0.52;
        }
        return base;
    }

    public static boolean getPotionStatus() {
        if ((!mc.thePlayer.isSprinting() && MotionUtil.getBaseMoveSpeed() > 0.26) ||
                mc.thePlayer.isSprinting() && MotionUtil.getBaseMoveSpeed() > 0.3) {
            return true;
        } else {return false;}
    }

    public static boolean getPotionStatus2() {
        if ((!mc.thePlayer.isSprinting() && MotionUtil.getBaseMoveSpeed() > 0.3) ||
                mc.thePlayer.isSprinting() && MotionUtil.getBaseMoveSpeed() > 0.35) {
            return true;
        } else {return false;}
    }

    public static boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty())
                return true;
        }
        return false;
    }

    public static double getChunkloadMotion() {
        return -0.0784000015258789;
    }
}
