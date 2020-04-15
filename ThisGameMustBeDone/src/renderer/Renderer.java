package renderer;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import dataStructure.Texture;
import entities.Entity;
import font.Text;
import gameEntity.Camera;

public class Renderer implements Renderable{

	private Camera camera ;
	private World world ;
	private EntityRenderer entityRenderer ;
	private FontRenderer fontRenderer ;
	
	public Renderer(Camera camera, World world) {
		this.camera = camera ;
		this.world = world ;
	}

	@Override
	public void init() {
		entityRenderer = new EntityRenderer(camera);
		entityRenderer.init();
		fontRenderer = new FontRenderer();
		fontRenderer.init();
	}

	@Override
	public void update() {
		entityRenderer.update();
		fontRenderer.update();
	}

	@Override
	public void render() {
		entityRenderer.render();
		fontRenderer.render();
		
	}

	@Override
	public void clean() {
		entityRenderer.clean();
		fontRenderer.clean();
		
	}
	public void addEntity(Entity entity) {
		entityRenderer.addEntity(entity);
		
	}
	public void addText(Text text, Texture texture) {
		fontRenderer.addText(text, texture);
	}
	

	
}
