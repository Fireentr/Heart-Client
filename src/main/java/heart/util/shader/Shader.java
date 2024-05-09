package heart.util.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static net.minecraft.src.Config.readInputStream;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private InputStream vertexLocation;
    private InputStream fragmentLocation;
    public int program;

    public Shader(ResourceLocation vertexLocation, ResourceLocation fragmentLocation) throws IOException {
        try {
            this.vertexLocation = (Minecraft.getMinecraft().getResourceManager().getResource(vertexLocation).getInputStream());
            this.fragmentLocation = (Minecraft.getMinecraft().getResourceManager().getResource(fragmentLocation).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        program = create(readInputStream(this.vertexLocation), readInputStream(this.fragmentLocation));
    }

    public int create(String vertexShader, String fragmentShader){
        int program = glCreateProgram();

        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertID, vertexShader);
        glShaderSource(fragID, fragmentShader);

        glCompileShader(vertID);

        if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("failed to compile vertex");
            System.err.println(glGetShaderInfoLog(vertID, 500));
        }

        glCompileShader(fragID);

        if(glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("failed to compile vertex");
            System.err.println(glGetShaderInfoLog(fragID, 500));
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);

        glLinkProgram(program);
        glValidateProgram(program);

        return program;

    }

}