package com.stephancode.main.entities;

import com.stephancode.main.utils.Components;

public class Grass extends Entity{

	public Grass() {
		super(Components.getArt().wild_grass);
		render_bigger = true;
	}

}
