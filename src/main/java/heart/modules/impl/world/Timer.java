package heart.modules.impl.world;

import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.DoubleSetting;

public class Timer extends Module {
    public Timer() {
        super("Timer", "Increase speed the games runs at", Category.WORLD);
        initmodule();
    }

    DoubleSetting amount = new DoubleSetting("Amount", "Changes how much it changes timer speed", 0.1, 10, 1, 10);

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = (float) amount.getValue();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
    }
}
