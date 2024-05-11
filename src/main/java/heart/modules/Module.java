package heart.modules;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.events.impl.RotationEvent;
import heart.events.impl.TickEvent;
import heart.modules.modes.Mode;
import heart.modules.settings.Requirement;
import heart.modules.settings.Setting;
import heart.modules.settings.impl.*;
import net.minecraft.client.Minecraft;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

public class Module {
    private final String name;
    private final String description;
    private final Category category;

    public Minecraft mc = Minecraft.getMinecraft();

    private boolean hidden = false;
    private boolean enabled = false;
    private int keycode = -1;
    private LinkedHashMap<String, Setting> settings = new LinkedHashMap<>();

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void initmodule(){
        for(Field f : this.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.getType().isAssignableFrom(BoolSetting.class) || f.getType().isAssignableFrom(IntSetting.class) || f.getType().isAssignableFrom(IntSetting.class) || f.getType().isAssignableFrom(DoubleSetting.class)  || f.getType().isAssignableFrom(ColorSetting.class) || f.getType().isAssignableFrom(EnumSetting.class)){
                Setting setting;
                try {
                    setting = (Setting) f.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (setting != null) {
                    settings.put(f.getName(), setting);
                }
            }
        }
    }


    //getters and setters


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private List<Mode> modes = new ArrayList<>();

    public HashMap<String, Setting> getSettings() {
        return settings;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable();
            Heart.getBus().register(this);
            if(!modes.isEmpty())
                ((ModeSetting)getSettings().get("mode")).getSelected().setEnabled(true);
        } else{
            onDisable();
            Heart.getBus().unregister(this);
            if(!modes.isEmpty())
                ((ModeSetting)getSettings().get("mode")).getSelected().setEnabled(false);
        }
    }

    public void onRegister() throws InstantiationException, IllegalAccessException {
        String modePackage = this.getClass().getPackage().getName() + ".modes";

        Reflections modulereflections = new Reflections(modePackage);
        Set<Class<? extends Mode>> classes = modulereflections.getSubTypesOf(Mode.class);

        if(!classes.isEmpty()){
            for (Class<? extends Mode> clazz : classes) {
                modes.add(clazz.newInstance());
            }
            settings.put("mode", new ModeSetting("Mode", "Mode", modes));
            if(!modes.isEmpty()){
                for (Mode mode : modes) {
                    for(Field f : mode.getClass().getDeclaredFields()){
                        f.setAccessible(true);
                        if(f.getType().isAssignableFrom(BoolSetting.class) || f.getType().isAssignableFrom(IntSetting.class) || f.getType().isAssignableFrom(IntSetting.class) || f.getType().isAssignableFrom(DoubleSetting.class) || f.getType().isAssignableFrom(ColorSetting.class)  || f.getType().isAssignableFrom(EnumSetting.class)){
                            Setting setting;
                            try {
                                setting = (Setting) f.get(mode);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                            if (setting != null) {
                                settings.put(f.getName(), setting.addRequirement(new Requirement(settings.get("mode"), mode)));
                            }
                        }
                    }
                }
            }
        }

    }

    public int getKeycode() {
        return keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }


    public String getSuffix(){
        return "";
    }

    //events



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
}

