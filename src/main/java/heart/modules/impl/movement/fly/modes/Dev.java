package heart.modules.impl.movement.fly.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Dev extends Mode {
    public Dev() {
        super("Dev", "Development Fly");
    }

    int ticks;

    @Override
    public void onEnable() {
        ticks = 0;
        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0)))));
//        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2.01, mc.thePlayer.posZ, false));
        super.onEnable();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        ticks++;
        if (ticks < 6) {
            mc.thePlayer.motionY = 0.9;
        }
//        if (ticks > 20) {
//            if (mc.thePlayer.ticksExisted % 2 == 0) mc.thePlayer.motionY = -0.155;
//            else mc.thePlayer.motionY = -0.1;
//        }
    }
}
