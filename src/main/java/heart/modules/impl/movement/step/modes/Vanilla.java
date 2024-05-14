package heart.modules.impl.movement.step.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Vanilla extends Mode {
    public Vanilla() {
        super("Vanilla", "Step up a block when colliding with it");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e) {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.movementInput.moveForward > 0 && mc.thePlayer.onGround) MotionUtil.vClip(0.2);
    }
}
