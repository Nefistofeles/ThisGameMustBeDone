
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
import entities.Background;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import entities.Wall;
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

public class GameManager implements Renderable {

	private Text text;
	private Text timeText;
	private Text levelText;
	private Text scoreText;
	private Creator creator;
	private Player player;
	private FontMeshLoader fmLoader;
	private float time;
	private float worldTime;
	private int level;

	public GameManager(Creator creator) {
		this.creator = creator;
		time = 0;
		worldTime = 0;
		level = 1;
	}

	/**
	 * Oyun ba�lad���nda y�klenilmesi istenilen ��eler burada ilk y�klenir.
	 */
	@Override
	public void init() {
		EntityContactListener contactListener = new EntityContactListener();
		creator.getWorld().setContactListener(contactListener);
		creator.getRenderer().init();

		Texture zombie = creator.loadTexture("zombieatlas", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		creator.getRenderer().createhashList(zombie);
		Texture bullet = creator.loadTexture("bullet", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		creator.getRenderer().createhashList(bullet);
		Texture character = creator.loadTexture("character_tile", TextureLoader.TextureNearest,
				TextureLoader.DEFAULT_BIAS);
		creator.getRenderer().createhashList(character);
		Texture wall = creator.loadTexture("wall", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		creator.getRenderer().createhashList(wall);
		Texture harita = creator.loadTexture("harita1", TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS);
		creator.getRenderer().createhashList(harita);

		Wall wall1 = (Wall) creator.loadEntity("wall", new Vec2(0, 100), new Vec2(105, 5), EntityType.BIT_WALL,
				(short) (EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER));
		wall1.setxDown(true);
		Wall wall2 = (Wall) creator.loadEntity("wall", new Vec2(0, -100), new Vec2(105, 5), EntityType.BIT_WALL,
				(short) (EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER));
		wall2.setxDown(true);
		Wall wall3 = (Wall) creator.loadEntity("wall", new Vec2(100, 0), new Vec2(5, 105), EntityType.BIT_WALL,
				(short) (EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER));
		wall3.setxDown(false);
		Wall wall4 = (Wall) creator.loadEntity("wall", new Vec2(-100, 0), new Vec2(5, 105), EntityType.BIT_WALL,
				(short) (EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER));
		wall4.setxDown(false);

		player = (Player) creator.loadEntity("character_tile", null, null, EntityType.BIT_PLAYER,
				EntityType.BIT_ZOMBIE);

		Entity background = new Background(creator.loadMesh("untitled"),
				creator.loadTexture("harita1", TextureLoader.TextureLinear, TextureLoader.DEFAULT_BIAS), new Vec2(0, 0),
				0, new Vec2(100, 100), -5, creator);
		creator.getRenderer().addEntity(background);

		fmLoader = new FontMeshLoader("arial", creator.getLoader());
		text = new Text("Health : 100", new Vec2(-70, 58), 0, new Vec2(2f, 2.5f));
		fmLoader.loadMeshforFont(text);
		creator.getRenderer().addText(text, fmLoader.getTexture());

		timeText = new Text("Time : 100", new Vec2(-70, 54), 0, new Vec2(2f, 2.5f));
		fmLoader.loadMeshforFont(timeText);
		creator.getRenderer().addText(timeText, fmLoader.getTexture());

		levelText = new Text("Level : " + level, new Vec2(-70, 50), 0, new Vec2(2f, 2.5f));
		fmLoader.loadMeshforFont(levelText);
		creator.getRenderer().addText(levelText, fmLoader.getTexture());

		scoreText = new Text("Score : " + player.getScore(), new Vec2(-70, 46), 0, new Vec2(2f, 2.5f));
		fmLoader.loadMeshforFont(scoreText);
		creator.getRenderer().addText(scoreText, fmLoader.getTexture());

		/*
		 * 
		 * creator.loadEntity("zombie",null,null) ; creator.loadEntity("bullet",new
		 * Vec2(20,20),null) ;
		 */

		/*
		 * Texture texture2 = creator.loadTexture("button", TextureLoader.TextureLinear,
		 * 0); GUI g = new GUI(texture2, new Vec2(0,0), new Vec2(15,15));
		 * creator.getRenderer().addGUI(g);
		 * 
		 * 
		 * 
		 */
	}

	/**
	 * Oyun i�i �izilen ��eler belli �zelliklerine g�re(�rn : d�nya konumlar�
	 * durmadan g�ncelleniyor.) update edilir.
	 */
	@Override
	public void update() {
		worldTime += DisplayManager.getFrameTime();
		creator.getRenderer().update();
		text.setText("Health : " + player.getHealth(), fmLoader);
		if(!player.isDead())
			timeText.setText("Time : " + (int) worldTime, fmLoader);
		time += DisplayManager.getFrameTime();
		if (time >= 5) {
			if (level <= 4) {
				level++;
				time = 0;
				levelText.setText("Level : " + level, fmLoader);
			}

		}
		if (!player.isDead()) {
			if (time > 5 / level) {
				Enemy entity = (Enemy) creator.loadEntity("zombie",
						new Vec2(Maths.random(-50, 50), Maths.random(-50, 50)), new Vec2(7, 7), EntityType.BIT_ZOMBIE,
						(short) (EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER));
				entity.setFollow(player);
				time = 0;
			}
		}

		if (!player.isDead()) {
			scoreText.setText("Score : " + player.getScore(), fmLoader);
		}

	}

	/**
	 * �izilmesi istenilen ��eler {@code Renderer} s�n�f�ndaki renderer metodunu
	 * dolayl� yoldan �a��rarak �izim i�leminin yap�lmas�na olanak sa�layan
	 * metottur.
	 */
	@Override
	public void render() {
		creator.getRenderer().render();

	}

	/**
	 * Program sonland���nda silinmesi gereken objelerin silinmesini sa�layan
	 * metottur. Genellikle opengl ile olu�turulan objelerin silinmesi gerekir.
	 */
	@Override
	public void clean() {
		creator.getRenderer().clean();
		creator.getLoader().clean();
	}

}
