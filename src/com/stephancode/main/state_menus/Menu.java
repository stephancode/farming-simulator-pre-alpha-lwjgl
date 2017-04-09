package com.stephancode.main.state_menus;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import com.stephancode.main.Game;
import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.State;
import com.stephancode.main.utils.VirtualGameObject;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Menu {

	String[] options 	= {"Start game", "About", "Exit game"};
	int current_option 	= 1;
	
	DoubleBuffer x 		= BufferUtils.createDoubleBuffer(1);
	DoubleBuffer y 		= BufferUtils.createDoubleBuffer(1);
	int time 			= 0;
	float scalex 		= 1.0f / Components.WIDTH;
	float scaley 		= 1.0f / Components.HEIGHT;
	float xx;
	float yy;
		
	public Menu() {

	}

	public void update(){
		glfwGetCursorPos(Game.window, x, y);
		xx = (float) (2f * x.get(0)) / Components.WIDTH - 1;
		yy = (float) (2f * y.get(0)) / Components.HEIGHT - 1;
		yy *= -1;
		detect_mouse();
		if (glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) select();
	}
	
	private void select() {
		switch(current_option){
			case 0:
				Components.setState(State.INGAME);
				break;
			case 2:
				//				
				break;
			//default: throw new RuntimeException("What is this?? index " + current_option);
		}
	}

	public void render(){
		Components.getLevel().render_terrain(Components.getSun(), Components.getTerrain(), Components.getCamera());
		Components.getCamera().yaw += 0.1f;

		Components.getRenderer().render_frame(0, 0,
				Components.WIDTH / 2, Components.HEIGHT * 2);
		for(int i = 0; i < options.length; i++){
			if(current_option == i)
				Components.getFont().write2048(options[i], new Vector2f(-0.21f, -0.10f + i * (-1.0f / 9)), 1.0f,
						new Vector3f(1, 0, 0));
			else
				Components.getFont().write2048(options[i], new Vector2f(-0.21f, -0.10f + i * (-1.0f / 9)), 1.0f,
						new Vector3f(1, 1, 1));

//			float x0 = 0f;
//			float y0 = -0.1f -0.25f * i / 2;
//
//			VirtualGameObject select_box = new VirtualGameObject(x0, y0, 450, 50);
//			
//
//			Components.getRenderer().render_frame(select_box.getX0(), select_box.getY0()
//												, 5, 5);
//
//			Components.getRenderer().render_frame(select_box.getX1(), select_box.getY0()
//					, 5, 5);
//			
//			Components.getRenderer().render_frame(select_box.getX0(), select_box.getY1()
//					, 5, 5);
//
//			Components.getRenderer().render_frame(select_box.getX1(), select_box.getY1()
//					, 5, 5);
//			
//			

		}
		

	}
	
	private void detect_mouse(){
		for(int i = 0; i < options.length; i++){
			float x0 = 0f;
			float y0 = -0.1f -0.25f * i / 2;

			VirtualGameObject select_box = new VirtualGameObject(x0, y0, 450, 50);
//			if(select_box.getX0() <= xx && select_box.getX1() >= xx) System.out.println("yea bhoi");
			if(select_box.getY0() <= yy && select_box.getY1() >= yy) System.out.println("lol bhoi");
					
			if(!(select_box.getX0() <= xx && select_box.getX1() >= xx &&
				select_box.getY0() <= yy && select_box.getY1() <= yy))
				current_option = i;
			
//			double dx = Math.max(Math.abs(xx - x0 + 225) - 450 / 2.0, 0);
//			double dy = Math.max(Math.abs(yy - y0 + 25) - 50 / 2.0, 0);
//			double dist = dx * dx + dy * dy;
			
//			if(dist == 0) current_option = i;
		}
	}
	
}
