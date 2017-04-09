#version 400 core

in vec2 position;
in vec2 tex_coords;

out vec2 otex_coords;

uniform mat4 transformation_matrix;

uniform int rows;
uniform vec2 offset;

void main(void){
	vec4 world_position = transformation_matrix * vec4(position, 1.0, 1.0);
	gl_Position = world_position;
	otex_coords = (tex_coords / rows) + offset;
}
