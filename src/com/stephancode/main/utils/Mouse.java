package com.stephancode.main.utils;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import com.stephancode.main.Game;
import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.Camera;
import com.stephancode.main.level.terrain.Terrain;
import com.stephancode.math.Matrix4f;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;
import com.stephancode.math.Vector4f;

public class Mouse {

	private static final int RECURSION_COUNT 	= 200;
	private static final float RAY_RANGE 		= 300;
	private Vector3f currentRay 				= new Vector3f();
	private static boolean mouse_locked 		= true;
	private Matrix4f projection_matrix;
	private Matrix4f view_matrix;
	private Camera camera;
	private Terrain terrain;
	private Vector3f currentTerrainPoint;

	public Mouse(Camera cam, Terrain terrain) {
		camera = cam;
		this.terrain = terrain;
	}
	

	public void update() {
		projection_matrix = Components.getMathUtils().create_projection_matrix();
		view_matrix = Components.getMathUtils().create_view_matrix(camera);

		currentRay = calculateMouseRay();
		if (intersectionInRange(0, RAY_RANGE, currentRay)) {
			currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
		} else {
			currentTerrainPoint = null;
		}
	}

	private Vector3f calculateMouseRay() {
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Game.window, x, y);
		
		float mouseX = (float)x.get(0);
		float mouseY = (float)y.get(0);
		Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(view_matrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projection_matrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Components.WIDTH - 1f;
		float y = (2.0f * mouseY) / Components.HEIGHT - 1f;
		return new Vector2f(x, y);
	}
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			Terrain terrain = this.terrain;
			if (terrain != null) {
				return endPoint;
			} else {
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		if (!isUnderGround(startPoint) && isUnderGround(endPoint)) return true;
		else return false;
	}

	private boolean isUnderGround(Vector3f testPoint) {
//		float height = terrain.getHeight(testPoint.getX(), testPoint.getZ());
		float height = -9;
		if (testPoint.y < height) return true;
		else return false;
	}
	
	public Entity getIntersectedEntity(Entity owner){
		Entity result = null;
		
		for(Entity e : Components.getLevel().entities){
			if((getCurrentTerrainPoint().x >= e.x0 && getCurrentTerrainPoint().x <= e.x1 &&
			   getCurrentTerrainPoint().z >= e.z0 && getCurrentTerrainPoint().z <= e.z1) && e != owner){
				result = e;
			}
		}
		
		return result;
	}
	
	public void check_mouse_status(){
		if (glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) unlock_mouse();
		if (glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS) lock_mouse();
	}
	
	public void lock_mouse(){
		glfwSetCursorPos(Game.window, Components.WIDTH/2, Components.HEIGHT/2);
		mouse_locked = true;
		glfwSetInputMode(Game.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	public void unlock_mouse(){
		mouse_locked = false;
		glfwSetInputMode(Game.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}

	public Vector3f getCurrentTerrainPoint() { return currentTerrainPoint; }
	public Vector3f getCurrentRay() { return currentRay; }
	public boolean getMouseLockStatus(){ return mouse_locked; }

}