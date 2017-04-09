package com.stephancode.main.gfx.shaders;

public class RotationShader extends Shader {

	public RotationShader(String vertex_shader_path, String fragment_shader_path) {
		super(vertex_shader_path, fragment_shader_path);
	}

	public void init(){
		attrib_location(0, "position");
		attrib_location(1, "tex_coords");
	}
	
	public void get_info() {
		transformation_matrix_location = get_uniform_location("transformation_matrix");
		projection_matrix_location = get_uniform_location("projection_matrix");
		view_matrix_location = get_uniform_location("view_matrix");
	}

}
