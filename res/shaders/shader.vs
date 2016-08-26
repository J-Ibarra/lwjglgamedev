#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 inColour;

out vec3 exColour;

uniform mat4 mProjection;
uniform mat4 mView;
uniform mat4 mModel;

void main() {

    gl_Position = mProjection * mView *  mModel * vec4(position, 1.0);
    exColour = inColour;
}