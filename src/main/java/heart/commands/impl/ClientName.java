package heart.commands.impl;

import heart.Heart;
import heart.commands.Command;
import heart.modules.impl.visual.Hud;
import org.lwjgl.input.Keyboard;

public class ClientName extends Command {
    public ClientName() {
        super(new String[]{"cn", "clientname"}, "Set the HUD clientname");
    }

    @Override
    public void runCommand(String[] arguments) {
        if (arguments.length > 1 && arguments[1] != null) {
            ((Hud) Heart.getModuleManager().getModule("hud")).setClientName(String.join(" ", arguments).replace(arguments[0], "").substring(1));
        }
        super.runCommand(arguments);
    }
}