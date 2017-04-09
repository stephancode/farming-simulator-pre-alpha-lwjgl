#version 400 core

in vec3 position;
in vec2 tex_coords;
in vec3 normals;

out vec2 otex_coords;
out vec3 onormals;
out vec3 oto_light;
out vec3 ocamera;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec3 light_position;

uniform float time;

void main(void){
	vec4 world_position = transformation_matrix * vec4(position, 1.0);

	bool xd = int(world_position.x) % 2 == 0;
	bool zd = int(world_position.z) % 2 == 0;
	if(xd)world_position.y = world_position.y - (sin(time * world_position.x * 0.02f)) * 1.7f;
	if(zd)world_position.y = world_position.y - (cos(time * world_position.z * 0.02f)) * 1.7f;

	gl_Position =  projection_matrix * view_matrix * world_position;
	otex_coords = tex_coords;

	onormals = (transformation_matrix * vec4(normals, 0.0)).xyz;
	oto_light = light_position - world_position.xyz;
	ocamera = (inverse(view_matrix) * vec4(0, 0, 0, 1.0)).xyz - world_position.xyz;
}
