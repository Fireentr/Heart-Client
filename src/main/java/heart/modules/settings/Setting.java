package heart.modules.settings;

import java.util.ArrayList;

public class Setting {
    private final String name;
    private final String description;

    private ArrayList<Requirement> requirements = new ArrayList<>();


    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Setting addRequirement(Requirement requirement) {
        requirements.add(requirement);
        return this;
    }

    public String getName() {
        return name;
    }


    public boolean shouldShow(){
        for(Requirement requirement : requirements){
            if(!requirement.getRequirementMet())
                return false;
        }
        return true;
    }
}
