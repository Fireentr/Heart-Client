package heart.modules.impl.combat;

import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;

public class Killaura extends Module {
    public Killaura() {
        super("Killaura", "Attack Nearby Entities", Category.COMBAT);
    }

    @Override
    public void onTick(TickEvent e){
        System.out.println("WOOHOO");
    }
}