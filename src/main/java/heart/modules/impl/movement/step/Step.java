package heart.modules.impl.movement.step;

import heart.modules.Category;
import heart.modules.Module;

public class Step extends Module {
    public Step() {
        super("Step", "Step up blocks quickly.", Category.MOVEMENT);
        initmodule();
    }
}