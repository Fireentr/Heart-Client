package heart.modules.impl.movement.fly.modes;

import heart.Heart;
import heart.events.impl.AirBBEvent;
import heart.events.impl.CollisionEvent;
import heart.events.impl.PacketEvent;
import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.DoubleSetting;
import heart.util.MotionUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Vulcan extends Mode {
    public Vulcan() {
        super("Vulcan", "Vulcan fly");
    }

    public final ConcurrentLinkedQueue<Packet> linkedQueue = new ConcurrentLinkedQueue<>();
    public static DoubleSetting vulcanTimer = new DoubleSetting("Timer Boost", "zoom", 1, 8, 1, 10);

    double startY = 0;
    public boolean teleported;
    public boolean disabled;
    int packetAmount;
    int airTicks;

    public void onEnable() {
        packetAmount = 0;
        teleported = false;
        mc.timer.timerSpeed = 0.9f;
        startY = mc.thePlayer.posY;
        if (disabled) mc.thePlayer.jump();
    }

    public void onTick(TickEvent e) {
        if (packetAmount > 5) mc.timer.timerSpeed = (float) vulcanTimer.getValue();
        if (disabled) MotionUtil.strafe(-MotionUtil.getBaseMoveSpeed() / 1.2);
        if (disabled) {
            mc.thePlayer.setSprinting(false);
            airTicks++;
            mc.timer.timerSpeed = 25f;
        }
        if (airTicks > 120) Heart.getModuleManager().getModule("fly").setEnabled(false);
    }

    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook && mc.thePlayer.ticksExisted > 20) {
            event.setCancelled(true);
            packetAmount++;
            teleported = true;
        }
    }

    public void onDisable() {
        airTicks = 0;
        if (disabled) { disabled = false; teleported = false;}
        if (teleported) {
            Heart.getModuleManager().getModule("fly").setEnabled(false);;
            disabled = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAirBB(AirBBEvent e) {
        if (this.isEnabled()) {
            e.aabb = new AxisAlignedBB((double) (e.pos.getX() + e.block.minX), (double) (e.pos.getY() + e.block.minY) - 1, (double) (e.pos.getZ() + e.block.minZ), (double) (e.pos.getX() + e.block.maxX), (double) (e.pos.getY() + e.block.maxY) - 2, (double) (e.pos.getZ() + e.block.maxZ));
        }
    }
}
