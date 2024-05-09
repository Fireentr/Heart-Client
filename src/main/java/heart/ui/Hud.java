package heart.ui;

import heart.Heart;
import heart.events.impl.Render2DEvent;
import heart.modules.Module;
import heart.util.CFontRenderer;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;

import java.awt.*;
import java.util.Objects;

public class Hud {
    public CFontRenderer fontRenderer = new CFontRenderer(new Font("Product Sans", 0, 18));

    public void drawHud(float partialTicks){
        if (Heart.getBus().hasSubscriberForEvent(Render2DEvent.class)) {
            Heart.getBus().postSticky(new Render2DEvent());
        }
    }

}