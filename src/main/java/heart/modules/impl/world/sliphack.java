package heart.modules.impl.world;

import heart.events.impl.PacketEvent;
import heart.events.impl.RotationEvent;
import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.ColorSetting;
import heart.util.MotionUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class sliphack extends Module {
    public sliphack() {
        super("Slip Hack", "Makes them fools slip up on you", Category.WORLD);
        initmodule();
    }

    int ticks;

    @Override
    public void onTick(TickEvent e) {
        mc.thePlayer.motionY = 0;
        if (ticks < 360) ticks += 15;
        else ticks = 0;
        super.onTick(e);
    }

    @Override
    public void onRotate(RotationEvent e) {
        mc.thePlayer.rotationYawHead = ticks;
        mc.thePlayer.rotationPitchHead = 3;

        e.setPitch(3);
        e.setYaw(mc.thePlayer.rotationYawHead);
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(
                (x + Math.sin(3.14 + mc.thePlayer.rotationYawHead) * 0.5),
                y,
                (z + Math.cos(mc.thePlayer.rotationYawHead) * 0.5));
        super.onRotate(e);
    }

    @Override
    public void onPacket(PacketEvent e) {
        super.onPacket(e);
    }
}