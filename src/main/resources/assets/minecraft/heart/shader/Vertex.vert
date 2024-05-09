#version 330 core

uniform float time;

uniform vec3 color1 = vec3(0, 0, 0);
uniform vec3 color2 = vec3(1, 1, 1);

layout(location = 0) in vec3 position;

void main()
{
    gl_Position = vec4(position, 1.0);
}