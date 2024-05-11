package heart.commands;

import net.minecraft.client.Minecraft;

public class Command {

    private String description;
    private String[] names;

    public Minecraft mc = Minecraft.getMinecraft();

    public Command(String[] names, String description){
        this.names = names;
        this.description = description;
    }

    public String[] getNames() {
        return names;
    }

    public String getDescription() {
        return description;
    }

    public void runCommand(String[] arguments){

    }
}
