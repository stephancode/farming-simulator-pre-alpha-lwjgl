package com.stephancode.main.level.terrain;

import com.stephancode.main.gfx.Model;
import com.stephancode.main.utils.Components;
import com.stephancode.math.Vector3f;

public class Water {

	private Vector3f position 			= new Vector3f(0, 0, 0);
	private float scale 				= 20.0f;
	private int VERTEX_COUNT 			= 10;
	private int SIZE 					= 5;
	private Model model 				= Components.getLoader().generateStaticWater(VERTEX_COUNT, SIZE);
	private float damping				= 10;
	private float reflectivity			= 0.5f;

	public Water() {
		position = new Vector3f(2, -15, 2);
	}
	
	public float getDamping() {return damping;}
	public void setDamping(float damping) {this.damping = damping;}
	public float getReflectivity() {return reflectivity;}
	public void setReflectivity(float reflectivity) {this.reflectivity = reflectivity;}
	public float getScale() {return scale;}
	public Vector3f getPosition() {return position;}
	public Model getModel() {return model;}
	
}
