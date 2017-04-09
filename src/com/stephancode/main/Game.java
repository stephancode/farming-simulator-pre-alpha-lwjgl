package com.stephancode.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import com.stephancode.main.entities.PlayerStats;
import com.stephancode.main.gfx.DebugGUI;
import com.stephancode.main.state_menus.InventoryMenu;
import com.stephancode.main.state_menus.Menu;
import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.KeyboardHandler;
import com.stephancode.main.utils.State;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Game{

	public static double game_time;
	public static long window;
	public static int FPS = 0;
	private int switch_time = 0;

	public int WIDTH;
	public int HEIGHT;
		
	public Game(){
		if(!glfwInit()) throw new RuntimeException("Could not init the glfw!\n");
		GLFWVidMode video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		window = glfwCreateWindow(video_mode.width(), video_mode.height(), "Farming thing", 0, 0);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		glfwSwapInterval(1);
		createCapabilities();
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		WIDTH = w.get(0);
		HEIGHT = h.get(0);
		glfwSetKeyCallback(window, new KeyboardHandler());
		init();
		run();
	}
	
	private void init(){
		Components.setSize(WIDTH, HEIGHT);
	}
	
	public void update(){
		Components.getMathUtils().update();
		if(Components.getState() == State.INGAME){
			
			if(switch_time++ == 0) glfwSetInputMode(Game.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
			Components.getPlayer().update();
			Components.getLevel().update_entities();
			Components.getCamera().update(Components.getPlayer());
			Components.getMouse().update();
			if(KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
				switch_time = 0;
				glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
				Components.setMenu(new Menu());
				Components.setState(State.MENU);
			}

		} else if(Components.getState() == State.MENU){
			if(Components.getMenu() instanceof InventoryMenu)
				Components.getPlayer().update();
			
			Components.getMenu().update();
		}
	}
	
	public void render(){
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		    
		if(Components.getState() == State.INGAME){
			glClearColor(0.3f, 0.3f, 1.0f, 1);
			
			if(KeyboardHandler.isKeyDown(GLFW_KEY_X)) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			
			Components.getLevel().render_terrain(Components.getSun(),Components.getTerrain(), Components.getCamera());
			Components.getLevel().render_water(Components.getSun(),Components.getWater(), Components.getCamera());
			Components.getInGameGUI().display_indicator();
			Components.getLevel().render_entities();
			Components.getRenderer().render_pointer();

			Components.getInGameGUI().render();
			
			DebugGUI.render();
			
		} else if(Components.getState() == State.MENU){
			Components.getMenu().render();
		}
	}
	
	public static void main(String[] args){
		new Game();
	}
	

	public void run() {

		int fps = 0;
		int ups = 0;
		
		double then = System.nanoTime();
		double fpsTimer = System.currentTimeMillis();
		double delta = 0;
		double nspt = 1000000000 / 60d;
		
		while(!glfwWindowShouldClose(window)){
			glfwPollEvents();
			glfwSwapBuffers(window);
			double now = System.nanoTime();
			delta += (now - then) / nspt;
			then = now;
			while(delta >= 1){
				ups++;
				update();
				delta--;
			}
			
			fps++;
			render();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			game_time = glfwGetTime();
			
			if(System.currentTimeMillis() - fpsTimer >= 1000){
				FPS = fps;
				fps = ups = 0;
				fpsTimer += 1000;
			}
		}
		Components.getLoader().clear();
	}
		
}
