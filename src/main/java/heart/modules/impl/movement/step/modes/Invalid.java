package heart.modules.impl.movement.step.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Invalid extends Mode {
    public Invalid() {
        super("Invalid", "Step up blocks using invalid packets");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e) {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.movementInput.moveForward > 0 && mc.thePlayer.onGround) {
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.5, mc.thePlayer.posZ, true));
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, 0, mc.thePlayer.posZ, true));
        }
    }
}
