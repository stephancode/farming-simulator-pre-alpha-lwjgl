#version 400 core

in vec3 position;
in vec2 tex_coords;

out vec2 otex_coords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;

void main(void){
	vec4 world_position = transformation_matrix * vec4(position, 1.0);
	gl_Position = projection_matrix * world_position;
}
