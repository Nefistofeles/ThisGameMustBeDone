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
	 * Gönderilen obj file ismini burada okunmasýný saðlýyor.
	 * 
	 * @param meshFileName 		.obj tabanlý dosyanýn ismi
	 */
	public void loadMesh(String meshFileName) {
		System.out.println(meshFileName);
		this.mesh = creator.loadMesh(meshFileName) ;
	}
	/**
	 * 
	 * @param textureName 	resmin ismi (.png tabanlý)
	 * @param row 			resmin bölünmesi gereken satýrý.
	 * @param column 		resmin bölünmesi gereken sütunu
	 */
	public void loadTexture(String textureName, int row, int column) {
		this.texture = creator.loadTexture(textureName, TextureLoader.TextureNearest, TextureLoader.DEFAULT_BIAS, row, column) ;
		
	}
	/**
	 * Resim animasyon özelliði taþýyorsa o resmin genel animasyon özelliklerinin girildiði kýsým
	 * 
	 * @param e 	Animasyon idsi
	 * @param data	içerisinde satýrýn toplam resim sayýsý ve kaçýncý satýrda bulunduðuna dair string deðer. Parse edilip deðerler gönderiliyor. 
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
	 * Entityi oluþturan yapý
	 * 
	 * @param entityType		Entity nesnesinin yapýsý (Player veya Enemynin alt sýnýflarý) tabi daha sonra ekleme yapýlýrsa burasý güncellenecektir.
	 * @param position			entitiynin dünyadaki koordinatý
	 * @param rotation			entitynin rotation deðeri. Yani ne kadar döndürülmüþ
	 * @param scale				entitynin scale deðeri. Sadece koordinatlarý yüklemek yetmez belli bir katsayý ile çarpýp büyütmek veya küçültmek için kullanýlýr.
	 * @param worldPosition		normalde 2 boyutlu bir oyun fakat bazý özel durumlar için worldposition yani z ekseni ile de oynama yapýlabiliyor.
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
	 * Oluþturulan entitye gerekli fizik özellikleri eklemek yani bodysini oluþturmak için kullanýlan metot.
	 * @param bodyType			oluþturulacak gövdenin türü statik dinamik veya kinematik
	 * @param fixedRotation		bodynin döndürülüp döndürülmemesini ayarlamak için girilen deðer
	 * @param isBullet			hýzlý nesneler için girilen deðer.
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
	 * Oluþturulan bodynin bir bir þeklini oluþturan metot
	 * 
	 * TODO : Shape e girilen koordinatlar geçici onlar karaktere göre tekrar yapýlandýrýlmalý
	 */
	public void loadShapePolygon() {
		this.shape = creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), Coordinates.getVertexVector(entity.getScale())) ;
		
	}
	/**
	 * Oluþturulacak þekil eðer daire ise kullanýlmasý gereken metottur.
	 * @param radius	dairenin yarýçapý
	 */
	public void loadShapeCircle(float radius) {
		this.shape = creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), radius) ;
	}
	/**
	 * Oluþturulan bodynin yoðunluðu, sürtünme katsayýsý gibi özelliklerin girilip bodye fiziksel özelliklerin eklenmesinin saðlayan sýnýf.
	 * @param restitution	esneklik katsayýsý
	 * @param density		yoðunluk katsayýsý	
	 * @param friction		sürtünme katsayýsý
	 * @param isSensor		bu özellik algýlayýcý mý (ileri düzey bir özellik kullanacak mýyým bilmiyorum.)
	 */
	public void loadFixture(float restitution, float density, float friction, boolean isSensor) {
		this.fixture = creator.getLoader().getPhysicsLoader().loadFixture(shape, body, restitution, density, friction, isSensor) ;
	}
	
	public void loadEntity() {
		entity.setPhysics(body, shape, fixture);
		creator.getRenderer().addEntity(entity);
	}

}
