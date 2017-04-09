package com.stephancode.main.state_menus;

import static org.lwjgl.glfw.GLFW.*;

import com.stephancode.main.Game;
import com.stephancode.main.entities.Entity;
import com.stephancode.main.entities.PlayerStats;
import com.stephancode.main.entities.items.Slot;
import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.State;
import com.stephancode.main.utils.VirtualGameObject;
import com.stephancode.math.Vector3f;

public class InventoryMenu extends Menu {

	private int slotprow 		= 5;
	private float dist_x 		= 0.21f;
	private float dist_y 		= 0.385f;
	float w 					= -Components.WIDTH / 2;
	float inc 					= 60;
	Vector3f rotation 			= new Vector3f(40, 0, 0);
	Vector3f small_rotation 	= new Vector3f(0, 0, 0);
	


	public void update(){
		glfwGetCursorPos(Game.window, x, y);
		xx = (float) (2f * x.get(0)) / Components.WIDTH - 1;
		yy = (float) (2f * y.get(0)) / Components.HEIGHT - 1;
		yy *= -1;
	}

	public void render(){
		Components.getLevel().render_terrain(Components.getSun(),Components.getTerrain(), Components.getCamera());
		Components.getLevel().render_water(Components.getSun(),Components.getWater(), Components.getCamera());
		Components.getLevel().render_entities();
		Components.getRenderer().render_blur_frame(0, 0, Components.WIDTH, Components.HEIGHT);
		render_slots_background();
	}

	public void render_slots(float x){
		float xxx = -0.35f; 
		float yyy = -0.59f; 

		for(int i = 0; i < Components.getPlayerStats().getInventorySlots(); i++){
			float tx = xxx + ((i % slotprow) * dist_x);
			float ty = yyy + ((i / slotprow) * dist_y);
			Components.getRenderer().render_frame(x + tx, -ty, 200, 200);
			if(Components.getPlayerStats().getSlots().size() != 0){
				if(i < Components.getPlayerStats().getSlots().size()){
					Entity e = Components.getPlayerStats().getSlots().get(i).getItem().getEntity();
					if(!e.render_bigger){
						float z = -130;
						Vector3f position = new Vector3f(x * (128) - tx * (-130), -ty * 70f, z);
						rotation.y += 0.1f;
						Components.getRenderer().render_tmp_entity2d(e, position, rotation);
					} else {
						float z = -35;
						Vector3f position = new Vector3f(x * (35.3f) - tx * (-35), -1.3f -ty * 18f, z);
						small_rotation.y += 0.1f;
						Components.getRenderer().render_tmp_entity2d(e, position, small_rotation);
					}
					VirtualGameObject select_slot = new VirtualGameObject(x + tx - 0.1f, -ty + 0.18f, 50, 150);
					if(xx >= select_slot.getX0() && xx <= select_slot.getX1() - 0.517f &&
							yy <= select_slot.getY0() && yy >= select_slot.getY1()- 0.445f){
						Components.getRenderer().render_frame(x + tx, -ty, 200, 200);
						if(glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_1) == GLFW_TRUE){
							select(Components.getPlayerStats().getSlots().get(i));
							Components.setState(State.INGAME);
							Components.getMouse().lock_mouse();
							Components.setCanUpdate(true);
							break;
						}
					}

				}
			}
		}
	}
	
	private void select(Slot slot) {
		/**NEED TO ADD STUFF IN HERE**/
		Components.getPlayerStats().setCurrentItem(slot.getItem());
	}

	public void render_slots_background(){
		w += inc;
		float trans = (2f * w) / Components.WIDTH - 1;
		Components.getRenderer().render_frame(trans, 0, Components.WIDTH - Components.WIDTH / 3, Components.HEIGHT - 100);
		inc -= 1.2f;
		if(inc < 0) inc = 0;
		render_slots(trans);
	}
	
}






