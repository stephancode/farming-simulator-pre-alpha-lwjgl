package com.stephancode.main.level.terrain;

import com.stephancode.main.gfx.Model;
import com.stephancode.main.utils.Components;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Terrain {

	private Vector3f position 		= new Vector3f(0, 0, 0);
	private float scale 			= 20.0f;
	private Model model 			= Components.getArt().floor_model;
	
	public Terrain(){
		position = new Vector3f(0, -10, 0);
	}
	
	public Vector3f getPosition() {	return position;}
	public Model getModel() {return model;}
	public float getScale() {return scale;}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
//	public float getHeight(float xa, float za){
//		float result = 0;
//		float tx = xa - position.x;
//		float tz = za - position.z;
//		float grid_size = SIZE / ((float)heightMap.length - 1);
//		int xx = (int) Math.floor(tx / grid_size);
//		int zz = (int) Math.floor(tz / grid_size);
//		
//		//System.out.println("xx: " + xx + ", yy: " + zz);
//		
//		if(xx < 0 || xx >= heightMap.length - 1 || zz < 0 || zz >= heightMap.length - 1){
//			System.out.println("WHAT DID I DO WRONG?");
//			return -9;
//		}
//		float xc = (tx % grid_size) / grid_size;
//		float zc = (tz % grid_size) / grid_size;
//		if (xc <= (1-zc)){ 
//			result = 9 - GameMath.barryCentric(new Vector3f(0, heightMap[xx][zz], 0), new Vector3f(1,
//							heightMap[xx + 1][zz], 0), new Vector3f(0,
//							heightMap[xx][zz + 1], 1), new Vector2f(xc, zc));
//			//System.out.println("yea k");
//		} else { 
//			result = 9 - GameMath.barryCentric(new Vector3f(1, heightMap[xx + 1][zz], 0), new Vector3f(1,
//							heightMap[xx + 1][zz + 1], 1), new Vector3f(0,
//							heightMap[xx][zz + 1], 1), new Vector2f(xc, zc));
//			//System.out.println("yea k2");
//		}
//		
//		return result;
//	}
	
}





