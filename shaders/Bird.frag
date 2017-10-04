#version 330 core

layout (location = 0) out vec4 color;

// Comes IN from vertex shader
in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;

void main() {
    //color = vec4(0.4, 0.3, 0.8, 1.0);
    color = texture(tex, fs_in.tc);
}


