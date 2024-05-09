package heart.util.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtil {
    public static int CreateVertexShader(String vertexShader){
        int program = glCreateProgram();

        int vertID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertID, vertexShader);

        glCompileShader(vertID);

        if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("failed to compile vertex");
            System.err.println(glGetShaderInfoLog(vertID, 500));
        }

        glAttachShader(program, vertID);

        glLinkProgram(program);
        glValidateProgram(program);

        return program;
    }

}
