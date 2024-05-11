package heart.modules.settings;

import heart.modules.modes.Mode;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.ModeSetting;

public class Requirement {

    final Setting reqSetting;
    final Object requirementValue;

    public Requirement(Setting reqSetting, Object requirementValue){
        this.reqSetting = reqSetting;
        this.requirementValue = requirementValue;
    }

    public boolean getRequirementMet(){
        switch (reqSetting.getClass().getSimpleName()) {
            default:
                return true;
            case "BoolSetting":
                return ((BoolSetting) reqSetting).getValue() == (boolean) requirementValue;
            case "ModeSetting":
                return ((ModeSetting) reqSetting).getSelected().equals((Mode) requirementValue);
        }
    }

}
