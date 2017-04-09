#version 400 core

in vec2 otex_coords;
in vec3 onormals;
in vec3 oto_light;
in vec3 ocamera;

uniform sampler2D tex;

uniform vec3 light_color;

uniform float damping;
uniform float reflecitvity;

void main(void){

	vec3 norm_normals = normalize(onormals);
	vec3 norm_light = normalize(oto_light);

	float prod = dot(norm_normals, norm_light);
	float light_value = max(prod, 0.0);

	vec3 norm_camera = normalize(ocamera);
	vec3 light_direction = -norm_light;
	vec3 reflextion = reflect(light_direction, norm_normals);

	float specular = dot(reflextion, norm_camera);
	specular = max(specular, 0.0);
	float damper = pow(specular, damping);

	vec3 final_spec = damper * reflecitvity * light_color;

	vec3 col = ((light_value * light_color));

	vec4 color = vec4(col, 1) * texture(tex, otex_coords) + vec4(final_spec, 1.0);
	color.a = 0.4f;
	gl_FragColor = color;
}
