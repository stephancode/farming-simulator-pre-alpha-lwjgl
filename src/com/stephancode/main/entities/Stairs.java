package com.stephancode.main.entities;

import com.stephancode.main.utils.Components;

public class Stairs extends Entity{

	public Stairs() {
		super(Components.getArt().stairs);
	}
	
	public void update(){
		scale = 5f;
		position.y = -9;
	}

}
