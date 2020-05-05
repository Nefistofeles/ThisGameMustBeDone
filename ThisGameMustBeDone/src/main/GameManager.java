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
import utils.MouseOrtho;

public class GameManager implements Renderable{
	
	private Entity entity ;
	private Text text ;
	private Creator creator ;
	
	public GameManager(Creator creator) {
		this.creator = creator ;
	}

	/**
	 * Oyun ba�lad���nda y�klenilmesi istenilen ��eler burada ilk y�klenir.
	 */
	@Override
	public void init() {
		creator.getRenderer().init(); 

		LoadCharacterInformation lc = new LoadCharacterInformation(creator) ;
		lc.loadCharacter("character");
		
		Texture texture2 = creator.loadTexture("button", TextureLoader.TextureLinear, 0);
		GUI g = new GUI(texture2, new Vec2(0,0), new Vec2(15,15));
		creator.getRenderer().addGUI(g);
		
		
		FontMeshLoader fmLoader = new FontMeshLoader("arial", creator.getLoader());
		text = new Text("Tarkan", new Vec2(0,0), 0, new Vec2(5,5));
		fmLoader.loadMeshforFont(text);
		creator.getRenderer().addText(text, fmLoader.getTexture());
	}
	/**
	 * Oyun i�i �izilen ��eler belli �zelliklerine g�re(�rn : d�nya konumlar� durmadan g�ncelleniyor.) update edilir.
	 */
	@Override
	public void update() {
		creator.getRenderer().update();
		text.setPosition(creator.getMouse().getMousePos2());
	}
	/**
	 * �izilmesi istenilen ��eler {@code Renderer} s�n�f�ndaki renderer metodunu dolayl� yoldan �a��rarak �izim i�leminin yap�lmas�na olanak sa�layan metottur.
	 */
	@Override
	public void render() {
		creator.getRenderer().render();
		
	}
	/**
	 * Program sonland���nda silinmesi gereken objelerin silinmesini sa�layan metottur. Genellikle opengl ile olu�turulan objelerin silinmesi gerekir.
	 */
	@Override
	public void clean() {
		creator.getRenderer().clean();
		creator.getLoader().clean();
	}

}
