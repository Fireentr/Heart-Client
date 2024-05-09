package heart.commands.impl;

import heart.Heart;
import heart.commands.Command;

public class Toggle extends Command {
    public Toggle() {
        super(new String[]{"t", "toggle"}, "Toggle a module");
    }

    @Override
    public void runCommand(String[] arguments) {
        if (arguments.length == 2 && Heart.getModuleManager().getModule(arguments[1].toLowerCase()) != null){
            Heart.getModuleManager().getModule(arguments[1].toLowerCase()).setEnabled(!Heart.getModuleManager().getModule(arguments[1].toLowerCase()).isEnabled());
        } else{
            //thing
        }
        super.runCommand(arguments);
    }
}