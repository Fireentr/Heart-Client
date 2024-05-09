package heart.ui.clickgui.components.impl;

import heart.Heart;
import heart.modules.Category;
import heart.ui.clickgui.ClickguiScreen;
import heart.ui.clickgui.components.Component;
import heart.ui.clickgui.components.impl.partimpl.ModulePart;
import heart.util.animation.Animation;
import heart.util.animation.DynamicAnimation;
import heart.util.animation.EasingStyle;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.util.concurrent.atomic.AtomicInteger;

public class CategoryCompontenent extends Component {
    public Category category;

    public CategoryCompontenent(Category category, char icon, int x, int y) {
        super(category.name() + "§7 (7)", icon, x, y, 250/2, 0);

        Heart.getModuleManager().getModules().forEach((key, module) -> {
            if(module.getCategory().equals(category)) {
                parts.add(new ModulePart(20, module));
            }
        });
        this.category = category;
    }

    @Override
    public void DrawComponentContent(int mouseX, int mouseY) {
        super.DrawComponentContent(mouseX, mouseY);
        AtomicInteger i = new AtomicInteger(0);
        for(Part part : parts) {
            part.drawPart(getX(), getY() + i.get(), mouseX, mouseY);
            i.set(i.get() + part.height);
        }
    }

    public int getHeight(){
        int i = 0;
        for(Part part : parts) {
            i += part.height;
        }
        System.out.println("sdffs");
        return i;
    }
}