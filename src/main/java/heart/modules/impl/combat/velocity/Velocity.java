package heart.modules.impl.combat.velocity;

import heart.modules.Category;
import heart.modules.Module;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Prevents player movement from being hit", Category.COMBAT);
        initmodule();
    }
}
