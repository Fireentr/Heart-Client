package heart.modules.settings.impl;

import heart.modules.settings.Setting;

import java.awt.*;

public class ColorSetting extends Setting {

    private Color value;

    public ColorSetting(String name, String description, Color defaultValue) {
        super(name, description);
        this.value = defaultValue;
    }

    public void setValue(Color value) {
        this.value = value;
    }

    public Color getValue() {
        return value;
    }

}
