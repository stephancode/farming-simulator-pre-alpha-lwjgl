package com.stephancode.main.gfx.shaders;

public class WaterShader extends Shader{
	
	private int damping_location;
	private int reflectivity_location;
	
	public WaterShader(String vertex_shader_path, String fragment_shader_path) {
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
		time_location = get_uniform_location("time");
		damping_location = get_uniform_location("damping");
		reflectivity_location = get_uniform_location("reflecitvity");
	}
	
	public void inject_specular_attr(float damping, float reflectivity){
		inject_float(reflectivity_location, reflectivity);
		inject_float(damping_location, damping);
	}
	
}
