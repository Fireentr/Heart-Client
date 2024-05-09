package heart.commands.impl;

import heart.Heart;
import heart.commands.Command;
import heart.modules.modes.Mode;
import heart.modules.settings.impl.ModeSetting;

public class Setting extends Command {
    public Setting() {
        super(new String[]{"s", "setting"}, "Toggle a module");
    }

    @Override
    public void runCommand(String[] arguments) {
        if (arguments.length == 4 && Heart.getModuleManager().getModule(arguments[1].toLowerCase()) != null && Heart.getModuleManager().getModule(arguments[1].toLowerCase()).getSettings().containsKey(arguments[2].toLowerCase())){
            heart.modules.settings.Setting setting = Heart.getModuleManager().getModule(arguments[1].toLowerCase()).getSettings().get(arguments[2].toLowerCase());
            switch (setting.getClass().getName()) {
                case "heart.modules.settings.impl.ModeSetting":

                    for(Mode m : ((ModeSetting)setting).getModes()) {
                        if(m.isEnabled()){
                            m.setEnabled(false);
                        }
                        if(m.getName().equalsIgnoreCase(arguments[3])) {
                            m.setEnabled(true);
                            ((ModeSetting)setting).setSelected(m);
                        }
                    }
                    System.out.println("ASAWAAA");
                    break;

            }
        }

        super.runCommand(arguments);
    }
}