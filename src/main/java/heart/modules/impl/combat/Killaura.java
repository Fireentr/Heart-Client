package heart.modules.impl.combat;

import heart.events.impl.RotationEvent;
import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.modules.settings.impl.EnumSetting;
import heart.util.RotationUtil;
import heart.util.animation.EasingStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

import java.util.Timer;

import static heart.events.impl.Direction.POST;

public class Killaura extends Module {
    public Killaura() {
        super("KillAura", "Attack Nearby Entities", Category.COMBAT);
        initmodule();
    }

    DoubleSetting apsSetting = new DoubleSetting("APS", "Sets the killaura's APS/CPS.", 1, 20, 12, 10);
    EnumSetting<sortingMode> sortingModeSetting = new EnumSetting<>("Sort", "Sets the way entities get sorted.", sortingMode.values());
    EnumSetting<autoBlockMode> autoBlockSetting = new EnumSetting<>("AutoBlock", "AutoBlock Type.", autoBlockMode.values());
    BoolSetting attackOtherEntities = new BoolSetting("Atack non-players", "Attack other entities.", false);
    EnumSetting<autoBlockMode> targetHudSetting = new EnumSetting<>("Target HUD", "Displays a HUD element that gives info about targets.", autoBlockMode.values());
    EnumSetting<EasingStyle> rotationEasingSetting = new EnumSetting<>("Easing", "Sets the way entities get sorted.", EasingStyle.values());

    @Override
    public String getSuffix() {
        return sortingModeSetting.getDisplayName();
    }

    int i = 0;
    long nanoDelay = 0;
    long lastUpdateTime = 0;

    @Override
    public void onTick(TickEvent e){
        if (e.direction.equals(POST))
            return;


        i++;
        if (i > 20){
            i = 0;
            nanoDelay = (long) (1_000_000_000L / apsSetting.getValue());
            lastUpdateTime = System.nanoTime();
        }

        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastUpdateTime;

        boolean hasSwung = false;

        if(sortingModeSetting.getValue() == sortingMode.MULTI){
            for (Entity entity : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
                if (entity != Minecraft.getMinecraft().thePlayer && entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= 3.1 && (entity instanceof EntityPlayer || entity instanceof EntityCreature) && !(entity instanceof EntityArmorStand)) {
                    if (elapsedTime >= nanoDelay) {
                        if (!hasSwung)
                            Minecraft.getMinecraft().thePlayer.swingItem();
                        hasSwung = true;
                        Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entity);
                    }
                }
            }
            return;
        }
        if(getTarget() != null) {
            if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                switch (autoBlockSetting.getValue()) {
                    default:

                        break;
                    case VANILLA:
                        // Vanilla AB
                        break;
                    case FAKE:
                        mc.thePlayer.itemInUseCount = 1;
                        break;
                }
            }

            if (elapsedTime >= nanoDelay) {
                Minecraft.getMinecraft().thePlayer.swingItem();
                Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, getTarget());
            }
        } else if(!GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem)) mc.thePlayer.itemInUseCount = 0;
    }



    @Override
    public void onRotate(RotationEvent e) {
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

    @Override
    public void onDisable() {
        mc.thePlayer.itemInUseCount = 0;
        super.onDisable();
    }
}

enum sortingMode {
    FOV, DISTANCE, HEALTH, SWITCH, MULTI
}

enum autoBlockMode {
    NONE, FAKE, VANILLA
}

enum targetHudModes {
    NONE, HEART, OLDNOVOLINE, RISE, REMIX, EXHIBITION
}