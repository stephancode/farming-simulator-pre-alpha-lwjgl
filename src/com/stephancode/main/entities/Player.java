package com.stephancode.main.entities;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import com.stephancode.main.Game;
import com.stephancode.main.entities.crops.CropGround;
import com.stephancode.main.entities.crops.seeds.CropSeeds;
import com.stephancode.main.entities.items.Item;
import com.stephancode.main.entities.items.Item.TYPE;
import com.stephancode.main.entities.items.Slot;
import com.stephancode.main.entities.trees.Tree;
import com.stephancode.main.gfx.Model;
import com.stephancode.main.state_menus.InventoryMenu;
import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.KeyboardHandler;
import com.stephancode.main.utils.State;

public class Player extends Entity {

	private float speed 					= 0;
	private float normal_speed 				= 0.6f;
	private float sprint_speed 				= 1.0f;
	private float turn_speed				= 0.4f;
	private float dx 						= 0;
	private float dz 						= 0;
	private float current_turn_speed 		= 0;
	private float current_turn_speed_x 		= 0;
	private float move_dist 				= 0;
	private boolean rot_x 					= false;
	private boolean rot_y 					= false;
	private double delta_x 					= 0;
	private double delta_y 					= 0;
	private int time 						= 0;
	private int switch_time 				= 0;

	public Player(Model model) {
		super(model);

		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().crop_ground));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().normal_sapling));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().crop_seed));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().wild_grass));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().normal_sapling));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().crop_seed));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().wild_grass));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().crop_seed));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().wild_grass));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().wild_grass));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().normal_sapling));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().crop_seed));
		Components.getPlayerStats().getSlots().add(new Slot(Components.getItems().wild_grass));
	}

	public void update() {
		dx = 0;
		dz = 0;
		if (switch_time > 0)
			switch_time--;

		if (Components.getCanUpdate()) {
			mouse_movement();

			if (rot_y)
				current_turn_speed = (float) (delta_x * turn_speed);
			if (rot_x)
				current_turn_speed_x = (float) (delta_y * turn_speed);
			else
				current_turn_speed_x = 0;
			if (current_turn_speed_x >= 90)
				current_turn_speed_x = 90;
			setRotation(current_turn_speed_x, current_turn_speed, 0);

			if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT) || KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT_SHIFT))
				speed = sprint_speed;
			else
				speed = normal_speed;

			if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
				dx -= (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
				dz -= (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
			} else if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
				dx += (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
				dz += (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
			}

			if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
				dx += (float) (Math.sin((-rotation.y - 90) * Math.PI / 180) * speed);
				dz += (float) (Math.cos((-rotation.y - 90) * Math.PI / 180) * speed);
			} else if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
				dx += (float) (Math.sin((-rotation.y + 90) * Math.PI / 180) * speed);
				dz += (float) (Math.cos((-rotation.y + 90) * Math.PI / 180) * speed);
			}

			float dy = (float) Math.sin(move_dist) * 0.1f;

			if (dx != 0 || dz != 0) {
				move_dist += 0.2f;
				setPosition(dx, dy, +dz);
			} else
				dy = 0;

			// tmp I think
			if (glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS && time == 0) {
				if (Components.getMouse().getCurrentTerrainPoint() != null) {
					float dxx = position.x - Components.getMouse().getCurrentTerrainPoint().x;
					float dzz = position.z - Components.getMouse().getCurrentTerrainPoint().z;
					float distt = dxx * dxx + dzz * dzz;
					if (distt < 10000) {
						time = 30;
						Components.getPlayerStats().place_item();
					}
				}
			}

			if (time > 0)
				time--;
			if (KeyboardHandler.isKeyDown(GLFW_KEY_E) && switch_time == 0) {
				switch_time = 30;
				Components.getMouse().unlock_mouse();
				Components.setCanUpdate(false);
				Components.setState(State.MENU);
				Components.setMenu(new InventoryMenu());
			}

		} else {
			if (KeyboardHandler.isKeyDown(GLFW_KEY_E) && switch_time == 0) {
				switch_time = 30;
				Components.getMouse().lock_mouse();
				Components.setCanUpdate(true);
				Components.setState(State.INGAME);
			}
		}
	}

	private void mouse_movement() {
		double new_x = Components.WIDTH / 2;
		double new_y = Components.HEIGHT / 2;
		double prev_x = 0;
		double prev_y = 0;
		Components.getMouse().check_mouse_status();
		if (Components.getMouse().getMouseLockStatus()) {
			DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
			DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

			glfwGetCursorPos(Game.window, x, y);
			x.rewind();
			y.rewind();

			new_x = x.get();
			new_y = y.get();

			delta_x = new_x - Components.WIDTH / 2;
			delta_y = new_y - Components.HEIGHT / 2;

			rot_x = new_x != prev_x;
			rot_y = new_y != prev_y;

			prev_x = new_x;
			prev_y = new_y;

			glfwSetCursorPos(Game.window, Components.WIDTH / 2, Components.HEIGHT / 2);
		}
	}

	public float getDx() {return dx;}
	public float getDz() {return dz;}
	public float getCurrent_turn_speed() {return current_turn_speed;}
	public float getCurrent_turn_speed_x() {return current_turn_speed_x;}

}
