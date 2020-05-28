package loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import contactlisteners.EntityType;
import dataStructure.EntityData;
import dataStructure.Mesh;
import dataStructure.OBJFileData;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import utils.Maths;

public class LoadCharacterInformation {

	private Map<String, String> data;
	private Creator creator;

	/**
	 * Bu s�n�f olu�turulacak entitynin sahip oldu�u bilgileri bir .txt tabanl�
	 * dosyadan okumaktad�r.
	 * 
	 * @param creator bir nesne olu�turulacaksa gereken s�n�ft�r.
	 */
	public LoadCharacterInformation(Creator creator) {
		data = new HashMap<String, String>();
		this.creator = creator;
	}

	/**
	 * ismin g�nderilen .txt tabanl� dosyan�n ismini al�p i�erisindeki bilgileri okuyarak bir entity olu�turan metottur.
	 * @param name		.txt tabanl� dosyan�n ismi
	 */
	
	public EntityData loadCharacter(String name) {
		BufferedReader reader = null ;
		EntityData entitydata = null ;
		try {
			InputStream in = Class.class.getResourceAsStream("/res/"+name+".txt") ;
			reader = new BufferedReader(new InputStreamReader(in)) ;
			
			String line = null ; 
			System.out.println(name + " dosyas� okunamaya haz�rland�...");
			entitydata = new EntityData(creator);
			System.out.println("entity data olu�turuldu...");
			String type = null ;

			while((line = reader.readLine()) != null) {
				
				if(line.startsWith("e ")) {
					parse(line) ;
					type = data.get("entity") ;
					if(type.equals("player"))
						entitydata.setEntityType(EntityType.BIT_PLAYER);
					else if(type.equals("zombie"))
						entitydata.setEntityType(EntityType.BIT_ZOMBIE);
					else if(type.equals("bullet"))
						entitydata.setEntityType(EntityType.BIT_BULLET);
					else if(type.equals("wall"))
						entitydata.setEntityType(EntityType.BIT_WALL);
					System.out.println("entity type : " + type);
				}else if(line.startsWith("t ")) {
					parse(line) ;
					Texture texture = creator.loadTexture(data.get("texture"), TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS, Integer.parseInt(data.get("x")), Integer.parseInt(data.get("y"))) ;
					entitydata.loadTexture(texture);
					System.out.println("entity dataya texture load edildi : " + data.get("texture") + " "+ Integer.parseInt(data.get("x")) + " "+ Integer.parseInt(data.get("y")));
					loadAnimationData(entitydata);
					System.out.println("animasyon data load edildi.");
				}else if(line.startsWith("r ")) {
					parse(line) ;
					Mesh mesh = creator.loadMesh(data.get("model")) ;
					entitydata.loadMesh(mesh);
					System.out.println("mesh model dosyas�n�n ismi entity dataya eklendi : " + data.get("model"));
				}else if(line.startsWith("b ")) {
					parse(line) ;
					System.out.println("-------------------------------------b");
					String position = data.get("position") ;
					System.out.println("karakterin pozisyon de�eri : " + position);
					String rotation = data.get("rotation") ;
					System.out.println("karakterin rotation de�eri : " + rotation);
					String scale = data.get("scale") ;
					System.out.println("karakterin scale de�eri : " + scale);
					String worldposition = data.get("worldposition") ;
					System.out.println("karakterin worldposition de�eri : " + worldposition);
					String bodytype = data.get("bodytype") ;
					BodyType bd = null ;
					if(bodytype.equals("dynamic")) {
						bd = BodyType.DYNAMIC ;
					}else if(bodytype.equals("static")) {
						bd = BodyType.STATIC ;
					}else if(bodytype.equals("kinematic")) {
						bd = BodyType.KINEMATIC ;
					}
					System.out.println("karakterin bodytype de�eri : " + bodytype);
					boolean fixedRotation = Boolean.valueOf(data.get("fixedrotation")) ;
					System.out.println("karakterin fixedRotation de�eri : " + fixedRotation);
					boolean isbullet = Boolean.valueOf(data.get("isbullet")) ;
					System.out.println("karakterin isbullet de�eri : " + isbullet);
					entitydata.loadBodyData(bd, loadVec2(position), Integer.parseInt(rotation), loadVec2(scale), Integer.parseInt(worldposition), fixedRotation, isbullet);
					System.out.println("body data y�klendi...");
				}else if(line.startsWith("f ")) {
					parse(line) ;

					String shapeType = data.get("shapetype") ;
					System.out.println("karakterin shapeType de�eri : " + shapeType);
					float restitution = Float.parseFloat(data.get("restitution")) ;
					System.out.println("karakterin restitution de�eri : " + restitution);
					float friction = Float.parseFloat(data.get("friction")) ;
					System.out.println("karakterin friction de�eri : " + friction);
					float density = Float.parseFloat(data.get("density")) ;
					System.out.println("karakterin density de�eri : " + density);
					boolean isSensor = Boolean.valueOf(data.get("issensor")) ;
					System.out.println("karakterin isSensor de�eri : " + isSensor);
					boolean isbullet = Boolean.valueOf(data.get("isbullet")) ;
					System.out.println("karakterin isbullet de�eri : " + isbullet);
					int radius = Integer.valueOf(data.get("radius")) ;
					System.out.println("karakterin radius de�eri : " + radius);
					String modelDataName = data.get("model") ;
					System.out.println("model ismi : " + modelDataName);
					
					OBJFileData modelData = creator.getLoader().getObjLoader().loadObjFile(modelDataName) ;
					
					
					if(shapeType.equals("polygon")) {
						//shape = entitydata.loadShapePolygon(Maths.arrayToVec2Array(modelData.getVertices()));
						entitydata.loadPhysicModelData(modelDataName,Maths.arrayToVec2Array(modelData.getVertices()), ShapeType.POLYGON, restitution, density, friction, radius, isSensor);
						System.out.println("entity nesnesi i�in polygon shape nesnesi eklendi...");
					}else {
						//shape = entitydata.loadShapeCircle(radius);
						entitydata.loadPhysicModelData(modelDataName,Maths.arrayToVec2Array(modelData.getVertices()), ShapeType.CIRCLE, restitution, density, friction, radius, isSensor);
						System.out.println("entitydata nesnesi i�in circle shape nesnesi eklendi...");
					}
					System.out.println(restitution + " " + density + " " + friction + " " + isSensor);
					
				}
					
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return entitydata ;
	}

	/**
	 * dosyadaki animasyon bilgilerini okuyan metot.
	 * 
	 * @param entitydata olu�turulacak entity s�n�f�n�n bilgilerini tutar.
	 */
	private void loadAnimationData(EntityData entitydata) {
		String idle = data.get("idle");
		System.out.println("idle : " + idle);
		if (!idle.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.idle, idle);
		}
		String walk_left = data.get("walk_left");
		System.out.println("walk_left : " + walk_left);
		if (!walk_left.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.walk_left, walk_left);
		}
		String walk_up = data.get("walk_up");
		System.out.println("walk_up : " + walk_up);
		if (!walk_up.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.walk_up, walk_up);
		}
		String walk_right = data.get("walk_right");
		System.out.println("walk_right : " + walk_right);
		if (!walk_right.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.walk_right, walk_right);
		}
		String walk_down = data.get("walk_down");
		System.out.println("walk_left : " + walk_down);
		if (!walk_down.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.walk_down, walk_down);
		}

		String attack = data.get("attack");
		System.out.println("attack : " + attack);
		if (!attack.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.attack, attack);
		}
		String crauch = data.get("crauch");
		System.out.println("crauch : " + crauch);
		if (!crauch.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.crauch, crauch);
		}
		String dead = data.get("dead");
		System.out.println("dead : " + dead);
		if (!dead.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.dead, dead);
		}
		String hurt = data.get("hurt");
		System.out.println("hurt : " + hurt);
		if (!hurt.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.hurt, hurt);
		}
	}

	/**
	 * string bir de�eri par�alayarak Vec2 s�n�f�na d���t�ren metot.
	 * 
	 * @param data i�erisinde x ve y de�eri bulunan string
	 * @return stringe g�re de�erleri d�zenlenmi� vec2 s�n�f�
	 */
	private Vec2 loadVec2(String data) {
		String[] parser = data.split("/");
		Vec2 dataV = new Vec2(Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));
		return dataV;

	}

	/**
	 * Her sat�rda entitynin sahip oldu�u �zellikler bir = ile g�sterilmi�tir. Bu
	 * metot sadece okunan sat�r i�in bu de�erleri al�p global olan data hashmapina
	 * kaydediyor.
	 * 
	 * @param reader dosyan�n okunmas�na yard�mc� olan java s�n�f�
	 * @return dosyan�n okunup okunmad���na dair bilgi g�nderilmesi.
	 */
	private boolean parse(String line) {
		data.clear();

		if (line == null) {
			return false;
		}

		String[] parser = line.split(" ");
		for (int i = 0; i < parser.length; i++) {
			String parseWord = parser[i];
			System.out.println("parse edilmi� kelime : " + parseWord);
			if (parseWord.contains("=")) {
				String[] parserEquals = parseWord.split("=");
				System.out.println("e�itlik parse de�erleri : " + parserEquals[0] + " " + parserEquals[1]);
				data.put(parserEquals[0], parserEquals[1]);
			}
		}

		return true;

	}
}
