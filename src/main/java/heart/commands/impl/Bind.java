package heart.commands.impl;

import heart.Heart;
import heart.commands.Command;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super(new String[]{"b", "bind"}, "Bind a module to a key");
    }

    @Override
    public void runCommand(String[] arguments) {
        if (arguments.length == 3 && arguments[2] != null && Heart.getModuleManager().getModule(arguments[1].toLowerCase()) != null){
            Heart.getModuleManager().getModule(arguments[1].toLowerCase()).setKeycode(Keyboard.getKeyIndex(arguments[2].toUpperCase()));
        } else{

        }
        super.runCommand(arguments);
    }
}