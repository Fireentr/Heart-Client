package heart.modules.impl.movement.speed.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.util.MotionUtil;
import net.minecraft.client.Minecraft;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BunnyHop extends Mode {
    public BunnyHop() {
        super("BunnyHop", "Vanilla BHop");
    }

    BoolSetting test = new BoolSetting("Test", "TESING", false);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        MotionUtil.strafe(MotionUtil.getBaseMoveSpeed() * 1.5);
        if(Minecraft.getMinecraft().thePlayer.onGround){
            Minecraft.getMinecraft().thePlayer.jump();
        }
    }
}
