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
	 * Bu sýnýf oluþturulacak entitynin sahip olduðu bilgileri bir .txt tabanlý
	 * dosyadan okumaktadýr.
	 * 
	 * @param creator bir nesne oluþturulacaksa gereken sýnýftýr.
	 */
	public LoadCharacterInformation(Creator creator) {
		data = new HashMap<String, String>();
		this.creator = creator;
	}

	/**
	 * ismin gönderilen .txt tabanlý dosyanýn ismini alýp içerisindeki bilgileri okuyarak bir entity oluþturan metottur.
	 * @param name		.txt tabanlý dosyanýn ismi
	 */
	
	public EntityData loadCharacter(String name) {
		BufferedReader reader = null ;
		EntityData entitydata = null ;
		try {
			InputStream in = Class.class.getResourceAsStream("/res/"+name+".txt") ;
			reader = new BufferedReader(new InputStreamReader(in)) ;
			
			String line = null ; 
			System.out.println(name + " dosyasý okunamaya hazýrlandý...");
			entitydata = new EntityData(creator);
			System.out.println("entity data oluþturuldu...");
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
					System.out.println("mesh model dosyasýnýn ismi entity dataya eklendi : " + data.get("model"));
				}else if(line.startsWith("b ")) {
					parse(line) ;
					System.out.println("-------------------------------------b");
					String position = data.get("position") ;
					System.out.println("karakterin pozisyon deðeri : " + position);
					String rotation = data.get("rotation") ;
					System.out.println("karakterin rotation deðeri : " + rotation);
					String scale = data.get("scale") ;
					System.out.println("karakterin scale deðeri : " + scale);
					String worldposition = data.get("worldposition") ;
					System.out.println("karakterin worldposition deðeri : " + worldposition);
					String bodytype = data.get("bodytype") ;
					BodyType bd = null ;
					if(bodytype.equals("dynamic")) {
						bd = BodyType.DYNAMIC ;
					}else if(bodytype.equals("static")) {
						bd = BodyType.STATIC ;
					}else if(bodytype.equals("kinematic")) {
						bd = BodyType.KINEMATIC ;
					}
					System.out.println("karakterin bodytype deðeri : " + bodytype);
					boolean fixedRotation = Boolean.valueOf(data.get("fixedrotation")) ;
					System.out.println("karakterin fixedRotation deðeri : " + fixedRotation);
					boolean isbullet = Boolean.valueOf(data.get("isbullet")) ;
					System.out.println("karakterin isbullet deðeri : " + isbullet);
					entitydata.loadBodyData(bd, loadVec2(position), Integer.parseInt(rotation), loadVec2(scale), Integer.parseInt(worldposition), fixedRotation, isbullet);
					System.out.println("body data yüklendi...");
				}else if(line.startsWith("f ")) {
					parse(line) ;

					String shapeType = data.get("shapetype") ;
					System.out.println("karakterin shapeType deðeri : " + shapeType);
					float restitution = Float.parseFloat(data.get("restitution")) ;
					System.out.println("karakterin restitution deðeri : " + restitution);
					float friction = Float.parseFloat(data.get("friction")) ;
					System.out.println("karakterin friction deðeri : " + friction);
					float density = Float.parseFloat(data.get("density")) ;
					System.out.println("karakterin density deðeri : " + density);
					boolean isSensor = Boolean.valueOf(data.get("issensor")) ;
					System.out.println("karakterin isSensor deðeri : " + isSensor);
					boolean isbullet = Boolean.valueOf(data.get("isbullet")) ;
					System.out.println("karakterin isbullet deðeri : " + isbullet);
					int radius = Integer.valueOf(data.get("radius")) ;
					System.out.println("karakterin radius deðeri : " + radius);
					String modelDataName = data.get("model") ;
					System.out.println("model ismi : " + modelDataName);
					
					OBJFileData modelData = creator.getLoader().getObjLoader().loadObjFile(modelDataName) ;
					
					
					if(shapeType.equals("polygon")) {
						//shape = entitydata.loadShapePolygon(Maths.arrayToVec2Array(modelData.getVertices()));
						entitydata.loadPhysicModelData(modelDataName,Maths.arrayToVec2Array(modelData.getVertices()), ShapeType.POLYGON, restitution, density, friction, radius, isSensor);
						System.out.println("entity nesnesi için polygon shape nesnesi eklendi...");
					}else {
						//shape = entitydata.loadShapeCircle(radius);
						entitydata.loadPhysicModelData(modelDataName,Maths.arrayToVec2Array(modelData.getVertices()), ShapeType.CIRCLE, restitution, density, friction, radius, isSensor);
						System.out.println("entitydata nesnesi için circle shape nesnesi eklendi...");
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
	 * @param entitydata oluþturulacak entity sýnýfýnýn bilgilerini tutar.
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
	 * string bir deðeri parçalayarak Vec2 sýnýfýna döüþtüren metot.
	 * 
	 * @param data içerisinde x ve y deðeri bulunan string
	 * @return stringe göre deðerleri düzenlenmiþ vec2 sýnýfý
	 */
	private Vec2 loadVec2(String data) {
		String[] parser = data.split("/");
		Vec2 dataV = new Vec2(Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));
		return dataV;

	}

	/**
	 * Her satýrda entitynin sahip olduðu özellikler bir = ile gösterilmiþtir. Bu
	 * metot sadece okunan satýr için bu deðerleri alýp global olan data hashmapina
	 * kaydediyor.
	 * 
	 * @param reader dosyanýn okunmasýna yardýmcý olan java sýnýfý
	 * @return dosyanýn okunup okunmadýðýna dair bilgi gönderilmesi.
	 */
	private boolean parse(String line) {
		data.clear();

		if (line == null) {
			return false;
		}

		String[] parser = line.split(" ");
		for (int i = 0; i < parser.length; i++) {
			String parseWord = parser[i];
			System.out.println("parse edilmiþ kelime : " + parseWord);
			if (parseWord.contains("=")) {
				String[] parserEquals = parseWord.split("=");
				System.out.println("eþitlik parse deðerleri : " + parserEquals[0] + " " + parserEquals[1]);
				data.put(parserEquals[0], parserEquals[1]);
			}
		}

		return true;

	}
}
