package com.stephancode.main.gfx.shaders;

public class FontShader extends Shader{


	public FontShader(String vertex_shader_path, String fragment_shader_path) {
		super(vertex_shader_path, fragment_shader_path);
	}

	public void init() {
		attrib_location(0, "position");
		attrib_location(1, "tex_coords");
	}

	public void get_info() {
		transformation_matrix_location = get_uniform_location("transformation_matrix");
		rows_location = get_uniform_location("rows"); 
		offs_location = get_uniform_location("offset"); 
		font_color_location = get_uniform_location("color");
	}
}