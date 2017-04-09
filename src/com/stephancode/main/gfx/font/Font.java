package com.stephancode.main.gfx.font;

import com.stephancode.main.gfx.Quad;
import com.stephancode.main.utils.Components;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Font {
	
	String chars = " !\"#$ &'()*+,-./"+
				   "0123456789:;<=>?"+
				   "@ABCDEFGHIJKLMNO"+
				   "PQRSTUVWXYZ[\\]^_"+
				   "'abcdefghijklmno"+
				   "pqrstuvwxyz{|}~|";
	
	public void write2048(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 80;
			float h = 80;
			Components.getRenderer().render_gui_tile(Components.getArt().letter2048, position.x + i * 0.05f, position.y + 0, scale * w, scale * h, index, color);
		}
	}

	public void write1024(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 50;
			float h = 50;
			Components.getRenderer().render_gui_tile(Components.getArt().letter1024, position.x + i * 0.03f, position.y + 0, scale * w, scale * h, index, color);
		}
	}
	
	public void write512(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 30;
			float h = 30;
			Components.getRenderer().render_gui_tile(Components.getArt().letter512, position.x + i * 0.016f, position.y + 0, scale * w, scale * h, index, color);
		}
	}
	
	public void write2048b(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 80;
			float h = 80;
			Components.getRenderer().render_gui_tile(Components.getArt().letter2048b, position.x + i * 0.05f, position.y + 0, scale * w, scale * h, index, color);
		}
	}

	public void write1024b(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 50;
			float h = 50;
			Components.getRenderer().render_gui_tile(Components.getArt().letter1024b, position.x + i * 0.03f, position.y + 0, scale * w, scale * h, index, color);
		}
	}
	
	public void write512b(String msg, Vector2f position, float scale, Vector3f color){
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			float w = 30;
			float h = 30;
			Components.getRenderer().render_gui_tile(Components.getArt().letter512b, position.x + i * 0.016f, position.y + 0, scale * w, scale * h, index, color);
		}
	}
}
