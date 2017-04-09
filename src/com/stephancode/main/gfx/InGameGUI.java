package com.stephancode.main.gfx;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import com.stephancode.main.entities.PlayerStats;
import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.KeyboardHandler;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class InGameGUI {
	
	private int time 		= 0;
	private int i 			= 0;
	private boolean show 	= false;
	
    public void display_indicator(){
        i %= 2;
        if(time > 0) time--;
    	if(KeyboardHandler.isKeyDown(GLFW_KEY_Z) && time == 0 && i == 0) {
        	time = 30;
        	i++;
        	show = true;
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_Z) && time == 0 && i == 1){
        	time = 30;
        	i++;
        	show = false;
        }
        
	    if(show)
	    	if(Components.getMouse().getCurrentTerrainPoint() != null)
	    		Components.getRenderer().render_tmp_entity(Components.getPlayerStats().getCurrentItem().getEntity(), Components.getMouse().getCurrentTerrainPoint());
    }

	public void render(){
		Components.getRenderer().render_frame(-1f + 0.2f, 0, 250, 450);

		Components.getFont().write512b(Components.getPlayerStats().getCurrentItem().getName(), new Vector2f(-1f + 0.1f, 0.35f), 1.1f,
				  new Vector3f(1, 1, 1));

		for(int i = 0; i < Components.getPlayerStats().getCurrentItem().getDescription().length; i++){
			Components.getFont().write512b(Components.getPlayerStats().getCurrentItem().getDescription()[i], new Vector2f(-1f + 0.085f, 0.15f - i * 0.06f), 1.1f,
				  new Vector3f(1, 1, 1));
		}
	}
}
