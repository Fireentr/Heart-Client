package heart.modules.impl.movement.fly.modes.verus;

import heart.Heart;
import heart.events.impl.PacketEvent;
import heart.events.impl.Render2DEvent;
import heart.events.impl.TickEvent;
import heart.modules.impl.visual.Hud;
import heart.modules.modes.Mode;
import heart.util.CFontRenderer;
import heart.util.ChatUtil;
import heart.util.DamageUtil;
import heart.util.MotionUtil;
import heart.util.shader.impl.RoundedRectShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerusDamage extends Mode {
    public VerusDamage() {
        super("Verus", "Verus damage");
    }

    boolean damaged;
    int ticks;

    CFontRenderer fontRenderer = new CFontRenderer(new Font("Product Sans", Font.PLAIN, 18));
    RoundedRectShader roundedRectShader;
    {
        try {
            roundedRectShader = new RoundedRectShader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        ticks = 0;
        damaged = false;
        mc.thePlayer.jump();
        DamageUtil.damage("verus");
        mc.timer.timerSpeed = 0.3f;
        super.onEnable();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent e){
        ticks++;
        mc.timer.timerSpeed = 1f;
        if (mc.thePlayer.hurtTime > 0) damaged = true;
        if (damaged && ticks > 5 && ticks < 200) {
            MotionUtil.strafe(1D);
            mc.thePlayer.motionY = 0;
        }
        if (ticks > 200) Heart.getModuleManager().getModule("fly").setEnabled(false);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (mc.thePlayer.ticksExisted % 9 != 0 && e.getPacket() instanceof C03PacketPlayer && ticks < 200) e.setCancelled(true);
        super.onPacket(e);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        super.onDisable();
    }

    @Override
    public void onRender2D(Render2DEvent e) {
        Hud hud = (Hud) Heart.getModuleManager().getModule("hud");
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        int width = 100 - ticks / 2;

        int textWidth = fontRenderer.getStringWidth(100 - ticks / 2 + "%");
        roundedRectShader.drawRectWithShader((sr.getScaledWidth() / 2f) - 54, sr.getScaledHeight() - 90, 104, 12, 1, 5, new Color(0, 0, 0, 180), new Color(50, 50, 50, 200));
        roundedRectShader.drawRectWithShader((sr.getScaledWidth() / 2f) - 52, sr.getScaledHeight() - 88, width, 8, 1, 3, hud.getColor(1, 1), hud.getColor(1, 1));
        GlStateManager.popAttrib();
        fontRenderer.drawString(100 - ticks / 2 + "%", (sr.getScaledWidth() / 2f) - textWidth / 2, sr.getScaledHeight() - 89, 0xb0ffffff);
    }
}
