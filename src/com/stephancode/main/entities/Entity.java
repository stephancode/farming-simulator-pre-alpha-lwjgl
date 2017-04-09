package com.stephancode.main.entities;

import com.stephancode.main.gfx.Model;
import com.stephancode.main.gfx.Quad;
import com.stephancode.math.Vector3f;

public class Entity {

	protected Model model 			= null;
	protected Quad quad 			= null;
	protected float scale = 1;
	protected Vector3f position 	= new Vector3f(0.5f, 0, 0);
	protected Vector3f rotation 	= new Vector3f(0, 180, 0);
	public boolean render_bigger 	= false;
	public float x0, x1, z0, z1;
	
	public Entity(Model model){this.model = model;}
	public Entity(Quad model){this.quad = model;}
	
	public void setRotation(float rx, float ry, float rz){
		rotation.x += rx;
		rotation.y += ry;
		rotation.z += rz;
	}
	
	public void setPosition(float xa, float ya, float za){
		position.x += xa;
		position.y += ya;
		position.z += za;
	}
	
	public void setFixedRotation(float rx, float ry, int rz) {
		if(rx != 0) rotation.x = rx;
		if(ry != 0) rotation.y = ry;
		if(rz != 0) rotation.z = rz;
	}
	
	public void update(){}
	
	public void setScale(float scale){ this.scale = scale; }
	public float getScale(){ return scale; }
	public Vector3f getPosition() { return position; }
	public Vector3f getRotation() { return rotation;}
	public Model getModel(){ return model; }
	public void setPosition(Vector3f point) {this.position = point;}
	public Quad getQuad() {return quad;}
}
