package renderer;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import dataStructure.Texture;
import entities.Entity;
import entities.EntityRenderer;
import font.FontRenderer;
import font.Text;
import gui.GUI;
import gui.GUIRenderer;
import loader.Creator;
import loader.Loader;
import utils.Camera;
import utils.MouseOrtho;

public class Renderer implements Renderable{
	/**
	 *  T�m renderer s�n�flar�n� bir arada belli s�ra ile update ve render i�lemlerinin ger�ekle�mesini sa�layan s�n�ft�r.
	 *  Di�er t�m renderer s�n�flar�na bu s�n�f ile eri�im sa�lan�r.
	 */

	private EntityRenderer entityRenderer ;
	private FontRenderer fontRenderer ;
	private GUIRenderer guiRenderer ;
	private Camera camera ;
	private World world ;
	private MouseOrtho mouse ;
	private Loader loader ;
	
	public Renderer(Loader loader, MouseOrtho mouse, Camera camera, World world) {
		this.camera = camera ;
		this.world = world ;
		this.loader = loader ;
		this.mouse = mouse ;
	}

	@Override
	public void init() {
		entityRenderer = new EntityRenderer(camera, world);
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
