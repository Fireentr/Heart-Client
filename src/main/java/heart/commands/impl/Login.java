package heart.commands.impl;

import heart.Heart;
import heart.commands.Command;
import heart.util.ChatUtil;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class Login extends Command {
    public Login() {
        super(new String[]{"l", "login"}, "Login with an offline username");
    }

    @Override
    public void runCommand(String[] args) {
        if(args.length == 2) {
            mc.session = new Session(args[1], "", "", "mojang");
            ChatUtil.notify("Set Offline Username to " + args[1]);
        }
        super.runCommand(args);
    }
}