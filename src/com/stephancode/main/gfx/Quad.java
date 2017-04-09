package com.stephancode.main.gfx;

import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Quad {

	private Vector2f position;

	private float x_scale = 1.0f;
	private float y_scale = 1.0f;
	private float z_scale = 1.0f;
	private Model model;
	
	public Quad(Vector2f position, Model model) {
		this.position = position;
		this.model = model;
	}
	
	public void setX_scale(float x_scale) {this.x_scale = x_scale;}
	public void setY_scale(float y_scale) {this.y_scale = y_scale;}
	public void setZ_scale(float z_scale) {this.z_scale = z_scale;}
	public Vector2f getPosition() {return position;}
	public float getX_scale() {return x_scale;}
	public float getY_scale() {return y_scale;}
	public float getZ_scale() {return z_scale;}
	public void setPosition(Vector2f position) {this.position = position;}
	public void setModel(Model model) {this.model = model;}
	public Model getModel() {return model;}
	
	

}
