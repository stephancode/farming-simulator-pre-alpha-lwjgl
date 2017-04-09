package com.stephancode.main.utils;

import com.stephancode.main.entities.Player;
import com.stephancode.main.entities.PlayerStats;
import com.stephancode.main.entities.items.Items;
import com.stephancode.main.gfx.Art;
import com.stephancode.main.gfx.Camera;
import com.stephancode.main.gfx.InGameGUI;
import com.stephancode.main.gfx.Light;
import com.stephancode.main.gfx.Renderer;
import com.stephancode.main.gfx.font.Font;
import com.stephancode.main.gfx.shaders.Shaders;
import com.stephancode.main.level.Level;
import com.stephancode.main.level.terrain.Terrain;
import com.stephancode.main.level.terrain.Water;
import com.stephancode.main.state_menus.Menu;
import com.stephancode.math.Vector3f;

public class Components {
	
	public static float[] basic_verts = {
			-1f, -1f, 
			+1f, -1f, 
			-1f, +1f, 
			+1f, +1f, 
	};
	
	public static int[] basic_indices = {
		0, 1, 2, 2, 1, 3	
	};
	
	public static float[] basic_tex_coords = {
			0, 1,
			1, 1,
			0, 0,
			1, 0
	};
		
	private static Shaders shaders 		= new Shaders();
	private static LoadVAO loader 		= new LoadVAO();
	private static Art art 				= new Art();
	private static Camera camera 		= new Camera();
	private static Font font 			= new Font();
	private static State state 			= State.MENU;
	private static MathUtils math_utils = new MathUtils();
	private static Renderer renderer 	= new Renderer();
	private static Menu menu 			= new Menu();
	private static Light sun 			= new Light(new Vector3f(0, 200, 0), new Vector3f(1, 1, 1));
	private static Light menu_sun 		= new Light(new Vector3f(0, 50, 0), new Vector3f(1, 1, 1));
	private static Terrain terrain 		= new Terrain();
	private static Water water	 		= new Water();
	private static Mouse mouse 			= new Mouse(camera, terrain);
	private static InGameGUI gui 		= new InGameGUI();
	private static Level level 			= new Level();
	private static Items items 			= new Items();
	private static PlayerStats stats 	= new PlayerStats();
	private static Player player 		= new Player(art.floor_model);
	private static boolean can_update 	= true;
	public static int WIDTH;
	public static int HEIGHT;
	
	/* NEED PROPER IMEPLENTATION FOR CLASSES TO BE UPDATED */

	public static Menu getMenu() {return menu;}
	public static void setMenu(Menu menu){Components.menu = menu;}
	public static void setSize(int w, int h){WIDTH = w;	HEIGHT = h;}
	public static Font getFont() {return font;}
	public static State getState() {return state;}
	public static void setState(State state) {Components.state = state;}
	public static Camera getCamera() {return camera;}
	public static Level getLevel() {return level;}
	public static Art getArt() {return art;}
	public static Renderer getRenderer() {return renderer;}
	public static LoadVAO getLoader() {return loader;}
	public static Shaders getShaders() {return shaders;}
	public static Player getPlayer() {return player;}
	public static Light getSun() {return sun;}
	public static Light getMenuSun() {return menu_sun;}
	public static Mouse getMouse(){return mouse;}
	public static Terrain getTerrain() {return terrain;}
	public static Water getWater() {return water;}
	public static InGameGUI getInGameGUI() {return gui;}
	public static boolean getCanUpdate() {return can_update;}
	public static void setCanUpdate(boolean b) {can_update = b;}
	public static MathUtils getMathUtils() { return math_utils; }
	public static Items getItems() { return items; }
	public static PlayerStats getPlayerStats() { return stats; }

}
