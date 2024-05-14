package heart.modules.impl.movement.step.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Dev extends Mode {
    public Dev() {
        super("Dev", "dev step");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e) {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.movementInput.moveForward > 0 && mc.thePlayer.onGround) {
            mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            mc.thePlayer.motionY = 0.37f;
            mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
        }
    }
}
