package heart.modules.settings.impl;

import heart.modules.settings.Setting;

public class BoolSetting extends Setting {
    private boolean enabled;

    public BoolSetting(String name, String description, boolean defaultValue) {
        super(name, description);
        this.enabled = defaultValue;
    }

    public boolean getValue() {
        return enabled;
    }
    public void setValue(boolean value) {
        this.enabled = value;
    }
}
