package main;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import dataStructure.Mesh;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import fileParser.OBJFileLoader;
import font.FontLoader;
import font.Text;
import gui.GUI;
import loader.Creator;
import loader.FontMeshLoader;
import loader.Loader;
import loader.MeshLoader;
import loader.PhysicsDataLoader;
import loader.TextureLoader;
import renderer.Renderable;
import renderer.Renderer;
import utils.Camera;
import utils.Coordinates;
import utils.MouseOrtho;

public class GameManager implements Renderable{
	
	private Entity entity ;
	private Text text ;
	private Creator creator ;
	
	public GameManager(Creator creator) {
		this.creator = creator ;
	}

	@Override
	public void init() {
		
		creator.getRenderer().init(); 
		
		Texture texture = creator.loadTextureFromFile("player", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		texture.setTextureRowColumn(6, 7);
		creator.addTexture("player", texture);
		Mesh mesh = creator.loadMeshFromFile("untitled") ;
		
		Texture texture2 = creator.loadTextureFromFile("button", TextureLoader.TextureLinear, 0);
		
		entity = new Player(mesh,texture, new Vec2(0,0), 0, new Vec2(5,5),0, creator);
		creator.getPhysicsLoader().createPhysics(entity, Coordinates.getVertexVector(entity.getScale()), entity.getScale(), BodyType.DYNAMIC, false, ShapeType.POLYGON, 0.1f, 0.01f, 1, false, false);
		creator.getRenderer().addEntity(entity);
	
		Entity entity2 = new Zombie(mesh,texture.getClone(), new Vec2(0,20), 0, new Vec2(5,5),0);
		creator.getPhysicsLoader().createPhysics(entity2, Coordinates.getVertexVector(entity2.getScale()), entity2.getScale(), BodyType.DYNAMIC, false, ShapeType.POLYGON, 0.1f, 0.01f, 1, false, false);
		creator.getRenderer().addEntity(entity2);
		
		GUI g = new GUI(texture2, new Vec2(0,0), new Vec2(15,15));
		creator.getRenderer().addGUI(g);
		
		
		FontMeshLoader fmLoader = new FontMeshLoader("arial", creator.getLoader());
		text = new Text("Tarkan", new Vec2(0,0), 0, new Vec2(5,5));
		fmLoader.loadMeshforFont(text);
		creator.getRenderer().addText(text, fmLoader.getTexture());
	}

	@Override
	public void update() {
		creator.getRenderer().update();
		text.setPosition(creator.getMouse().getMousePos2());
	}

	@Override
	public void render() {
		creator.getRenderer().render();
		
	}

	@Override
	public void clean() {
		creator.getRenderer().clean();
		creator.getLoader().clean();
	}

}
