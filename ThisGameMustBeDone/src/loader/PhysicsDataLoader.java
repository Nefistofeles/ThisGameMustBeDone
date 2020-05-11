package loader;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import entities.Entity;


public class PhysicsDataLoader {

	private World world ;
	
	/**
	 * Jbox2d body oluþturma ve fizik deðerleri karmaþýklýðýndan kurtulup ara bir sýnýf ile bu deðerlerin daha kolay ve sýralý girilmesini saðlayan sýnýftýr.
	 * @param world		jbox2d yönetici sýnýfý.
	 */
	
	public PhysicsDataLoader(World world) {
		this.world = world ;
	}
	/**
	 * Temel jbox2d obje gövdesini oluþturan metottur.
	 * @param entity		gövdesi oluþturulacak nesne
	 * @param bodyType		gövdesinin tipi (Static, Dynamic, Kinematic)
	 * @param fixedRotation	gövde kendi dünyasýnda rotate olsun mu sorusunu cevaplayan boolean deðer
	 * @param isBullet		hýzlý nesneler için seçilen seçenek
	 * @return				jbox2d gövde sýnýfý
	 */
	public Body loadBody(Entity entity, BodyType bodyType, boolean fixedRotation, boolean isBullet) {
		BodyDef bodydef = new BodyDef();
		System.out.println("bodydef oluþturuldu...");
		bodydef.type = bodyType ; 
		System.out.println("bodytype deðeri girildi.." + bodyType);
		bodydef.position = entity.getPosition() ;
		System.out.println("pozisyon deðeri girildi : " + entity.getPosition().toString());
		bodydef.fixedRotation = fixedRotation ;
		System.out.println("nesnenin döndürülüp döndürülmeyeceði deðeri girildi : " + fixedRotation);
		bodydef.linearDamping = 5 ; 
		System.out.println("linear damping deðeri girildi : " + 5);
		bodydef.userData = entity ;
		System.out.println("bodye ait olan entity girildi : ");
		bodydef.bullet = isBullet ;
		System.out.println("oluþturulan entitynin hýzlý nesne olup olmadýðý girildi..." + isBullet);
		Body body = world.createBody(bodydef) ;
		System.out.println("body worlde eklendi...");
		return body ;
	}
	/**
	 * Bir jbox2d gövdesi için gerekli sürtünme, yoðunluk, iade katsayýsý gibi özellikleri fixture ile tanýmlanýr. Bu sýnýfý oluþturan metottur.
	 * @param shape			fixture sýnýfýnýn þekli
	 * @param body			fixture sýnýfýnýn gövdesi
	 * @param restitution	geri iade katsayýsý
	 * @param density		yoðunluk
	 * @param friction		sürtünme katsayýsý
	 * @param isSensor		bir algýlayýcý mý. Yani obje olmayýp sadece çakýþma olup tepki olmayacaðý durumlar bu seçenek seçilir.
	 * @return
	 */
	public Fixture loadFixture(Shape shape, Body body, float restitution, float density, float friction, boolean isSensor, short categoryBits, short maskBits) {
		FixtureDef fixturedef = new FixtureDef();
		System.out.println("fixturedef oluþturuldu...");
		fixturedef.restitution = restitution;
		System.out.println("geri iade katsayýsý girildi... " + restitution);
		fixturedef.shape = shape ;
		System.out.println("þekli için oluþturulmuþ shape sýnýfý atandý : ");
		fixturedef.density = density ;
		System.out.println("yoðunluk deðeri girildi : " + density);
		fixturedef.friction = friction;
		System.out.println("sürtünme katsayýsý girildi... " + friction);
		fixturedef.isSensor = isSensor ;
		System.out.println("algýlayýcý mý : " + isSensor);
		fixturedef.filter.categoryBits = categoryBits ;		//kendi türü
		fixturedef.filter.maskBits = maskBits ;				//bunlarla collide olacak
		Fixture fixture = body.createFixture(fixturedef) ;
		System.out.println("fixture oluþturuldu... ");

		
		return fixture ;
	}
	/**
	 * Tüm body, shape, fixture gibi özelliklerin bir arada yapýldýðý bir metottur.
	 * @param entity		gövdesi oluþturulacak nesne
	 * @param vertices		shape koordinat deðerleri
	 * @param scale			shader koordinatýnýn büyüklüðü
	 * @param bodyType		gövdesinin tipi (Static, Dynamic, Kinematic)
	 * @param fixedRotation	gövde kendi dünyasýnda rotate olsun mu sorusunu cevaplayan boolean deðer
	 * @param shapeType		oluþturulan bodynin þeklinin türü (polygon, circle)
	 * @param restitution	geri iade katsayýsý
	 * @param density		yoðunluk
	 * @param friction		sürtünme katsayýsý
	 * @param isSensor		bir algýlayýcý mý. Yani obje olmayýp sadece çakýþma olup tepki olmayacaðý durumlar bu seçenek seçilir.
	 * @param isBullet		hýzlý nesneler için seçilen seçenek
	 */
	public void createPhysics(Entity entity, Vec2[] vertices,Vec2 scale,BodyType bodyType, boolean fixedRotation,
			ShapeType shapeType, float restitution, float density, float friction,boolean isSensor, boolean isBullet , short categoryBits, short maskBits) {
		Body body = loadBody(entity, bodyType, fixedRotation, isBullet) ;
		
		Shape shape = null ;
		if(shapeType == ShapeType.POLYGON) {
			shape = loadShape(entity.getPosition(), vertices) ;
		}else if(shapeType == ShapeType.CIRCLE) {
			shape = loadShape(entity.getPosition(), scale.x) ;
		}
		Fixture fixture = loadFixture(shape, body, restitution, density, friction, isSensor, categoryBits, maskBits); 
		
		
	}
	/**
	 * polygon bir shape için oluþturulmuþ metottur.
	 * @param centerPos		polygonun merkezi
	 * @param vertices		polygonun koordinatlarý
	 * @return				yeni bir shape sýnýfý gönderir. Oluþturulan nesnenin çarpýþma þeklini ifade eder.
	 */
	public Shape loadShape(Vec2 centerPos, Vec2[] vertices) {
		PolygonShape shape = new PolygonShape();
		System.out.println("polygon shape oluþturuldu...");
		shape.m_centroid.set(centerPos) ; 
		System.out.println("merkez koordinatý girildi : " +centerPos.toString());
		shape.set(vertices, vertices.length);
		System.out.println("vertex deðerleri girildi...");
		for(int i = 0 ; i < vertices.length ; i++) {
			System.out.println(vertices[i].toString());
		}
		return shape ;
	}
	/**
	 * Circle türünde shape oluþturmak için kullanýlan metottur.
	 * @param shapePos	shapein merkezi
	 * @param radius	circle shapein radiusu
	 * @return			yeni bir shape sýnýfý gönderir. Oluþturulan nesnenin çarpýþma þeklini ifade eder.
	 */
	public Shape loadShape(Vec2 shapePos, float radius) {
		CircleShape shape = new CircleShape();
		System.out.println("circle shape oluþturuldu...");
		shape.m_p.set(shapePos) ;
		System.out.println("merkezi girildi : " + shapePos.toString());
		shape.m_radius = radius ;
		System.out.println("radius deðeri girildi : " +radius);
		return shape ;
	}
}
