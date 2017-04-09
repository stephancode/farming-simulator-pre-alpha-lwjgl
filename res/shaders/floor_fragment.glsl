#version 400 core

in vec2 otex_coords;
in vec3 onormals;
in vec3 oto_light;

uniform sampler2D tex;

uniform vec3 light_color;

void main(void){

	vec3 norm_normals = normalize(onormals);
	vec3 norm_light = normalize(oto_light);

	float prod = dot(norm_normals, norm_light);
	float light_value = max(prod, 0.1);
	vec3 col = ((light_value * light_color));

	gl_FragColor = (vec4(col, 1.0) * texture(tex, otex_coords));
}
