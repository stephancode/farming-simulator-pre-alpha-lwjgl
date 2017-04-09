package com.stephancode.main.gfx;

import com.stephancode.main.entities.Player;
import com.stephancode.math.Vector3f;

public class Camera {
	
	public Vector3f position = new Vector3f(0, 0, 0);
	public float pitch = 0;
	public float yaw = 0;;
	public float roll = 0;;
	
	
	public Camera(){}
	
	public void update(Player player){
		position.x = player.getPosition().x;
		position.y = player.getPosition().y;
		position.z = player.getPosition().z;

		yaw = player.getRotation().y;
		pitch = player.getRotation().x;
	}

	public Vector3f getPosition() {
		return position;
	}
	
}
