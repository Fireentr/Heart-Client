package heart.util.shader.impl;

import heart.util.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class RoundedRectShader extends Shader {
    public RoundedRectShader() throws IOException {
        super(new ResourceLocation("heart/shader/Vertex.vert"), new ResourceLocation("heart/shader/RoundedRect.frag"));
    }

    public void drawRectWithShader(float x, float y, float width, float height, int outlineWidth, int rounded, Color color, Color outlineColor){
        GL20.glUseProgram(program);

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float scalefactor = sr.getScaleFactor();

        GL20.glUniform2f(GL20.glGetUniformLocation(program, "resolution"), width*scalefactor, height*scalefactor);
        GL20.glUniform2f(GL20.glGetUniformLocation(program, "position"), x * scalefactor, sr.getScaledHeight()*scalefactor - ((y + height) * scalefactor));
        GL20.glUniform2f(GL20.glGetUniformLocation(program, "scale"), width, height);
        GL20.glUniform4f(GL20.glGetUniformLocation(program, "color"), color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        GL20.glUniform4f(GL20.glGetUniformLocation(program, "outlineColor"), outlineColor.getRed()/255f, outlineColor.getGreen()/255f, outlineColor.getBlue()/255f, outlineColor.getAlpha()/255f);
        GL20.glUniform1f(GL20.glGetUniformLocation(program, "width"), outlineWidth);
        GL20.glUniform1f(GL20.glGetUniformLocation(program, "radius"), rounded);

        drawSquare(x, y, width, height, 1 + outlineWidth);

        GL20.glUseProgram(0);
    }


    void drawSquare(float x1, float y1, float width, float height, int extra){

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(((x1 - extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1 - extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 - extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1+height + extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 + width + extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1+height + extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 + width + extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1 - extra)/sr.getScaledHeight()) * -2);

        GL11.glEnd();
    }

}
