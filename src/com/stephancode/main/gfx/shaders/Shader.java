package com.stephancode.main.gfx.shaders;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.stephancode.main.gfx.Light;
import com.stephancode.math.Matrix4f;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public abstract class Shader {
	
	protected StringBuilder vertex_shader_source 	= new StringBuilder();
	protected StringBuilder fragment_shader_source	= new StringBuilder();
	protected int transformation_matrix_location; 
	protected int view_matrix_location; 
	protected int projection_matrix_location; 
	protected int light_position_location;
	protected int light_color_location;
	protected int font_color_location;
	protected int position_location;
	protected int data_location;
	protected int time_location;
	protected int rows_location;
	protected int offs_location;
	
	protected int vertex_shader;
	protected int fragment_shader;
	protected int program;
	
	public Shader(String vertex_shader_path, String fragment_shader_path){
		
		make_source(vertex_shader_path, fragment_shader_path);
		
		compile_vertex_shader();
		compile_fragment_shader();
		program = glCreateProgram();

		glAttachShader(program, vertex_shader);
		glAttachShader(program, fragment_shader);

		init();
	
		glLinkProgram(program);
		glValidateProgram(program);
		
		get_info();
	}
	
	public abstract void init();
	public abstract void get_info();
	
	public void inject_float_buffer(int location, float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glUniform1fv(location, buffer);
	}
	
	
	public void inject_light(Light light){
		inject_vector3f(light_position_location, light.getPosition());
		inject_vector3f(light_color_location, light.getColor());
	}
	
	protected void inject_matrix(int location, Matrix4f matrix){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.store(buffer);
		buffer.flip();
		glUniformMatrix4fv(location, false, buffer);
	}

	protected void compile_vertex_shader(){
		vertex_shader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex_shader, vertex_shader_source);
		glCompileShader(vertex_shader);
		if(glGetShaderi(GL_COMPILE_STATUS, vertex_shader) == GL_FALSE){
			System.out.println(glGetShaderInfoLog(vertex_shader));
		}
	}
	
	protected void compile_fragment_shader(){
		fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment_shader, fragment_shader_source);
		glCompileShader(fragment_shader);
		if(glGetShaderi(GL_COMPILE_STATUS, fragment_shader) == GL_FALSE){
			System.out.println(glGetShaderInfoLog(fragment_shader));
		}
	}
	
	protected void make_source(String vertex_shader_path, String fragment_shader_path){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(vertex_shader_path));
			String line = "";
			while((line = reader.readLine()) != null){
				vertex_shader_source.append(line).append("\n");
			}
			reader.close();
			BufferedReader reader2 = new BufferedReader(new FileReader(fragment_shader_path));
			String line2 = "";
			while((line2 = reader2.readLine()) != null){
				fragment_shader_source.append(line2).append("\n");
			}
			reader2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}	
	
	public void inject_time(float time){inject_float(time_location, time);}
	public void inject_positions(float[] data){inject_float_buffer(position_location, data);}
	public void inject_rows(int rows){inject_int(rows_location, rows);}
	public void inject_offset(Vector2f offset){inject_vector2f(offs_location, offset);}
	public void inject_color(Vector3f color) {inject_vector3f(font_color_location, color);}

	public void inject_transformation_matrix(Matrix4f matrix){inject_matrix(transformation_matrix_location, matrix);}
	public void inject_projection_matrix(Matrix4f matrix){inject_matrix(projection_matrix_location, matrix);}
	public void inject_view_matrix(Matrix4f matrix){inject_matrix(view_matrix_location, matrix);}

	protected void inject_vector3f(int location, Vector3f data){glUniform3f(location, data.x, data.y, data.z);}
	protected void inject_vector2f(int location, Vector2f data){glUniform2f(location, data.x, data.y);}
	protected void inject_float(int location, float data){glUniform1f(location, data);}
	protected void inject_int(int location, int data){glUniform1i(location, data);}
	protected int get_uniform_location(String name){return glGetUniformLocation(program, name);}
	protected void attrib_location(int index, String name){glBindAttribLocation(program, index, name);}

	public void start(){glUseProgram(program);}
	public void stop(){glUseProgram(0);}

}
