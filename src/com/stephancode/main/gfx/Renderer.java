package com.stephancode.main.gfx;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stephancode.main.Game;
import com.stephancode.main.entities.Entity;
import com.stephancode.main.gfx.shaders.Shader;
import com.stephancode.main.level.terrain.Terrain;
import com.stephancode.main.level.terrain.Water;
import com.stephancode.main.utils.Components;
import com.stephancode.math.Matrix4f;
import com.stephancode.math.Vector2f;
import com.stephancode.math.Vector3f;

public class Renderer {
	
	Matrix4f projection_matrix 			= Components.getMathUtils().create_projection_matrix();
	Map<Model, List<Entity>> entities 	= new HashMap<Model, List<Entity>>();
	
	public Renderer(){
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public void render(Map<Model, List<Entity>> entities, Light light){
		for(Model model : entities.keySet()){
			prepare_gl(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch){
				init_entity_gl(entity);
				Components.getShaders().basic_shader.inject_light(light);
				Components.getShaders().basic_shader.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
				Components.getShaders().basic_shader.inject_view_matrix(Components.getMathUtils().create_view_matrix(Components.getCamera()));

				glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
			}
			terminate_gl(model);
		}
		entities.clear();
	}
	

	

	public void render_rotation_quad(Entity e) {
		Model model = e.getQuad().getModel();
		prepare_gl(model, Components.getShaders().rotation_shader);

		Components.getShaders().rotation_shader.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(e));
		Components.getShaders().rotation_shader.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
		Components.getShaders().rotation_shader.inject_view_matrix(Components.getMathUtils().create_view_matrix_rotation(Components.getCamera()));

		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		
		terminate_gl(model, Components.getShaders().rotation_shader);
	}
	
	public void render_tmp_entity(Entity e, Vector3f position){
		Model model = e.getModel();
		prepare_gl(model);
		e.setPosition(position);
		init_entity_gl(e);
		Components.getShaders().basic_shader.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
		Components.getShaders().basic_shader.inject_view_matrix(Components.getMathUtils().create_view_matrix(Components.getCamera()));
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		terminate_gl(model);
	}

	public void render_tmp_entity2d(Entity e, Vector3f position, Vector3f rotation){
		Model model = e.getModel();
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(3);

		Components.getShaders().basic_shader2d.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
	

//		init_entity_gl(e);
		Components.getShaders().basic_shader2d.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(position, rotation, 1.0f));
		Components.getShaders().basic_shader2d.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
		Components.getShaders().basic_shader2d.inject_light(Components.getMenuSun());
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		
		Components.getShaders().basic_shader2d.stop();

		gl_disable_attribs(3);
		glBindVertexArray(0);
		
	}

	public void init_entity_gl(Entity e){
		Components.getShaders().basic_shader.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(e));
	}
	

	public void render_terrain(Light light, Terrain terrain, Camera camera){
		Model model = terrain.getModel();
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(3);

		Components.getShaders().floor_shader.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
		
		Components.getShaders().floor_shader.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(terrain));
		Components.getShaders().floor_shader.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
		Components.getShaders().floor_shader.inject_view_matrix(Components.getMathUtils().create_view_matrix(Components.getCamera()));
		Components.getShaders().floor_shader.inject_light(light);
		
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		
		Components.getShaders().floor_shader.stop();

		gl_disable_attribs(3);
		glBindVertexArray(0);

	}
	
	public void render_water(Light light, Water water, Camera camera){
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDepthMask(true);
		Model model = water.getModel();
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(3);

		Components.getShaders().water_shader.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
		Components.getShaders().water_shader.inject_time((float)Game.game_time);
		Components.getShaders().water_shader.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(water));
		Components.getShaders().water_shader.inject_projection_matrix(Components.getMathUtils().create_projection_matrix());
		Components.getShaders().water_shader.inject_view_matrix(Components.getMathUtils().create_view_matrix(Components.getCamera()));
		Components.getShaders().water_shader.inject_light(light);
		Components.getShaders().water_shader.inject_specular_attr(water.getDamping(), water.getReflectivity());
		
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		
		Components.getShaders().water_shader.stop();

		gl_disable_attribs(3);
		glBindVertexArray(0);

	}
	
	public void render_gui_tile(Quad quad, float x, float y, float w, float h){
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Model model = quad.getModel();
		quad.setPosition(new Vector2f(x, y));
		
		prepare_gl(quad);
		Components.getShaders().font_shader.inject_rows(model.getRows());
		Components.getShaders().font_shader.inject_offset(new Vector2f(0, 0));

		Components.getShaders().font_shader.inject_transformation_matrix(
				Components.getMathUtils().create_transformation_matrix(quad, w, h));
		
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);

		terminate_gl(quad);

	}
	
	public void render_gui_tile(Quad quad, float x, float y, float w, float h, int index, Vector3f color){
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Model model = quad.getModel();

		quad.setPosition(new Vector2f(x, y));

		prepare_gl(quad);
		model.setOffset(index);
		Components.getShaders().font_shader.inject_rows(model.getRows());
		Components.getShaders().font_shader.inject_offset(new Vector2f(model.getXOffset(), model.getYOffset()));
		Components.getShaders().font_shader.inject_color(color);
		
		Components.getShaders().font_shader.inject_transformation_matrix(Components.getMathUtils().create_transformation_matrix(quad, w, h));
		
		glDrawElements(GL_TRIANGLES, model.getVert_count(), GL_UNSIGNED_INT, 0);
		
		terminate_gl(quad);

	}

	private void prepare_gl(Model model){
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(3);

		Components.getShaders().basic_shader.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
	}	
	
	private void prepare_gl(Model model, Shader shader){
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(3);

		shader.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
	}	
	
	private void terminate_gl(Model model, Shader shader){
		shader.stop();

		gl_disable_attribs(3);
		glBindVertexArray(0);
	}
	
	private void terminate_gl(Model model){
		Components.getShaders().basic_shader.stop();

		gl_disable_attribs(3);
		glBindVertexArray(0);
	}

	private void prepare_gl(Quad quad){
		Model model = quad.getModel();
		glBindVertexArray(model.getVaoId());

		gl_enable_attribs(2);
		Components.getShaders().font_shader.start();
	
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, model.getTexId());
		
	}

	private void terminate_gl(Quad quad){
		Components.getShaders().font_shader.stop();
		
		gl_disable_attribs(2);
		glBindVertexArray(0);
	}

	public void gl_enable_attribs(int num){
		for(int i = 0; i < num; i++) glEnableVertexAttribArray(i);
	}

	public void gl_disable_attribs(int num){
		for(int i = 0; i < num; i++) glDisableVertexAttribArray(i);
	}
	
	public void render_frame(float xa, float ya, float w, float h){
		render_gui_tile(Components.getArt().frame_quad , xa, ya, w, h);
	}
	
	public void render_blur_frame(float xa, float ya, float w, float h){
		render_gui_tile(Components.getArt().blur_quad , xa, ya, w, h);
	}

	public void render_pointer(){render_gui_tile(Components.getArt().pointer_quad , 0, 0, 4, 4);}

	
}
