package heart.commands;


import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {
    private HashMap<String[], Command> commands = new HashMap<>();

    public void addCommand(Command command) {
        commands.put(command.getNames(), command);
    }

    public Command getCommand(String name) {
        for (Command command : commands.values()) {
            if (Arrays.asList(command.getNames()).contains(name)) {
                return command;
            }
        }
        return null;
    }
}
