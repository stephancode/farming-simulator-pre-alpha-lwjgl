package com.stephancode.main.utils;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.stephancode.main.Game;

public class KeyboardHandler extends GLFWKeyCallback{

	private static boolean keys[] = new boolean[65536];
	public boolean forward, backwards, left, right; 
	
	public KeyboardHandler(){
	}
	
	public void update(){
		forward = keys[KeyEvent.VK_W] | keys[KeyEvent.VK_UP]; 
		backwards = keys[KeyEvent.VK_S] | keys[KeyEvent.VK_DOWN]; 
		left = keys[KeyEvent.VK_A] | keys[KeyEvent.VK_LEFT]; 
		right = keys[KeyEvent.VK_D] | keys[KeyEvent.VK_RIGHT]; 
	}
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
	}
	
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

}
