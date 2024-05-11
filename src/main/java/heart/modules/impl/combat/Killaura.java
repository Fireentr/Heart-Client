package heart.modules.impl.combat;

import heart.events.impl.RotationEvent;
import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.EnumSetting;
import heart.util.RotationUtil;
import heart.util.animation.EasingStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;

import static heart.events.impl.Direction.POST;

public class Killaura extends Module {
    public Killaura() {
        super("Killaura", "Attack Nearby Entities", Category.COMBAT);
        initmodule();
    }

    EnumSetting<sortingMode> sortingModeSetting = new EnumSetting<>("Sort", "Sets the way entities get sorted.", sortingMode.values());
    BoolSetting attackOtherEntities = new BoolSetting("Atack non-players", "Attack other entities.", false);

    EnumSetting<EasingStyle> rotationEasingSetting = new EnumSetting<>("Easing", "Sets the way entities get sorted.", EasingStyle.values());


    int i = 0;
    @Override
    public void onTick(TickEvent e){
        if (e.direction.equals(POST))
            return;

        i++;
        if (i > 20){
            i = 0;
        }
        System.out.println(i + " | " + e.direction.name());

        boolean hasSwung = false;

        if(sortingModeSetting.getValue() == sortingMode.MULTI){
            for (Entity entity : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
                if (entity != Minecraft.getMinecraft().thePlayer && entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= 6.0 && (entity instanceof EntityPlayer || entity instanceof EntityCreature) && !(entity instanceof EntityArmorStand)) {
                    if(!hasSwung)
                        Minecraft.getMinecraft().thePlayer.swingItem();
                    hasSwung = true;
                    Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entity);
                }
            }
            return;
        }
        if(getTarget() != null) {
            Minecraft.getMinecraft().thePlayer.swingItem();
            Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, getTarget());
        }

    }



    @Override
    public void onRotate(RotationEvent e) {
        System.out.println("COCK!");
        Entity target = getTarget();
        e.setPitch(4);
        if(target != null){
            float[] rots = RotationUtil.getRotations(target.posX, target.posY + target.getEyeHeight(), target.posZ);

            e.setPitch(rots[0]);
            e.setYaw(rots[1]);

            mc.thePlayer.rotationYawHead = rots[0];
            mc.thePlayer.rotationPitchHead = rots[1];
            mc.thePlayer.renderYawOffset = rots[0];

        }
        super.onRotate(e);
    }

    public Minecraft mc= Minecraft.getMinecraft();

    public EntityLivingBase getTarget() {
        EntityLivingBase target = null;

        float max = 0;

        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if(entity instanceof EntityLivingBase) {
                if (entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= 6.0 && entity != Minecraft.getMinecraft().thePlayer && entity.isEntityAlive() && !(entity instanceof EntityArmorStand)) {
                    switch (sortingModeSetting.getValue()){
                        case HEALTH:
                            if(((EntityLivingBase) entity).getHealth() > max){
                                target = (EntityLivingBase) entity;
                                max = -((EntityLivingBase) entity).getHealth();
                            }
                            break;
                        case DISTANCE:
                            if(10 - entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > max){
                                target = (EntityLivingBase) entity;
                                max = 10 - entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
                            }
                            break;
                        case FOV:
                            target = (EntityLivingBase) entity;
                            break;
                    }
                }
            }
        }
        return target;
    }

    public static double convertToRange(double degrees) {
        double normalizedDegrees = degrees % 360;
        if (normalizedDegrees > 180) {
            normalizedDegrees -= 360;
        }
        if (normalizedDegrees < -180) {
            normalizedDegrees += 360;
        }

        return normalizedDegrees;
    }
}

enum sortingMode {
    FOV, DISTANCE, HEALTH, SWITCH, MULTI
}