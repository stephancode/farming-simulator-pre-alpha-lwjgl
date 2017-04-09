#version 400 core

in vec3 position;
in vec2 tex_coords;
in vec3 normals;

out vec2 otex_coords;
out vec3 onormals;
out vec3 oto_light;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec3 light_position;

uniform vec3 data;

void main(void){
	vec4 world_position = transformation_matrix * vec4(position, 1.0);

	//if(data.x == world_position.x && data.z  == world_position.z) world_position.y = data.y;

	bool xd = int(world_position.x) % 2 == 0;
	bool zd = int(world_position.z) % 2 == 0;
	if(xd)world_position.y = world_position.y - (sin(world_position.z * 0.5f) / world_position.y) * 10.0f;
	if(zd)world_position.y = world_position.y - (cos(world_position.x * 0.5f) / world_position.y) * 10.0f;

	if(world_position.x < 60 && world_position.z < 60
	  && world_position.x >= 15 && world_position.z >= 15) world_position.y = -18;

	gl_Position =  projection_matrix * view_matrix * world_position;
	otex_coords = tex_coords;

	onormals = (transformation_matrix * vec4(normals, 0.0)).xyz;
	oto_light = light_position - world_position.xyz;
}
