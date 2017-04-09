package com.stephancode.main.entities.crops.seeds;

import java.util.Random;

import com.stephancode.main.utils.Components;

public class CropSeeds extends Seeds{

	Random random;
	
	public CropSeeds() {
		super(Components.getArt().crop_seeds);
	}
	
	public void update(){
		check_growth();
	}

	protected void init_random_timer(){
		random 	= new Random();
		timer = random.nextInt(200) + 2000;
	}
	
	protected void init_growth_models() {
		set_model_phase(PHASE.SECOND, Components.getArt().crop_seeds2);
		set_model_phase(PHASE.THIRD, Components.getArt().crop_seeds3);
	}
	
	protected void init_growth_times() {
		set_time_phase(timer, PHASE.SECOND);
		set_time_phase(timer + random.nextInt(200) + 2000, PHASE.THIRD);
	}

}
