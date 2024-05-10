package heart.modules.impl.visual;

import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.ColorSetting;

import java.awt.*;

public class ESP2D extends Module {
    public ESP2D() {
        super("2D ESP", "CSGO-style wallhack", Category.VISUAL);
        initmodule();
    }

    ColorSetting ctest = new ColorSetting("ESP Color", "yup", Color.WHITE);
}