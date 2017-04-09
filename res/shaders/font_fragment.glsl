#version 400 core

in vec2 otex_coords;

uniform sampler2D tex;
uniform vec3 color;

void main(void){

	if(!(color.x == 0 && color.y == 0 && color.z == 0))
		gl_FragColor = texture(tex, otex_coords) * vec4(color, 1.0);
	else
		gl_FragColor = texture(tex, otex_coords);

}
