package loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import dataStructure.Mesh;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import fileParser.OBJFileLoader;
import renderer.Renderer;
import utils.Camera;
import utils.MouseOrtho;

public class Creator {

	private Loader loader;
	private World world;
	private MouseOrtho mouse;
	private Camera camera;
	private PhysicsDataLoader physicsLoader;
	private Renderer renderer ;
	private OBJFileLoader oLoader ;
	private Map<String, Mesh> meshes ;
	private Map<String,Texture> textures ;

	public Creator() {
		
		meshes = new HashMap<String,Mesh>();
		textures = new HashMap<String,Texture>();
		loader = new Loader();
		world = new World(new Vec2(0, 0));
		camera = new Camera();
		mouse = new MouseOrtho(camera);
		oLoader = new OBJFileLoader();
		physicsLoader = new PhysicsDataLoader(world);
		renderer = new Renderer(loader, mouse, camera, world);
	}
	
	public void addMesh(String name, Mesh mesh) {
		Mesh meshdata = meshes.get(name) ;
		if(meshdata == null) {
			meshes.put(name, meshdata) ;
		}else {
			System.out.println("this mesh already exist");
		}
		
	}
	public void addTexture(String name, Texture texture) {
		Texture texturedata = textures.get(name) ;
		if(texturedata == null) {
			textures.put(name, texturedata) ;
		}else {
			System.out.println("this texture already exist");
		}
		
	}
	public Texture getTexture(String name) {
		return textures.get(name) ;
		
	}
	public Mesh getMesh(String name) {
		return meshes.get(name) ;
		
	}
	
	public Mesh loadMeshFromFile(String name) {
		Mesh mesh = oLoader.loadObjFile("untitled", loader);
		addMesh(name, mesh);
		return mesh ;
	}
	public Texture loadTextureFromFile(String name, int nearest, float bias) {
		Texture texture = loader.getTextureLoader().loadTexture(name, nearest, bias) ;
		addTexture(name, texture);
		return texture ;
	}
	
	public Mesh getMeshClone(String name) {
		return meshes.get(name).getClone() ;
	}
	public Texture getTextureClone(String name) {
		return textures.get(name).getClone() ;
	}
	public Renderer getRenderer() {
		return renderer;
	}
	public Loader getLoader() {
		return loader;
	}

	public MouseOrtho getMouse() {
		return mouse;
	}

	public World getWorld() {
		return world;
	}
	
	public Camera getCamera() {
		return camera;
	}
	public PhysicsDataLoader getPhysicsLoader() {
		return physicsLoader;
	}
}
