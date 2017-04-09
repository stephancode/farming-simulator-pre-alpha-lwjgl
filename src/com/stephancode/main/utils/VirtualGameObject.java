package com.stephancode.main.utils;

public class VirtualGameObject {

	private float x0 = -1;
	private float y0 = -1;
	private float x1 = -1;
	private float y1 = -1;

	public VirtualGameObject(float x, float y, int w, int h) {
		float tw = (float) (2f * w) / Components.WIDTH - 1;
		float th = (float) (2f * h) / Components.HEIGHT - 1;

		this.x0 = x;
		this.y0 = y;
		this.x1 = x0 - tw - 0.23f;
		this.y1 = y0 + th + 0.8f;
	}
	
	public float getX0(){ return x0; }
	public float getY0(){ return y0; }
	public float getX1(){ return x1; }
	public float getY1(){ return y1; }
}
