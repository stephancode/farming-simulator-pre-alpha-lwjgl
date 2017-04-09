package com.stephancode.main.gfx.shaders;

public class Shaders {
	
	public BasicShader basic_shader 		= new BasicShader("res/shaders/basic_vertex.glsl", "res/shaders/basic_fragment.glsl");
	public BasicShader2d basic_shader2d 	= new BasicShader2d("res/shaders/basic_vertex2d.glsl", "res/shaders/basic_fragment2d.glsl");
	public FloorShader floor_shader 		= new FloorShader("res/shaders/floor_vertex.glsl", "res/shaders/floor_fragment.glsl");
	public FontShader font_shader 			= new FontShader("res/shaders/font_vertex.glsl", "res/shaders/font_fragment.glsl");
	public WaterShader water_shader 		= new WaterShader("res/shaders/water_vertex.glsl", "res/shaders/water_fragment.glsl");
	public RotationShader rotation_shader	= new RotationShader("res/shaders/rotation_vertex.glsl", "res/shaders/rotation_fragment.glsl");
}
