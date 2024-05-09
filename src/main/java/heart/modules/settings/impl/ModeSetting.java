package heart.modules.settings.impl;

import heart.Heart;
import heart.modules.modes.Mode;
import heart.modules.settings.Setting;

import java.util.List;

public class ModeSetting extends Setting {
    private final List<Mode> modes;
    Mode selected;

    public ModeSetting(String name, String description, List<Mode> modes) {
        super(name, description);
        this.modes = modes;
        setSelected(modes.get(0));
    }

    public void setSelected(Mode selected) {
        this.selected = selected;
    }

    public Mode getSelected() {
        return selected;
    }

    public List<Mode> getModes() {
        return modes;
    }
}
