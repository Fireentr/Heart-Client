package heart.modules.impl.visual;

import heart.Heart;
import heart.modules.Category;
import heart.modules.Module;
import heart.ui.clickgui.ClickguiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;

public class Clickgui extends Module {
    public Clickgui() {
        super("ClickGui", "Render the client gui", Category.VISUAL);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(Heart.getClickgui());
        this.setEnabled(false);
        super.onEnable();
    }
}