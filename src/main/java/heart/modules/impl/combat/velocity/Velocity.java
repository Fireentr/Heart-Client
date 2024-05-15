package heart.modules.impl.combat.velocity;

import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.ModeSetting;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Prevents player movement from being hit", Category.COMBAT);
        initmodule();
    }

    @Override
    public String getSuffix() {
        return ((ModeSetting)this.getSettings().get("mode")).getSelected().getName();
    }
}
