package com.stephancode.main.entities.crops.seeds;

import java.util.HashMap;
import java.util.Map;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.Model;

public abstract class Seeds extends Entity{
	
	protected int timer;
	protected int time;

	protected Map<PHASE, Model> model_map 		= new HashMap<PHASE, Model>(); 
	protected Map<Integer, PHASE> type_map 		= new HashMap<Integer, PHASE>();
	protected PHASE current_phase 				= PHASE.FIRST;
	protected PHASE final_phase 				= PHASE.THIRD;

	public Seeds(Model model) {
		super(model);
		init_random_timer();
		init_growth_models();
		init_growth_times();
	}

	protected abstract void init_random_timer();
	protected abstract void init_growth_models();
	protected abstract void init_growth_times();
	
	protected void check_growth(){
		if(current_phase != final_phase){
			time++;
			if(type_map.containsKey(time)){
				set_current_phase(type_map.get(time));
				type_map.remove(time);
				time = 0;
				sync_model();
			}
		}
	}
	
	protected void sync_model(){
		if(model_map.containsKey(current_phase)) model = model_map.get(current_phase);
	}
	
	public void set_time_phase(int time, PHASE phase){ this.type_map.put(time, phase); }
	public void set_model_phase(PHASE phase, Model model){ this.model_map.put(phase, model); }
	public void set_current_phase(PHASE phase){ this.current_phase = phase; }
	public void set_final_phase(PHASE phase){ this.final_phase = phase; }
	
	public enum PHASE {FIRST, SECOND, THIRD;}
}
