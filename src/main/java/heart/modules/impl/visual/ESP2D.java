package heart.modules.impl.visual;

import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.IntSetting;

public class ESP2D extends Module {
    public ESP2D() {
        super("2D ESP", "CSGO-style wallhack", Category.VISUAL);
        initmodule();
    }

    IntSetting test = new IntSetting("hiii", "yup", 5, 100, 50);
}