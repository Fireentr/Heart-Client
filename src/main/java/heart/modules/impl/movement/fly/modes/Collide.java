package heart.modules.impl.movement.fly.modes;

import heart.events.impl.AirBBEvent;
import heart.modules.modes.Mode;
import net.minecraft.util.AxisAlignedBB;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Collide extends Mode {
    public Collide() {
        super("Collide", "Spoofs the collision of blocks underneath the player");
    }

    private int startY;

    @Subscribe
    public void onEnable() {
        startY = (int) Math.floor(mc.thePlayer.posY);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAirBB(AirBBEvent e) {
        if (e.pos.getY() <= startY - 1)
            e.aabb = new AxisAlignedBB((double) (e.pos.getX() + e.block.minX), (double) (e.pos.getY() + e.block.minY), (double) (e.pos.getZ() + e.block.minZ), (double) (e.pos.getX() + e.block.maxX), (double) (e.pos.getY() + e.block.maxY), (double) (e.pos.getZ() + e.block.maxZ));

    }
}
