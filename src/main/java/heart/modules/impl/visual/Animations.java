package heart.modules.impl.visual;

import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.modules.settings.impl.EnumSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

enum AnimationOptions {
    NONE,
    OLD,
    EXHI,
    EXHINEW,
    SIGMA,
    CHILL,
    SWING
}

public class Animations extends Module {

    @Override
    public String getSuffix() {
        return animationOptions.getDisplayName();
    }
    public Animations() {
        super("Animations", "Animates sword blocking", Category.VISUAL);
        initmodule();
    }

    EnumSetting<AnimationOptions> animationOptions = new EnumSetting<>("Animation Style", "Select the animation style.", AnimationOptions.values());
    DoubleSetting speedSetting = new DoubleSetting("Speed", "Sets the animation speed", 0.1d, 2d, 1d, 5);
    DoubleSetting scaleSetting = new DoubleSetting("Scale", "Sets the item scale", 0.1d, 1.5d, 1d, 10);
    DoubleSetting xOffsetSetting = new DoubleSetting("X Offset", "Moves the item on the X axis", 0.1d, 1.5d, 1d, 10);
    DoubleSetting yOffsetSetting = new DoubleSetting("Y Offset", "Moves the item on the Y axis", 0.1d, 1.5d, 1d, 10);
    public BoolSetting fluxHit = new BoolSetting("Flux Swing", "Alternative swinging animation.", false);

    public void Animate(ItemRenderer itemRenderer, float fv, float f1v, float funnyvar1v){
        float f = (float) (speedSetting.getValue() * fv);
        float f1 = (float) (speedSetting.getValue() * f1v);
        float funnyvar1 = (float) (speedSetting.getValue() * funnyvar1v);

        GlStateManager.translate(xOffsetSetting.getValue() - 1, yOffsetSetting.getValue() - 1, scaleSetting.getValue() - 1);

        switch (animationOptions.getValue()) {
            default:
                itemRenderer.transformFirstPersonItem(f, 0.0F);
                itemRenderer.doBlockTransformations();
                break;
            case OLD:
                itemRenderer.transformFirstPersonItem(0.2F, f1);
                itemRenderer.doBlockTransformations();
                GlStateManager.translate(-0.5F, 0.2F, 0.0F);
                break;
            case EXHI:
                itemRenderer.transformFirstPersonItem(f / 2.0F, 0.0F);
                GL11.glRotatef(-funnyvar1 * 40.0F / 2.0F, funnyvar1 / 2.0F, -0.0F, 9.0F);
                GL11.glRotatef(-funnyvar1 * 30.0F, 1.0F, funnyvar1 / 2.0F, -0.0F);
                itemRenderer.doBlockTransformations();
                break;
            case EXHINEW:
                itemRenderer.transformFirstPersonItem(f / 2.0F, 0.0F);
                GL11.glRotatef(-funnyvar1 * 70.0F / 2.0F, funnyvar1 / 2.0F, -0.0F, 9.0F);
                GL11.glRotatef(-funnyvar1 * 50.0F, 1.0F, funnyvar1 / 2.0F, -0.0F);
                itemRenderer.doBlockTransformations();
                break;
            case SIGMA:
                itemRenderer.transformFirstPersonItem(f / 2.0F, 0.0F);
                itemRenderer.doBlockTransformations();
                float var9 = MathHelper.sin((MathHelper.sqrt_float(f1) * 3.0515927f));
                GlStateManager.rotate(((-var9) * 50.0f), -20.0f, 0.1f, 65.0f);
                GlStateManager.rotate(((-var9) * 70.0f), 1.0f, 0.1f, 0.0f);
                break;
            case CHILL:
                itemRenderer.transformFirstPersonItem(f / 2.0F, 0.0F);
                itemRenderer.doBlockTransformations();
                float var10 = MathHelper.sin((MathHelper.sqrt_float(f1) * 3.0515927f));
                GlStateManager.rotate(((-var10) * 30.0f), -20.0f, 0.1f, 65.0f);
                GlStateManager.rotate(((-var10) * 50.0f), 1.0f, 0.1f, 0.0f);
                break;
            case SWING:
                itemRenderer.transformFirstPersonItem(0.2F, f1);
                itemRenderer.doBlockTransformations();
                GlStateManager.translate(-0.5F, 0F, 0.0F);
                break;

        }
    }
}