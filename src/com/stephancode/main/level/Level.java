package com.stephancode.main.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.entities.Grass;
import com.stephancode.main.gfx.Camera;
import com.stephancode.main.gfx.Light;
import com.stephancode.main.gfx.Model;
import com.stephancode.main.level.terrain.Terrain;
import com.stephancode.main.level.terrain.Water;
import com.stephancode.main.utils.Components;

public class Level {
	
	Map<Model, List<Entity>> to_process = new HashMap<Model, List<Entity>>();
	public List<Entity> entities = new ArrayList<Entity>();
	public Level(){
//		for(float x = -100; x < 100; x += 6.5f){
//			for(float z = -50; z < 0; z += 6.5f){
//				Grass grass = new Grass();
//				grass.setPosition(x, -9, z);
//				add_entity(grass);
//			}			
//		}
	}
	
	public void update_entities(){
		for(int i = 0; i < entities.size(); i++) entities.get(i).update();
	}
	
	public void render_entities(){
		for(Entity e : entities) add_to_process(e);
		Components.getRenderer().render(to_process, Components.getSun());
	}
	
	public void render_terrain(Light light, Terrain terrain, Camera camera){
		Components.getRenderer().render_terrain(light, terrain, camera);
	}
	
	public void render_water(Light light, Water water, Camera camera){
		Components.getRenderer().render_water(light, water, camera);
	}
	
	
	
	private void add_to_process(Entity e){
		Model model = e.getModel();
		List<Entity> batch = to_process.get(model);
		if(batch != null) batch.add(e);
		else{
			List<Entity> new_batch = new ArrayList<Entity>();
			new_batch.add(e);
			to_process.put(model, new_batch);
		}
	}
	
	
	public void add_entity(Entity e){
		entities.add(e);
	}
	
}

