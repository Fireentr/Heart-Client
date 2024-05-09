package heart.modules.settings;

public class Setting {
    private final String name;
    private final String description;

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}