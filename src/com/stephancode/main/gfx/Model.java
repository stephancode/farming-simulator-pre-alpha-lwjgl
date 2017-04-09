package com.stephancode.main.gfx;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.shaders.Shader;

public class Model {
	
	private int vao_id;
	private int tex_id;
	private int[] indices;
	private float[] verts;
	private int vert_count;
	
	private int rows = 1;
	private int offset = 0;
	
	public Model(int vao_id, int tex_id, float[] verts, int[] indices, 
		int vert_count){
		this.vao_id = vao_id;
		this.tex_id = tex_id;
		this.verts = verts;
		this.indices = indices;
		this.vert_count = vert_count;
	}
	
	public void setVerts(float[] verts){this.verts = verts;}
	public int getRows() {return rows;}
	public int getOffset(){return offset;}
	public void setRows(int rows) {this.rows = rows;}
	public int getTexId() {return tex_id;}
	public int getVaoId() {return vao_id;}
	public int[] getIndices() {	return indices;}
	public float[] getVerts() {	return verts;}
	public int getVert_count() {return vert_count;}
	public void setOffset(int index) {offset = index;}
	
	public float getXOffset(){
		int col = getOffset() % getRows();
		return (float)col / (float)getRows();
	}

	public float getYOffset(){
		int row = getOffset() / getRows();
		return (float)row / (float)getRows();
	}
	


}
