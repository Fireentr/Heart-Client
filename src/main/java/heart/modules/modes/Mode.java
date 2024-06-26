package heart.modules.modes;

import heart.Heart;
import heart.events.impl.*;
import heart.modules.settings.Setting;
import net.minecraft.client.Minecraft;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;


public class Mode {
    private final String name;
    private final String description;
    private boolean enabled = false;

    public Minecraft mc = Minecraft.getMinecraft();

    public Mode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable();
            Heart.getBus().register(this);
        } else{
            onDisable();
            Heart.getBus().unregister(this);
        }
    }

    public void setEnabledWithoutEvent(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            Heart.getBus().register(this);
        } else{
            Heart.getBus().unregister(this);
        }
    }

    public String getName() {
        return name;
    }

    public void onEnable(){

    }

    public void onDisable(){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRender2D(Render2DEvent e){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRotate(RotationEvent e){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBlockCollide(CollisionEvent e){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPacket(PacketEvent e){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAirBB(AirBBEvent e){

    }

    public LinkedHashMap<String, Setting> settings = new LinkedHashMap<>();
}