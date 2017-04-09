package com.stephancode.main.entities.items;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.entities.crops.CropGround;
import com.stephancode.main.entities.crops.seeds.CropSeeds;
import com.stephancode.main.entities.trees.Tree;

public class Item {

	private Entity to_place;
	private String name;
	private String[] description;
	private TYPE type;
	
	public Item(Entity to_place, TYPE type, String name, String[] description) {
		this.to_place = to_place;
		this.name = name;
		this.description = description;
		this.type = type;
	}

	public Entity getEntity(){return to_place;}
	public String getName() {return name;}
	public String[] getDescription() {return description;}
	public TYPE getType() {return type;}
	
	public enum TYPE {TILE, SEEDS, SAPLING;}

	
}
