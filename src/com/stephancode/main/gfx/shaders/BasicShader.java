package com.stephancode.main.gfx.shaders;

public class BasicShader extends Shader{

	
	public BasicShader(String vertex_shader_path, String fragment_shader_path) {
		super(vertex_shader_path, fragment_shader_path);
	}
	
	
	public void init() {
		attrib_location(0, "position");
		attrib_location(1, "tex_coords");
		attrib_location(2, "normals");
	}

	public void get_info() {
		transformation_matrix_location = get_uniform_location("transformation_matrix");
		projection_matrix_location = get_uniform_location("projection_matrix");
		view_matrix_location = get_uniform_location("view_matrix");
		light_position_location = get_uniform_location("light_position");
		light_color_location = get_uniform_location("light_color");
	}
}
