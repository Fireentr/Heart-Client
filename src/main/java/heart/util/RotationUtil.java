package heart.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {

    static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(double x, double y, double z) {
        x = x - 1d;
        y = y - 1d;
        z = z - 1d;
        float[] ret = new float[] { 0, 0 };

        for (double xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.1D) {
            for (double ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.1D) {
                for (double zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.1D) {
                    final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
                    final Vec3 posVec = new Vec3(x, y, z).addVector(xSearch, ySearch, zSearch);
//                    final double dist = eyesPos.distanceTo(posVec);

                    final double diffX = posVec.xCoord - eyesPos.xCoord;
                    final double diffY = posVec.yCoord - eyesPos.yCoord;
                    final double diffZ = posVec.zCoord - eyesPos.zCoord;

                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

                    ret[0] = MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F);
                    ret[1] = MathHelper.wrapAngleTo180_float((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)));
                }
            }
        }

        return ret;
    }

}
