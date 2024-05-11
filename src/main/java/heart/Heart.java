package heart;

import heart.commands.Command;
import heart.commands.CommandManager;
import heart.commands.impl.Toggle;
import heart.modules.Module;
import heart.modules.ModuleManager;
import heart.modules.impl.combat.Killaura;
import heart.ui.Hud;
import heart.ui.clickgui.ClickguiScreen;
import heart.ui.screen.impl.MainMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import org.greenrobot.eventbus.EventBus;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.Set;

public class Heart {
    static final Heart instance = new Heart();
    static final Hud hud = new Hud();
    static final EventBus bus = new EventBus();
    static final GuiMainMenu mainMenuScreen = new GuiMainMenu(); // static final MainMenuScreen mainMenuScreen = new MainMenuScreen();
    static ClickguiScreen clickgui = null;
    static ModuleManager moduleManager = new ModuleManager();
    static CommandManager commandManager = new CommandManager();


    public static Heart getInstance() {
        return instance;
    }

    public static Hud getHud() {
        return hud;
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static ClickguiScreen getClickgui() {
        return clickgui;
    }

    public static GuiMainMenu getMainMenuScreen() { // public static MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public void Init() throws InstantiationException, IllegalAccessException {

        try {
            System.out.println(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("heart/shader/RoundedRect.frag")).getResourceLocation().getResourcePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddModules();
        AddCommands();

        bus.register(moduleManager);

        clickgui = new ClickguiScreen();

    }

    private void AddModules() throws InstantiationException, IllegalAccessException {
        Reflections modulereflections = new Reflections("heart.modules.impl");
        Set<Class<? extends Module>> classes = modulereflections.getSubTypesOf(Module.class);

        for (Class<? extends Module> clazz : classes) {
            Module m = clazz.newInstance();
            moduleManager.addModule(m);
            m.onRegister();
        }
    }

    private void AddCommands() throws InstantiationException, IllegalAccessException {
        Reflections modulereflections = new Reflections("heart.commands.impl");
        Set<Class<? extends Command>> classes = modulereflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> clazz : classes) {
            commandManager.addCommand(clazz.newInstance());
        }
    }

    public static EventBus getBus() {
        return bus;
    }

}
