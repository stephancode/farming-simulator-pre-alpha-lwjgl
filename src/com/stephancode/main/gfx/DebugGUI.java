package com.stephancode.main.gfx;

import com.stephancode.main.Game;
import com.stephancode.main.utils.Components;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class DebugGUI {

	public DebugGUI() {}
	
	
	
	public static void render(){
		Components.getRenderer().render_frame(-0.98f, 0.95f, 500, 150);
		Components.getFont().write512b(("Entities: " + Components.getLevel().entities.size()), new Vector2f(-0.98f, 0.88f), 1.0f, new Vector3f(1, 1, 1));
		Components.getFont().write512b(("FPS: " + Game.FPS), new Vector2f(-0.98f, 0.95f), 1.0f, new Vector3f(1, 1, 1));
	}
	
}
