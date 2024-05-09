package heart.modules;

import heart.Heart;
import heart.events.impl.KeyPressEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class ModuleManager {

    private static HashMap<String, Module> modules = new HashMap<>();

    public void addModule(Module module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    public Module getModule(String name) {
        return modules.get(name.toLowerCase());
    }

    public HashMap<String, Module> getModules() {
        return modules;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onKeyPressEvent(KeyPressEvent e) {
        for (Module module : modules.values()) {
            if(module.getKeycode() == e.keybind){
                module.setEnabled(!module.isEnabled());
            }
        }
    }

}
