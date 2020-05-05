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
	 * Bu sýnýf oluþturulacak entitynin sahip olduðu bilgileri bir .txt tabanlý dosyadan okumaktadýr.
	 * 
	 * @param creator bir nesne oluþturulacaksa gereken sýnýftýr.
	 */
	public LoadCharacterInformation(Creator creator) {
		data = new HashMap<String, String>();	
		this.creator = creator ;
	}
	/**
	 * ismin gönderilen .txt tabanlý dosyanýn ismini alýp içerisindeki bilgileri okuyarak bir entity oluþturan metottur.
	 * @param name		.txt tabanlý dosyanýn ismi
	 */
	public void loadCharacter(String name) {
		BufferedReader reader = null ;
		try {
			InputStream in = Class.class.getResourceAsStream("/res/"+name+".txt") ;
			reader = new BufferedReader(new InputStreamReader(in)) ;
			System.out.println(name + " dosyasý okunamaya hazýrlandý...");
			EntityData entitydata = new EntityData(creator);
			System.out.println("entity data oluþturuldu...");
			parse(reader) ;
			String type = data.get("entity") ;
			parse(reader) ;
			entitydata.loadTexture(data.get("texture"),Integer.parseInt(data.get("x")), Integer.parseInt(data.get("y"))) ;
			System.out.println("entity dataya texture load edildi : " + data.get("texture") + " "+ Integer.parseInt(data.get("x")) + " "+ Integer.parseInt(data.get("y")));
			loadAnimationData(entitydata);
			System.out.println("animasyon data load edildi.");
			parse(reader) ;
			entitydata.loadMesh(data.get("model"));
			System.out.println("mesh model dosyasýnýn ismi entity dataya eklendi : " + data.get("model"));
			parse(reader) ;
			
			String position = data.get("position") ;
			System.out.println("karakterin pozisyon deðeri : " + position);
			String rotation = data.get("rotation") ;
			System.out.println("karakterin rotation deðeri : " + rotation);
			String scale = data.get("scale") ;
			System.out.println("karakterin scale deðeri : " + scale);
			String worldposition = data.get("worldposition") ;
			System.out.println("karakterin worldposition deðeri : " + worldposition);
			String bodytype = data.get("bodytype") ;
			System.out.println("karakterin bodytype deðeri : " + bodytype);
			boolean fixedRotation = Boolean.valueOf(data.get("fixedrotation")) ;
			System.out.println("karakterin fixedRotation deðeri : " + fixedRotation);
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
			
			entitydata.loadEntity(type, loadVec2(position), Integer.parseInt(rotation), loadVec2(scale), Integer.parseInt(worldposition));
			System.out.println("entity nesnesi oluþturuldu...");
			entitydata.loadBody(bodytype, fixedRotation, isbullet);
			System.out.println("entitydata nesnesine body eklendi");
			if(shapeType == "polygon") {
				entitydata.loadShapePolygon();
				System.out.println("entity nesnesi için polygon shape nesnesi eklendi...");
			}else {
				entitydata.loadShapeCircle(radius);
				System.out.println("entitydata nesnesi için circle shape nesnesi eklendi...");
			}
			System.out.println(restitution + " " + density + " " + friction + " " + isSensor);
			entitydata.loadFixture(0.1f, 0.1f, 0.1f, false);
			System.out.println("entitydata nesnesine fixture nesnesi eklendi");
			entitydata.loadEntity();
			System.out.println("entity nesnesi ekrana çizdirilmek için renderera eklendi...");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * dosyadaki animasyon bilgilerini okuyan metot.
	 * @param entitydata	oluþturulacak entity sýnýfýnýn bilgilerini tutar.
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
	 * string bir deðeri parçalayarak Vec2 sýnýfýna döüþtüren metot.
	 * @param data	içerisinde x ve y deðeri bulunan string
	 * @return		stringe göre deðerleri düzenlenmiþ vec2 sýnýfý
	 */
	private Vec2 loadVec2(String data) {
		String[] parser = data.split("/") ;
		Vec2 dataV = new Vec2(Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));
		return dataV ;
		
	}
	/**
	 * Her satýrda entitynin sahip olduðu özellikler bir = ile gösterilmiþtir. Bu metot sadece okunan satýr için bu deðerleri alýp global olan data hashmapina kaydediyor.
	 * @param reader	dosyanýn okunmasýna yardýmcý olan java sýnýfý
	 * @return			dosyanýn okunup okunmadýðýna dair bilgi gönderilmesi.
	 */
	private boolean parse(BufferedReader reader) {
		data.clear();
		String line = null ;
		try {
			line = reader.readLine() ;
			System.out.println("satýr okundu : " + line);
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
			System.out.println("parse edilmiþ kelime : " + parseWord);
			if(parseWord.contains("=")) {
				String[] parserEquals = parseWord.split("=") ;
				System.out.println("eþitlik parse deðerleri : " + parserEquals[0] + " " + parserEquals[1]);
				data.put(parserEquals[0], parserEquals[1]) ;
			}
		}
		
		return true ;
		
	}
}
