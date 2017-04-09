package com.stephancode.main.gfx;

import com.stephancode.main.utils.Components;
import com.stephancode.main.utils.LoadOBJ;
import com.stephancode.math.Vector2f;

public class Art {
	
	/**QUADS**/
	private Model background		= Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/menu_bg.png");
	public Quad frame_quad 			= new Quad(new Vector2f(0, 0), background);
	private Model pointer 			= Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/img.png");
	public Quad pointer_quad 		= new Quad(new Vector2f(0, 0), pointer);
	private Model blur_background 	= Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/bg_blur.png");
	public Quad blur_quad 			= new Quad(new Vector2f(0, 0), blur_background);
	public Quad letter2048 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font2048.png"));
	public Quad letter1024 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font1024.png"));
	public Quad letter512 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font512.png"));
	public Quad letter2048b 		= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font2048b.png"));
	public Quad letter1024b 		= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font1024b.png"));
	public Quad letter512b 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/font512b.png"));
	public Quad health	 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/health.png"));
	public Quad health2	 			= new Quad(new Vector2f(0, 0), Components.getLoader().create_quad(Components.basic_verts, Components.basic_tex_coords, Components.basic_indices, "res/tex/health2.png"));
	{ /**FOR THE TEXTURE ATLAS, MAYBE MOVE THIS IN THE FONT THING, IDK**/
		letter2048.getModel().setRows(16);
		letter1024.getModel().setRows(16);
		letter512.getModel().setRows(16);
		letter2048b.getModel().setRows(16);
		letter1024b.getModel().setRows(16);
		letter512b.getModel().setRows(16);
	}
	
	/**MODELS**/
	public Model floor_model 	= LoadOBJ.create_model("res/models/floor.obj", Components.getLoader(), "res/tex/ground.png");
	public Model basic_model2 	= LoadOBJ.create_model("res/models/good.obj",	Components.getLoader(), "res/tex/lol.png");
	public Model tree_model 	= LoadOBJ.create_model("res/models/tree.obj", Components.getLoader(), "res/tex/tree.png");
	public Model crop_ground 	= LoadOBJ.create_model("res/models/crop_ground.obj", Components.getLoader(), "res/tex/crop_ground2.png");
	public Model crop_seeds 	= LoadOBJ.create_model("res/models/crop_seeds.obj", Components.getLoader(), "res/tex/crop_seed.png");
	public Model crop_seeds2 	= LoadOBJ.create_model("res/models/crop_seeds2.obj", Components.getLoader(), "res/tex/crop_seed.png");
	public Model crop_seeds3 	= LoadOBJ.create_model("res/models/crop_seeds3.obj", Components.getLoader(), "res/tex/crop_seed.png");
	public Model wild_grass		= LoadOBJ.create_model("res/models/wild_grass.obj", Components.getLoader(), "res/tex/crop_seed.png");
	public Model stairs			= LoadOBJ.create_model("res/models/stairs.obj", Components.getLoader(), "res/tex/lol.png");

}
