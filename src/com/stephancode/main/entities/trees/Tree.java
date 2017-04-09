package com.stephancode.main.entities.trees;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.Model;
import com.stephancode.main.utils.Components;

public class Tree extends Entity{

	public Tree() {
		super(Components.getArt().tree_model);
		render_bigger = true;
//		super.setScale(4);
	}
	
	public void update(){}

}
