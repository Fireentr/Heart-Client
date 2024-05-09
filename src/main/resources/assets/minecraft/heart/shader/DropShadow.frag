#version 400 core

uniform vec2 resolution;
uniform vec2 position;
uniform vec4 color;
uniform float radius;
uniform float width;
uniform bool fill;

float roundedBoxSDF(vec2 CenterPosition, vec2 Size, float Radius) {
    return length(max(abs(CenterPosition)-Size+Radius,0.0))-Radius;
}

void main() {
    vec2 size = vec2(resolution.x, resolution.y);
    vec2 location = vec2(position.x, position.y);
    float edgeSoftness  = 1.0f;
    float distToCube = roundedBoxSDF(gl_FragCoord.xy - location - (size/2.0f), size / 2.0f, radius);
    vec4 Color;
    float smoothedAlpha =  1.0f-smoothstep(0.0f, edgeSoftness * 2.0f, distToCube);

    float x = (1.0 - (distToCube/width) - (fill ? 0.0 : smoothedAlpha));

    Color = vec4(color.xyz, (x * x * x)*(color.a - (fill ? 0.0 : smoothedAlpha)));

    gl_FragColor = Color;
}