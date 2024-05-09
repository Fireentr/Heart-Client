package heart.modules.settings.impl;

import heart.modules.modes.Mode;
import heart.modules.settings.Setting;

import java.util.List;

public class IntSetting extends Setting {
    private final int min;
    private final int max;

    private int value;

    public IntSetting(String name, String description, int min, int max, int defaultValue) {
        super(name, description);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setValue(int value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    public int getValue() {
        return value;
    }

}
