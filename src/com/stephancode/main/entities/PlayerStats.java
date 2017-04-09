package com.stephancode.main.entities;

import java.util.ArrayList;
import java.util.List;

import com.stephancode.main.entities.crops.CropGround;
import com.stephancode.main.entities.crops.seeds.CropSeeds;
import com.stephancode.main.entities.items.Item;
import com.stephancode.main.entities.items.Item.TYPE;
import com.stephancode.main.entities.items.Slot;
import com.stephancode.main.entities.trees.Tree;
import com.stephancode.main.utils.Components;

public class PlayerStats {

	private Item current_item 	= Components.getItems().INVALID;
	private int inventory_slots = 20; 
	private List<Slot> slots 	= new ArrayList<Slot>();

	public void place_item() {
		/** POOR WAY MUSTTT CHANGE THIS SHIT **/
		Entity e = select_item();
		TYPE type = select_type();
		switch (type) {
		case SEEDS:
			if (Components.getMouse().getIntersectedEntity(Components.getPlayer()) != null) {
				Components.getLevel().add_entity(e);
				e.position = Components.getMouse().getIntersectedEntity(Components.getPlayer()).getPosition();
			}
			break;
		case TILE:
			e.setPosition(Components.getMouse().getCurrentTerrainPoint());
			Components.getLevel().add_entity(e);
			break;
		case SAPLING:
			e.setPosition(Components.getMouse().getCurrentTerrainPoint());
			Components.getLevel().add_entity(e);
			break;
		}
	}

	private Entity select_item() {
		Entity result = null;
		if (getCurrentItem().getEntity() instanceof CropGround) result = new CropGround();
		if (getCurrentItem().getEntity() instanceof CropSeeds) result = new CropSeeds();
		if (getCurrentItem().getEntity() instanceof Tree) result = new Tree();
		if (getCurrentItem().getEntity() instanceof Grass) result = new Grass();
		return result;
	}

	private TYPE select_type() {return getCurrentItem().getType();}
	
	public Item getCurrentItem(){ return current_item; }
	public int getInventorySlots(){ return inventory_slots; }
	public void setCurrentItem(Item item) { current_item = item; }
	public List<Slot> getSlots(){ return slots; }
}
