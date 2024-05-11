package heart.modules.impl.visual;

import heart.modules.Category;
import heart.modules.Module;

public class Fullbright extends Module {
    public Fullbright() {
        super("FullBright", "Gamma increaser", Category.VISUAL);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 1000;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
