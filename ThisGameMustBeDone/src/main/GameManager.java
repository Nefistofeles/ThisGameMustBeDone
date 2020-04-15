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
import gameEntity.Camera;
import gameEntity.MouseOrtho;
import loader.FontMeshLoader;
import loader.Loader;
import loader.MeshLoader;
import loader.PhysicsDataLoader;
import loader.TextureLoader;
import renderer.Renderable;
import renderer.Renderer;
import utils.Coordinates;

public class GameManager implements Renderable{
	
	//Düzenlenecek

	private World world ;
	private Renderer renderer ;
	private Loader loader ;
	private MouseOrtho mouse ;
	private Camera camera ;
	private Entity entity ;
	private PhysicsDataLoader pLoader ;
	private Text text ;
	
	public GameManager(World world) {
		this.world = world ;
	}

	@Override
	public void init() {
		loader = new Loader();
		camera = new Camera();
		renderer = new Renderer(camera, world);
		renderer.init(); 
		mouse = new MouseOrtho(camera);
		pLoader = new PhysicsDataLoader(world) ;
		
		Texture texture = loader.getTextureLoader().loadTexture("player", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		texture.setTextureRowColumn(6, 7);
		OBJFileLoader oLoader = new OBJFileLoader();
		Mesh mesh = oLoader.loadObjFile("untitled", loader);
		


		entity = new Player(mesh,texture, new Vec2(0,0), 0, new Vec2(5,5),0);
		pLoader.createPhysics(entity, Coordinates.getVertexVector(entity.getScale()), entity.getScale(), BodyType.DYNAMIC, true, ShapeType.POLYGON, 0.1f, 0.1f, 0.1f);
		renderer.addEntity(entity);
	
		Entity entity2 = new Zombie(mesh,texture, new Vec2(0,20), 0, new Vec2(5,5),0);
		pLoader.createPhysics(entity2, Coordinates.getVertexVector(entity2.getScale()), entity2.getScale(), BodyType.DYNAMIC, true, ShapeType.POLYGON, 0.01f, 0.001f, 0.01f);
		renderer.addEntity(entity2);

		FontMeshLoader fmLoader = new FontMeshLoader("arial", loader);
		text = new Text("Tarkan", new Vec2(0,0), 0, new Vec2(5,5), 0);
		fmLoader.loadMeshforFont(text);
		renderer.addText(text, fmLoader.getTexture());
		
	}

	@Override
	public void update() {
		renderer.update();
		text.setPosition(mouse.getMousePos2());
	}

	@Override
	public void render() {
		renderer.render();
		
	}

	@Override
	public void clean() {
		renderer.clean();
		loader.clean();
	}

}
