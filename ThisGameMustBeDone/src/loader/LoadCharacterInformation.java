package loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.EntityData;
import dataStructure.Mesh;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import entities.Zombie;

public class LoadCharacterInformation {
	
	private Map<String, String> data ;
	private Creator creator ;
	/**
	 * Bu s�n�f olu�turulacak entitynin sahip oldu�u bilgileri bir .txt tabanl� dosyadan okumaktad�r.
	 * 
	 * @param creator bir nesne olu�turulacaksa gereken s�n�ft�r.
	 */
	public LoadCharacterInformation(Creator creator) {
		data = new HashMap<String, String>();	
		this.creator = creator ;
	}
	/**
	 * ismin g�nderilen .txt tabanl� dosyan�n ismini al�p i�erisindeki bilgileri okuyarak bir entity olu�turan metottur.
	 * @param name		.txt tabanl� dosyan�n ismi
	 */
	public void loadCharacter(String name) {
		BufferedReader reader = null ;
		try {
			InputStream in = Class.class.getResourceAsStream("/res/"+name+".txt") ;
			reader = new BufferedReader(new InputStreamReader(in)) ;
			System.out.println(name + " dosyas� okunamaya haz�rland�...");
			EntityData entitydata = new EntityData(creator);
			System.out.println("entity data olu�turuldu...");
			parse(reader) ;
			String type = data.get("entity") ;
			parse(reader) ;
			entitydata.loadTexture(data.get("texture"),Integer.parseInt(data.get("x")), Integer.parseInt(data.get("y"))) ;
			System.out.println("entity dataya texture load edildi : " + data.get("texture") + " "+ Integer.parseInt(data.get("x")) + " "+ Integer.parseInt(data.get("y")));
			loadAnimationData(entitydata);
			System.out.println("animasyon data load edildi.");
			parse(reader) ;
			entitydata.loadMesh(data.get("model"));
			System.out.println("mesh model dosyas�n�n ismi entity dataya eklendi : " + data.get("model"));
			parse(reader) ;
			
			String position = data.get("position") ;
			System.out.println("karakterin pozisyon de�eri : " + position);
			String rotation = data.get("rotation") ;
			System.out.println("karakterin rotation de�eri : " + rotation);
			String scale = data.get("scale") ;
			System.out.println("karakterin scale de�eri : " + scale);
			String worldposition = data.get("worldposition") ;
			System.out.println("karakterin worldposition de�eri : " + worldposition);
			String bodytype = data.get("bodytype") ;
			System.out.println("karakterin bodytype de�eri : " + bodytype);
			boolean fixedRotation = Boolean.valueOf(data.get("fixedrotation")) ;
			System.out.println("karakterin fixedRotation de�eri : " + fixedRotation);
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
			
			entitydata.loadEntity(type, loadVec2(position), Integer.parseInt(rotation), loadVec2(scale), Integer.parseInt(worldposition));
			System.out.println("entity nesnesi olu�turuldu...");
			entitydata.loadBody(bodytype, fixedRotation, isbullet);
			System.out.println("entitydata nesnesine body eklendi");
			if(shapeType == "polygon") {
				entitydata.loadShapePolygon();
				System.out.println("entity nesnesi i�in polygon shape nesnesi eklendi...");
			}else {
				entitydata.loadShapeCircle(radius);
				System.out.println("entitydata nesnesi i�in circle shape nesnesi eklendi...");
			}
			System.out.println(restitution + " " + density + " " + friction + " " + isSensor);
			entitydata.loadFixture(0.1f, 0.1f, 0.1f, false);
			System.out.println("entitydata nesnesine fixture nesnesi eklendi");
			entitydata.loadEntity();
			System.out.println("entity nesnesi ekrana �izdirilmek i�in renderera eklendi...");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * dosyadaki animasyon bilgilerini okuyan metot.
	 * @param entitydata	olu�turulacak entity s�n�f�n�n bilgilerini tutar.
	 */
	private void loadAnimationData(EntityData entitydata) {
		String idle = data.get("idle") ;
		System.out.println("idle : " + idle);
		if(!idle.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.idle, idle);
		}
		String walk = data.get("walk") ;
		System.out.println("walk : " + walk);
		if(!walk.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.walk, walk);
		}
		String run = data.get("run") ;
		System.out.println("run : " + run);
		if(!run.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.run, run);
		}
		String attack = data.get("attack") ;
		System.out.println("attack : " + attack);
		if(!attack.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.attack, attack);
		}
		String crauch = data.get("crauch") ;
		System.out.println("crauch : " + crauch);
		if(!crauch.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.crauch, crauch);
		}
		String dead = data.get("dead") ;
		System.out.println("dead : " + dead);
		if(!dead.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.dead, dead);
		}
		String hurt = data.get("hurt") ;
		System.out.println("hurt : " + hurt);
		if(!hurt.equals("0")) {
			entitydata.loadAnimationData(AnimationEnum.hurt, hurt);
		}
	}

	
	/**
	 * string bir de�eri par�alayarak Vec2 s�n�f�na d���t�ren metot.
	 * @param data	i�erisinde x ve y de�eri bulunan string
	 * @return		stringe g�re de�erleri d�zenlenmi� vec2 s�n�f�
	 */
	private Vec2 loadVec2(String data) {
		String[] parser = data.split("/") ;
		Vec2 dataV = new Vec2(Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));
		return dataV ;
		
	}
	/**
	 * Her sat�rda entitynin sahip oldu�u �zellikler bir = ile g�sterilmi�tir. Bu metot sadece okunan sat�r i�in bu de�erleri al�p global olan data hashmapina kaydediyor.
	 * @param reader	dosyan�n okunmas�na yard�mc� olan java s�n�f�
	 * @return			dosyan�n okunup okunmad���na dair bilgi g�nderilmesi.
	 */
	private boolean parse(BufferedReader reader) {
		data.clear();
		String line = null ;
		try {
			line = reader.readLine() ;
			System.out.println("sat�r okundu : " + line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(line==null) {
			return false ;
		}
		
		String[] parser = line.split(" ") ;
		for(int i = 0 ; i < parser.length ; i++) {
			String parseWord = parser[i] ;
			System.out.println("parse edilmi� kelime : " + parseWord);
			if(parseWord.contains("=")) {
				String[] parserEquals = parseWord.split("=") ;
				System.out.println("e�itlik parse de�erleri : " + parserEquals[0] + " " + parserEquals[1]);
				data.put(parserEquals[0], parserEquals[1]) ;
			}
		}
		
		return true ;
		
	}
}
