package heart.modules.impl.movement;

import heart.events.impl.RotationEvent;
import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.EnumSetting;
import heart.util.MotionUtil;
import heart.util.RotationUtil;
import heart.util.animation.EasingStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;

import static heart.events.impl.Direction.POST;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints for you", Category.COMBAT);
        initmodule();
    }
    BoolSetting allDirections = new BoolSetting("All Directions", "Sprint in all directions.", false);
    @Override
    public void onTick(TickEvent e) {
        if (MotionUtil.canSprint(allDirections.getValue())) mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}