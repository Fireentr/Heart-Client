package heart.modules.modes;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.events.impl.TickEvent;
import heart.modules.settings.Setting;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;


public class Mode {
    private final String name;
    private final String description;
    private boolean enabled = false;

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


    public LinkedHashMap<String, Setting> settings = new LinkedHashMap<>();
}