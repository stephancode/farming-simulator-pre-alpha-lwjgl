#version 400 core

in vec2 otex_coords;

uniform sampler2D tex;

void main(void){

	gl_FragColor = texture(tex, otex_coords);
}
