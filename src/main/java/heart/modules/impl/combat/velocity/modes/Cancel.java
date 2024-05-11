package heart.modules.impl.combat.velocity.modes;

import heart.events.impl.PacketEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.DoubleSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.greenrobot.eventbus.Subscribe;

public class Cancel extends Mode {
    public Cancel() {
        super("Basic", "Basic velocity");
    }


    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId())
            event.setCancelled(true);
    }
}
