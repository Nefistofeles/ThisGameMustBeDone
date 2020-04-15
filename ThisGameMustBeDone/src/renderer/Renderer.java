package renderer;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import dataStructure.Texture;
import entities.Entity;
import entities.EntityRenderer;
import font.FontRenderer;
import font.Text;
import gameEntity.Camera;
import gameEntity.MouseOrtho;
import gui.GUI;
import gui.GUIRenderer;
import loader.Loader;

public class Renderer implements Renderable{

	private Camera camera ;
	private World world ;
	private Loader loader ;
	private EntityRenderer entityRenderer ;
	private FontRenderer fontRenderer ;
	private GUIRenderer guiRenderer ;
	private MouseOrtho mouse ;
	
	public Renderer(Loader loader, Camera camera, World world, MouseOrtho mouse) {
		this.camera = camera ;
		this.world = world ;
		this.loader = loader ;
		this.mouse = mouse ;
	}

	@Override
	public void init() {
		entityRenderer = new EntityRenderer(camera);
		entityRenderer.init();
		fontRenderer = new FontRenderer();
		fontRenderer.init();
		guiRenderer = new GUIRenderer(loader, mouse);
		guiRenderer.init();
	}

	@Override
	public void update() {
		entityRenderer.update();
		guiRenderer.update();
		fontRenderer.update();
	}

	@Override
	public void render() {
		entityRenderer.render();
		guiRenderer.render();
		fontRenderer.render();
	}

	@Override
	public void clean() {
		entityRenderer.clean();
		fontRenderer.clean();
		guiRenderer.clean();
		
	}
	public void addEntity(Entity entity) {
		entityRenderer.addEntity(entity);
		
	}
	public void addText(Text text, Texture texture) {
		fontRenderer.addText(text, texture);
	}
	public void addGUI(GUI g) {
		guiRenderer.addGUI(g);
	}

	
}
