package com.stephancode.main.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.stephancode.main.gfx.Model;
import com.stephancode.main.gfx.Texture;
import com.stephancode.main.gfx.shaders.Shader;

public class LoadVAO {
	
	private static List<Integer> vao_ids 		= new ArrayList<Integer>();
	private static List<Integer> texture_ids 	= new ArrayList<Integer>();
	private static List<Integer> vbo_ids 		= new ArrayList<Integer>();
	
	public Model create_model(float[] verts, float[] tex_coords, float[] normals, int[] indices, 
	String path){
		Texture texture = new Texture(path);
		int vao_id = glGenVertexArrays();
		vao_ids.add(vao_id);
		
		int tex_id = texture.getTextureID();

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		texture_ids.add(tex_id);

		glBindVertexArray(vao_id);
		if(indices!= null) bind_indices(indices);
		
		if(verts != null) store_buffer(0, 3, verts);
		if(tex_coords != null) store_buffer(1, 2, tex_coords);
		if(normals != null) store_buffer(2, 3, normals);
		
		glBindVertexArray(0);
		if(indices != null)	return new Model(vao_id, tex_id, verts, indices, indices.length);
		else	return new Model(vao_id, tex_id, verts, indices, verts.length / 3);
	}
	
	public Model create_quad(float[] verts, float[] tex_coords, int[] indices, String path){
		Texture texture = new Texture(path);
		int vao_id = glGenVertexArrays();
		vao_ids.add(vao_id);
		
		int tex_id = texture.getTextureID();

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		texture_ids.add(tex_id);

		glBindVertexArray(vao_id);
		bind_indices(indices);
		
		store_buffer(0, 2, verts);
		store_buffer(1, 2, tex_coords);
		
		glBindVertexArray(0);
		return new Model(vao_id, tex_id, verts, indices, indices.length);
	}
	
	public Model create_terrain_model(float[] verts, float[] tex_coords, float[] normals, int[] indices, String path){
		Texture texture = new Texture(path);
		int vao_id = glGenVertexArrays();
		vao_ids.add(vao_id);
		
		int tex_id = texture.getTextureID();

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		texture_ids.add(tex_id);
		
		glBindVertexArray(vao_id);
		bind_indices(indices);
		
		
		store_buffer(0, 3, verts);
		store_buffer(1, 2, tex_coords);
		store_buffer(2, 3, normals);
		
		glBindVertexArray(0);
		return new Model(vao_id, tex_id, verts, indices, indices.length);
	}
	
	public void store_buffer(int index, int dimensions, float[] data){
		int vbo_id = glGenBuffers();
		texture_ids.add(vbo_id);

		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, dimensions, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public Model generateTerrainTile(int VERTEX_COUNT, float SIZE){
		int count = VERTEX_COUNT * VERTEX_COUNT; 
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		
		for(int i = 0; i < VERTEX_COUNT; i++){
			for(int j = 0; j< VERTEX_COUNT; j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;

				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz = 0; gz < VERTEX_COUNT - 1; gz++){
			for(int gx = 0; gx < VERTEX_COUNT - 1; gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return Components.getLoader().create_model(vertices, textureCoords, normals, indices, "res/tex/ground.png");
	}
	
	public Model generateStaticWater(int VERTEX_COUNT, float SIZE){
		int count = VERTEX_COUNT * VERTEX_COUNT; 
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i = 0; i < VERTEX_COUNT; i++){
			for(int j = 0; j< VERTEX_COUNT; j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;

				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz = 0; gz < VERTEX_COUNT - 1; gz++){
			for(int gx = 0; gx < VERTEX_COUNT - 1; gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return Components.getLoader().create_model(vertices, textureCoords, normals, indices, "res/tex/water.png");
	}
	
	private void bind_indices(int[] data){
		int vbo_id = glGenBuffers();

		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	public void clear(){
		for(int vbo : vbo_ids) 		glDeleteBuffers(vbo); 
		for(int tex : texture_ids) 	glDeleteBuffers(tex); 
		for(int vao : vao_ids) 		glDeleteVertexArrays(vao); 
	}
}
