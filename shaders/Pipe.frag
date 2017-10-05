#version 330 core

layout (location = 0) out vec4 color;

// Comes IN from vertex shader
in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int top;

void main() {
    color = texture(tex, top == 1 ? vec2(fs_in.tc.x, 1.0 - fs_in.tc.y) : fs_in.tc);
    if (color.w < 1.0)
        discard;
}


