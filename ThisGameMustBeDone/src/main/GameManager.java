
package main;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Mouse;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import contactlisteners.EntityContactListener;
import contactlisteners.EntityType;
import dataStructure.Mesh;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import font.FontLoader;
import font.Text;
import gui.GUI;
import loader.Creator;
import loader.FontMeshLoader;
import loader.LoadCharacterInformation;
import loader.Loader;
import loader.MeshLoader;
import loader.OBJFileLoader;
import loader.PhysicsDataLoader;
import loader.TextureLoader;
import renderer.Renderable;
import renderer.Renderer;
import utils.Camera;
import utils.Coordinates;
import utils.DisplayManager;
import utils.Maths;
import utils.MouseOrtho;

public class GameManager implements Renderable{
	
	private Text text ;
	private Creator creator ;
	private Entity entity ;
	private Entity dayi ;
	
	public GameManager(Creator creator) {
		this.creator = creator ;
	}

	/**
	 * Oyun baþladýðýnda yüklenilmesi istenilen öðeler burada ilk yüklenir.
	 */
	@Override
	public void init() {
		EntityContactListener contactListener = new EntityContactListener();
		creator.getWorld().setContactListener(contactListener);
		creator.getRenderer().init(); 
		
		Texture zombie = creator.loadTexture("zombie", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS) ;
		creator.getRenderer().createhashList(zombie);
		Texture bullet = creator.loadTexture("bullet", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS) ;
		creator.getRenderer().createhashList(bullet);
		Texture character = creator.loadTexture("character", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS) ;
		creator.getRenderer().createhashList(character);
		
		
		creator.loadEntity("character",null,null, EntityType.BIT_PLAYER, EntityType.BIT_ZOMBIE) ;
		/*creator.loadEntity("zombie",null,null) ;
		creator.loadEntity("bullet",new Vec2(20,20),null) ;*/

		/*Texture texture2 = creator.loadTexture("button", TextureLoader.TextureLinear, 0);
		GUI g = new GUI(texture2, new Vec2(0,0), new Vec2(15,15));
		creator.getRenderer().addGUI(g);
		
		
		FontMeshLoader fmLoader = new FontMeshLoader("arial", creator.getLoader());
		text = new Text("Tarkan", new Vec2(0,0), 0, new Vec2(5,5));
		fmLoader.loadMeshforFont(text);
		creator.getRenderer().addText(text, fmLoader.getTexture());*/
	}
	/**
	 * Oyun içi çizilen öðeler belli özelliklerine göre(örn : dünya konumlarý durmadan güncelleniyor.) update edilir.
	 */
	@Override
	public void update() {
		creator.getRenderer().update();
		//text.setPosition(creator.getMouse().getMousePos2());
		
		
		
	}
	/**
	 * Çizilmesi istenilen öðeler {@code Renderer} sýnýfýndaki renderer metodunu dolaylý yoldan çaðýrarak çizim iþleminin yapýlmasýna olanak saðlayan metottur.
	 */
	@Override
	public void render() {
		creator.getRenderer().render();
		
	}
	/**
	 * Program sonlandýðýnda silinmesi gereken objelerin silinmesini saðlayan metottur. Genellikle opengl ile oluþturulan objelerin silinmesi gerekir.
	 */
	@Override
	public void clean() {
		creator.getRenderer().clean();
		creator.getLoader().clean();
	}

}
