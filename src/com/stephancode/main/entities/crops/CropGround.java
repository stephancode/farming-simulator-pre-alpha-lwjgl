package com.stephancode.main.entities.crops;

import java.util.Random;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.utils.Components;

public class CropGround extends Entity{

	public CropGround() {
		super(Components.getArt().crop_ground);
	}

	public void update(){
		x0 = position.x - 8f;
		x1 = position.x + 8f;
		z0 = position.z - 8f;
		z1 = position.z + 8f;
//		timer++;
//		timer %= res;
//		if(timer == 0 && tmp++ == 0) {
//			CropSeeds seeds = new CropSeeds();
//			seeds.setPosition(position);
//			//seeds.setScale(1);
//			Components.getLevel().add_entity(seeds);
//		}
	}
}
