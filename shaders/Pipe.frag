#version 330 core

layout (location = 0) out vec4 color;

// Comes IN from vertex shader
in DATA {
    vec2 tc;
    vec3 position;
} fs_in;

uniform vec2 bird;
uniform sampler2D tex;
uniform int top;

void main() {
    color = texture(tex, top == 1 ? vec2(fs_in.tc.x, 1.0 - fs_in.tc.y) : fs_in.tc);
    if (color.w < 1.0)
        discard;

    color *= 2.0 / (length(bird - fs_in.position.xy) + 1.5) + 0.6;
    color.w = 1;
}


