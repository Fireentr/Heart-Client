package heart.modules.impl.movement.fly.modes;

import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.util.MotionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Vanilla extends Mode {
    public Vanilla() {
        super("Vanilla", "Vanilla fly");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        MotionUtil.strafe(1.0);
        Minecraft.getMinecraft().thePlayer.motionY = 0;
        if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()){
            Minecraft.getMinecraft().thePlayer.motionY = 0.75;
        }

        if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()){
            Minecraft.getMinecraft().thePlayer.motionY = -0.75;
        }
    }
}
