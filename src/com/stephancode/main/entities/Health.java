package com.stephancode.main.entities;

import com.stephancode.main.utils.Components;
import com.stephancode.math.Vector3f;

public class Health extends Entity{

	private Vector3f my_rotation = new Vector3f(0, 0, 0);
	
	public Health() {
		super(Components.getArt().health);
		position.y = -7.5f;
		scale = 3;
	}

	public void update(){
		rotation.y = -Components.getCamera().yaw;
	}
	
}
