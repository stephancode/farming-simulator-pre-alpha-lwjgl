package com.stephancode.main.entities.items;

import com.stephancode.main.entities.Grass;
import com.stephancode.main.entities.crops.CropGround;
import com.stephancode.main.entities.crops.seeds.CropSeeds;
import com.stephancode.main.entities.items.Item.TYPE;
import com.stephancode.main.entities.trees.Tree;

public class Items {
	
	/**MUST REDO THIS WHOLE SHIT**/	
	String des[] 	= {"This is the", "description of", "an item boi!", "lol... so funny", ":("};
	String des1[] 	= {"This is the", "description of", "a tree sampling!", "lol... so funny", ":("};
	String des2[] 	= {"This is the", "description of", "a crop thing!", "lol... so funny", ":("};
	
	public final Item INVALID = new Item(new CropGround(), TYPE.TILE, "INVALID ITEM", des);
	public final Item normal_sapling = new Item(new Tree(), TYPE.SAPLING, "A tree!", des1);
	public final Item crop_ground = new Item(new CropGround(), TYPE.TILE, "Ground!!!!", des2);
	public final Item crop_seed = new Item(new CropSeeds(),  TYPE.SEEDS, "Seeds!", des);
	public final Item wild_grass = new Item(new Grass(),  TYPE.TILE, "GRASS", des);
	
}
