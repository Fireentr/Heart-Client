#version 400 core

uniform vec2 resolution;
uniform vec2 position;
uniform vec4 color;
uniform float radius;
uniform float width;
uniform vec4 outlineColor;

float roundedBoxSDF(vec2 CenterPosition, vec2 Size, float Radius) {
    return length(max(abs(CenterPosition)-Size+Radius,0.0))-Radius;
}
void main() {
    vec2 size = vec2(resolution.x, resolution.y);
    vec2 location = vec2(position.x, position.y);
    float edgeSoftness  = 1.0f;
    float distance 		= roundedBoxSDF(gl_FragCoord.xy - location - (size/2.0f), size / 2.0f, radius);
    float smoothedAlpha =  1.0f-smoothstep(0.0f, edgeSoftness * 2.0f, distance);

    float outlineRadius = radius + width/2;
    float outDistance = roundedBoxSDF(gl_FragCoord.xy - location - (size/2.0f), (size+(width*2)) / 2.0f, outlineRadius);

    float outSmoothedAlpha = 1.0f-smoothstep(0.0f, edgeSoftness * 2.0f,outDistance);

    float finalAlpha = color.a*smoothedAlpha + outlineColor.a * (outSmoothedAlpha - smoothedAlpha);
    gl_FragColor = vec4(mix(outlineColor, color, smoothedAlpha).xyz, finalAlpha);

}