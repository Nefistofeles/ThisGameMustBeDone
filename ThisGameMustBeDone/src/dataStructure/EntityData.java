package dataStructure;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import loader.Creator;
import loader.TextureLoader;
import utils.Coordinates;

public class EntityData {
	
	private Creator creator ;

	private Entity entity ;
	private Mesh mesh ;
	private Texture texture ;
	private AnimationData animationData ;
	private Body body ;
	private Fixture fixture ;
	private Shape shape ;
	
	public EntityData(Creator creator) {
		this.creator = creator ;
		animationData = new AnimationData();
		
	}
	
	/**
	 * G�nderilen obj file ismini burada okunmas�n� sa�l�yor.
	 * 
	 * @param meshFileName 		.obj tabanl� dosyan�n ismi
	 */
	public void loadMesh(String meshFileName) {
		System.out.println(meshFileName);
		this.mesh = creator.loadMesh(meshFileName) ;
	}
	/**
	 * 
	 * @param textureName 	resmin ismi (.png tabanl�)
	 * @param row 			resmin b�l�nmesi gereken sat�r�.
	 * @param column 		resmin b�l�nmesi gereken s�tunu
	 */
	public void loadTexture(String textureName, int row, int column) {
		this.texture = creator.loadTexture(textureName, TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS, row, column) ;
		
	}
	/**
	 * Resim animasyon �zelli�i ta��yorsa o resmin genel animasyon �zelliklerinin girildi�i k�s�m
	 * 
	 * @param e 	Animasyon idsi
	 * @param data	i�erisinde sat�r�n toplam resim say�s� ve ka��nc� sat�rda bulundu�una dair string de�er. Parse edilip de�erler g�nderiliyor. 
	 */
	public void loadAnimationData(AnimationEnum e, String data) {
		if(data == "0") {
			return ;
		}
		String[] parser = data.split("/") ;
		System.out.println(data);
		animationData.addAnimationData(e, Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));
		
	}
	/**
	 * Entityi olu�turan yap�
	 * 
	 * @param entityType		Entity nesnesinin yap�s� (Player veya Enemynin alt s�n�flar�) tabi daha sonra ekleme yap�l�rsa buras� g�ncellenecektir.
	 * @param position			entitiynin d�nyadaki koordinat�
	 * @param rotation			entitynin rotation de�eri. Yani ne kadar d�nd�r�lm��
	 * @param scale				entitynin scale de�eri. Sadece koordinatlar� y�klemek yetmez belli bir katsay� ile �arp�p b�y�tmek veya k���ltmek i�in kullan�l�r.
	 * @param worldPosition		normalde 2 boyutlu bir oyun fakat baz� �zel durumlar i�in worldposition yani z ekseni ile de oynama yap�labiliyor.
	 */
	public void loadEntity(String entityType, Vec2 position, float rotation, Vec2 scale, float worldPosition) {
		switch(entityType) {
		case "player" : 
			System.out.println(mesh);
			entity = new Player(mesh, texture, position, rotation, scale, worldPosition, animationData, creator) ;
			System.out.println("entity");
			break ;
		case "zombie" : 
			entity = new Zombie(mesh, texture, position, rotation, scale, worldPosition, animationData, creator) ;
		}
		
	}
	/**
	 * Olu�turulan entitye gerekli fizik �zellikleri eklemek yani bodysini olu�turmak i�in kullan�lan metot.
	 * @param bodyType			olu�turulacak g�vdenin t�r� statik dinamik veya kinematik
	 * @param fixedRotation		bodynin d�nd�r�l�p d�nd�r�lmemesini ayarlamak i�in girilen de�er
	 * @param isBullet			h�zl� nesneler i�in girilen de�er.
	 */
	public void loadBody(String bodyType, boolean fixedRotation, boolean isBullet) {
		if(bodyType.equals("dynamic"))
			body = creator.getLoader().getPhysicsLoader().loadBody(entity, BodyType.DYNAMIC, fixedRotation, isBullet) ;
		else if(bodyType.equals("static"))
			body = creator.getLoader().getPhysicsLoader().loadBody(entity, BodyType.STATIC, fixedRotation, isBullet) ;
		else if(bodyType.equals("kinematic"))
			body = creator.getLoader().getPhysicsLoader().loadBody(entity, BodyType.KINEMATIC, fixedRotation, isBullet) ;
	}
	
	/**
	 * Olu�turulan bodynin bir bir �eklini olu�turan metot
	 * 
	 * TODO : Shape e girilen koordinatlar ge�ici onlar karaktere g�re tekrar yap�land�r�lmal�
	 */
	public void loadShapePolygon() {
		this.shape = creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), Coordinates.getVertexVector(entity.getScale())) ;
		
	}
	/**
	 * Olu�turulacak �ekil e�er daire ise kullan�lmas� gereken metottur.
	 * @param radius	dairenin yar��ap�
	 */
	public void loadShapeCircle(float radius) {
		this.shape = creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), radius) ;
	}
	/**
	 * Olu�turulan bodynin yo�unlu�u, s�rt�nme katsay�s� gibi �zelliklerin girilip bodye fiziksel �zelliklerin eklenmesinin sa�layan s�n�f.
	 * @param restitution	esneklik katsay�s�
	 * @param density		yo�unluk katsay�s�	
	 * @param friction		s�rt�nme katsay�s�
	 * @param isSensor		bu �zellik alg�lay�c� m� (ileri d�zey bir �zellik kullanacak m�y�m bilmiyorum.)
	 */
	public void loadFixture(float restitution, float density, float friction, boolean isSensor) {
		this.fixture = creator.getLoader().getPhysicsLoader().loadFixture(shape, body, restitution, density, friction, isSensor) ;
	}
	
	public void loadEntity() {
		entity.setPhysics(body, shape, fixture);
		creator.getRenderer().addEntity(entity);
	}

}
