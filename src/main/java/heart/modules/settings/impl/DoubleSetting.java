package heart.modules.settings.impl;

import heart.modules.settings.Setting;

public class DoubleSetting extends Setting {
    private final double min;
    private final double max;
    private final double multiple;

    private double value;

    public DoubleSetting(String name, String description, double min, double max, double defaultValue, double multiple) {
        super(name, description);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
        this.multiple = multiple;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, ((double)Math.round(value*multiple))/multiple));
    }

    public double getValue() {
        return value;
    }

}
