package heart.util.shader.impl;

import heart.util.CFontRenderer;
import heart.util.shader.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class BackgroundShader extends Shader {
    public BackgroundShader() throws IOException {
        super(new ResourceLocation("heart/shader/Vertex.vert"), new ResourceLocation("heart/shader/Background.frag"));
    }


    long startTime = System.currentTimeMillis();

    public void drawRectWithShader(float x, float y, float width, float height){

        GL20.glUseProgram(program);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float scalefactor = (float) sr.getScaleFactor();

        GL20.glUniform2f(GL20.glGetUniformLocation(program, "resolution"), width* scalefactor, height*scalefactor);
        GL20.glUniform2f(GL20.glGetUniformLocation(program, "position"), x * scalefactor, sr.getScaledHeight()*scalefactor - ((y + height) * scalefactor));
        GL20.glUniform2f(GL20.glGetUniformLocation(program, "scale"), width, height);
        GL20.glUniform1f(GL20.glGetUniformLocation(program, "time"), (System.currentTimeMillis() - startTime)/1000f);

        GL11.glBegin(GL_QUADS);
        GL11.glVertex2f(((x - 30)/sr.getScaledWidth()) * 2 - 1, 1 + ((y - 30)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x - 30)/sr.getScaledWidth()) * 2 - 1, 1 + ((y+height + 30)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x + width + 30)/sr.getScaledWidth()) * 2 - 1, 1 + ((y+height + 30)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x + width + 30)/sr.getScaledWidth()) * 2 - 1, 1 + ((y - 30)/sr.getScaledHeight()) * -2);
        GL11.glEnd();

        GL20.glUseProgram(0);
    }


    void drawSquare(float x1, float y1, float width, float height, int extra){

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glBegin(GL_QUADS);

        GL11.glVertex2f(((x1 - extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1 - extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 - extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1+height + extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 + width + extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1+height + extra)/sr.getScaledHeight()) * -2);
        GL11.glVertex2f(((x1 + width + extra)/sr.getScaledWidth()) * 2 - 1, 1 + ((y1 - extra)/sr.getScaledHeight()) * -2);

        GL11.glEnd();
    }

}
