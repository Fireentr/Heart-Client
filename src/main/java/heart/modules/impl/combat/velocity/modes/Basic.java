package heart.modules.impl.combat.velocity.modes;

import heart.events.impl.PacketEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.DoubleSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Basic extends Mode {
    public Basic() {
        super("Basic", "Basic velocity");
    }

    DoubleSetting horiz = new DoubleSetting("Horizontal", "X", 0, 100, 100, 1);
    DoubleSetting vert = new DoubleSetting("Vertical", "Y", 0, 100, 100, 1);


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
            S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) event.getPacket();
            velocity.motionX = (int) (velocity.motionX * ((horiz.getValue() / 100)));
            velocity.motionY = (int) (velocity.motionY * ((vert.getValue() / 100)));
            velocity.motionZ = (int) (velocity.motionZ * ((horiz.getValue() / 100)));
        }
    }
}
