package com.stephancode.main.utils;

import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.Camera;
import com.stephancode.main.gfx.Quad;
import com.stephancode.main.level.terrain.Terrain;
import com.stephancode.main.level.terrain.Water;
import com.stephancode.math.Matrix4f;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class MathUtils {

	float scalex = 1;
	float scaley = 1;

	public void update(){
		scaley = 1.0f / Components.HEIGHT;
		scalex = 1.0f / Components.WIDTH;
	}
	
	public Matrix4f create_projection_matrix() {
		float FOV = 90;
		float NEAR_PLANE = 0.1f;
		float FAR_PLANE = 1000;
		
		Matrix4f matrix = new Matrix4f();
		float aspectRatio = (float) Components.WIDTH / (float) Components.HEIGHT;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		matrix = new Matrix4f();
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		matrix.m33 = 0;
		return matrix;
	}
	
	
	public Matrix4f create_transformation_matrix(Vector3f position, Vector3f rotation, float scale) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rotation.z), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	public Matrix4f create_transformation_matrix(Entity e) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(e.getPosition(), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(e.getRotation().x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(e.getRotation().y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(e.getRotation().z), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(e.getScale(), e.getScale(), e.getScale()), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f create_transformation_matrix(Terrain terrain) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(terrain.getPosition(), matrix, matrix);
		
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(1, 0, 0),  matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(0, 1, 0),  matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(0, 0, 1),  matrix, matrix);

		Matrix4f.scale(new Vector3f(terrain.getScale(), terrain.getScale(), terrain.getScale()), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f create_transformation_matrix(Water water) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(water.getPosition(), matrix, matrix);
		
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(1, 0, 0),  matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(0, 1, 0),  matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(0), new Vector3f(0, 0, 1),  matrix, matrix);

		Matrix4f.scale(new Vector3f(water.getScale(), water.getScale(), water.getScale()), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f create_transformation_matrix(Quad quad, float w, float h) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		Matrix4f.translate(quad.getPosition(), matrix, matrix);
		Matrix4f.scale(new Vector3f((w / (1.0f / scalex)), (h / (1.0f / scaley)), 1), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f create_view_matrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		
		Matrix4f.rotate((float)Math.toRadians(camera.pitch), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(camera.yaw), new Vector3f(0, 1, 0), matrix, matrix);

		Matrix4f.translate(new Vector3f(-camera.getPosition().x, -camera.getPosition().y,
				-camera.getPosition().z), matrix, matrix);
		return matrix;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public Matrix4f create_view_matrix_rotation(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);
		
		Matrix4f.rotate((float)Math.toRadians(camera.pitch), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(camera.yaw), new Vector3f(0, 1, 0), matrix, matrix);

		Matrix4f.translate(new Vector3f(-camera.getPosition().x, -camera.getPosition().y,
				-camera.getPosition().z), matrix, matrix);
		return matrix;	}
	
}
