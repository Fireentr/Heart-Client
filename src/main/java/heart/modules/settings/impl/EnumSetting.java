package heart.modules.settings.impl;

import heart.modules.settings.Setting;

public class EnumSetting<E extends Enum<E>> extends Setting {
    private final E[] values;
    private E selectedValue;

    public EnumSetting(String name, String description, E[] options) {
        super(name, description);
        values = options;
        selectedValue = values[0];
    }

    public void setValue(int value) {
        selectedValue = values[value];
    }

    public E getValue() {
        return selectedValue;
    }

    public E[] getValues() {
        return values;
    }

}