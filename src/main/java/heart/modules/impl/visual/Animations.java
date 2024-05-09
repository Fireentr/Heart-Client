package heart.modules.impl.visual;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.util.CFontRenderer;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Animations extends Module {
    public Animations() {
        super("Animations", "Animates sword blocking", Category.VISUAL);
    }

}