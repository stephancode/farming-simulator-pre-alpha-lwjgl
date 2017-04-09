package com.stephancode.main.gfx;

import com.stephancode.math.Vector3f;

public class Light {
	
	private Vector3f position;
	private Vector3f color;
	
	public Light(Vector3f position, Vector3f color){
		this.position = position;
		this.color = color;
	}
	
	public Vector3f getPosition(){return position;}
	public Vector3f getColor(){return color;}
	public void setColor(Vector3f color){this.color = color;}

	public void setPosition(Vector3f vector) {
		this.position.x += vector.x;
		this.position.y += vector.y;
		this.position.z += vector.z;
	}

	public void setPositionFixed(Vector3f vector) {
		this.position.x = vector.x;
		this.position.y = vector.y;
		this.position.z = vector.z;
	}
}
