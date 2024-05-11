package heart.ui.clickgui;

import heart.modules.Category;
import heart.ui.clickgui.components.Component;
import heart.ui.clickgui.components.impl.CategoryCompontenent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class ClickguiScreen extends GuiScreen {

    public ArrayList<Component> components;

    public ClickguiScreen() {
        super();
        components = new ArrayList<>();


        components.add(new CategoryCompontenent(Category.COMBAT, 'a', 24, 44));
        components.add(new CategoryCompontenent(Category.MOVEMENT, 'a', 164, 44));
        components.add(new CategoryCompontenent(Category.VISUAL, 'a', 304, 44));
        components.add(new CategoryCompontenent(Category.PLAYER, 'a', 444, 44));
        components.add(new CategoryCompontenent(Category.WORLD, 'a', 584, 44));
    }

    @Override
    public boolean doesGuiPauseGame() {return false;}

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Component component : components) {
            component.DrawComponent(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Component component : components) {
            component.onMouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
