package heart;

import com.google.gson.Gson;
import heart.commands.Command;
import heart.commands.CommandManager;
import heart.commands.impl.Toggle;
import heart.modules.Module;
import heart.modules.ModuleManager;
import heart.modules.impl.combat.Killaura;
import heart.modules.settings.impl.*;
import heart.ui.Hud;
import heart.ui.clickgui.ClickguiScreen;
import heart.ui.screen.impl.MainMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.reflections.Reflections;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

public class Heart {
    static final Heart instance = new Heart();
    static final Hud hud = new Hud();
    static final EventBus bus = new EventBus();
    static final GuiMainMenu mainMenuScreen = new GuiMainMenu(); // static final MainMenuScreen mainMenuScreen = new MainMenuScreen();
    static ClickguiScreen clickgui = null;
    static ModuleManager moduleManager = new ModuleManager();
    static CommandManager commandManager = new CommandManager();
    private File saveFileLocation = new File("heart/save.json");

    public File getSaveFileLocation() {
        return saveFileLocation;
    }


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

        if(!new File("heart").exists()) {
            new File("heart").mkdir();
        }

        saveFileLocation = new File("heart/save.json");

        if(saveFileLocation.canWrite()){
            try {
                load(saveFileLocation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void saveConfig(File file) throws FileNotFoundException {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        PrintWriter writer = new PrintWriter(file.getAbsolutePath());

        JSONObject jo = new JSONObject();


        getModuleManager().getModules().forEach((key, module) -> {
            HashMap<String, Object> modMap = new HashMap<>();

            modMap.put("enabled", module.isEnabled());
            modMap.put("key", module.getKeycode());

            HashMap<String, Object> setMap = new HashMap<>();

            module.getSettings().forEach((setKey, setting) ->{
                if(setting instanceof BoolSetting){
                    setMap.put(setKey, ((BoolSetting) setting).getValue());
                }
                if(setting instanceof EnumSetting){
                    setMap.put(setKey, ((EnumSetting) setting).getValue().ordinal());
                }
                if(setting instanceof IntSetting){
                    setMap.put(setKey, ((IntSetting) setting).getValue());
                }
                if(setting instanceof DoubleSetting){
                    setMap.put(setKey, ((DoubleSetting) setting).getValue());
                }
                if(setting instanceof ColorSetting){
                    setMap.put(setKey, ((ColorSetting)setting).getValue().getRGB());
                }
            });
            if(!setMap.isEmpty()){
                modMap.put("settings", setMap);
            }

            jo.put(key, modMap);
        });

        writer.println(jo);
        writer.close();
    }


    public void load(File file) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), StandardCharsets.UTF_8);

        JSONObject jo = new JSONObject(data);

        getModuleManager().getModules().forEach((key, module) -> {
            if (jo.has(key)) {
                JSONObject jsonObj = jo.getJSONObject(key);

                if(jsonObj.has("settings")){
                    JSONObject jsonSettingObj = jsonObj.getJSONObject("settings");
                    module.getSettings().forEach((setKey, setting) -> {
                        if(jsonSettingObj.has(setKey)){
                            if(setting instanceof BoolSetting){
                                ((BoolSetting) setting).setValue(jsonSettingObj.getBoolean(setKey));
                            }
                            if(setting instanceof EnumSetting){
                                ((EnumSetting) setting).setValue(jsonSettingObj.getInt(setKey));
                            }
                            if(setting instanceof IntSetting){
                                ((IntSetting) setting).setValue(jsonSettingObj.getInt(setKey));
                            }
                            if(setting instanceof DoubleSetting){
                                ((DoubleSetting) setting).setValue(jsonSettingObj.getDouble(setKey));
                            }
                            if(setting instanceof ColorSetting){
                                ((ColorSetting) setting).setValue(new Color(jsonSettingObj.getInt(setKey)));
                            }
                        }
                    });

                }
                module.setEnabledWithoutEvent(jsonObj.getBoolean("enabled"));
                module.setKeycode(jsonObj.getInt("key"));
            }
        });
    }

}
