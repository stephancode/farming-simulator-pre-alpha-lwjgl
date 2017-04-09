package com.stephancode.main.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stephancode.main.gfx.Model;
import com.stephancode.main.gfx.shaders.Shader;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class LoadOBJ {

	public static Model create_model(String obj_name, LoadVAO loader, String tex_path){
		FileReader fr = null;
		try {
			fr = new FileReader(new File(obj_name));
		} catch (FileNotFoundException e) {
			System.out.println("Could not load " + obj_name);
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] vertices_array = null;
		float[] textures_array = null;
		float[] normals_array = null;
		int[] indices_array = null;
		
		try {
			while(true){
				line = reader.readLine();
				String[] current_line = line.split(" ");
				if(line.startsWith("v ")){
					Vector3f vertex = new Vector3f(Float.parseFloat(current_line[1]), 
							Float.parseFloat(current_line[2]), Float.parseFloat(current_line[3]));
					vertices.add(vertex);
				} else if(line.startsWith("vt ")){
					Vector2f texture = new Vector2f(Float.parseFloat(current_line[1]), 
							Float.parseFloat(current_line[2]));
					textures.add(texture);
				} else if(line.startsWith("vn ")){
					Vector3f normal = new Vector3f(Float.parseFloat(current_line[1]), 
							Float.parseFloat(current_line[2]), Float.parseFloat(current_line[3]));
					normals.add(normal);
				} else if(line.startsWith("f ")){
					//vertices_array = new float[vertices.size()];
					textures_array = new float[vertices.size() * 2];
					normals_array = new float[vertices.size() * 3];
					break;
				}
				
			}
			
			while( line != null){
				if(!line.startsWith("f ")){
					line = reader.readLine();
					continue;
				}
				
				String[] current_line = line.split(" ");
				String[] vertex1 = current_line[1].split("/");
				String[] vertex2 = current_line[2].split("/");
				String[] vertex3 = current_line[3].split("/");
				
				proccess_vertex(vertex1, indices, textures, normals, textures_array,
						normals_array);

				proccess_vertex(vertex2, indices, textures, normals, textures_array,
						normals_array);
				
				proccess_vertex(vertex3, indices, textures, normals, textures_array,
						normals_array);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {e.printStackTrace();}
		
		vertices_array = new float[vertices.size() * 3];
		indices_array = new int[indices.size()];
		
		int vertexp = 0;
		for(Vector3f vertex : vertices){
			vertices_array[vertexp++] = vertex.x;
			vertices_array[vertexp++] = vertex.y;
			vertices_array[vertexp++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++){
			indices_array[i] = indices.get(i);
		}
		
		return loader.create_model(vertices_array, textures_array, normals_array, indices_array, tex_path);
	}
	
	private static void proccess_vertex(String[] vertex, List<Integer> indices,
			List<Vector2f> textures, List<Vector3f> normals, 
			float[] textures_array, float[] normals_array){
	
		int current_vertex = Integer.parseInt(vertex[0]) - 1;
		Vector2f current_texture = textures.get(Integer.parseInt(vertex[1]) - 1);
		Vector3f current_normal = normals.get(Integer.parseInt(vertex[2]) - 1);
		
		indices.add(current_vertex);
		
		textures_array[current_vertex * 2] = current_texture.x;
		textures_array[current_vertex * 2 + 1] = 1 - current_texture.y;
	
		normals_array[current_vertex * 3] = current_normal.x;
		normals_array[current_vertex * 3 + 1] = current_normal.y;
		normals_array[current_vertex * 3 + 2] = current_normal.z;
	}
	
}
