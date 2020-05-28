package loader;

import org.jbox2d.collision.shapes.ChainShape;
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
	 * Jbox2d body olu�turma ve fizik de�erleri karma��kl���ndan kurtulup ara bir s�n�f ile bu de�erlerin daha kolay ve s�ral� girilmesini sa�layan s�n�ft�r.
	 * @param world		jbox2d y�netici s�n�f�.
	 */
	
	public PhysicsDataLoader(World world) {
		this.world = world ;
	}
	/**
	 * Temel jbox2d obje g�vdesini olu�turan metottur.
	 * @param entity		g�vdesi olu�turulacak nesne
	 * @param bodyType		g�vdesinin tipi (Static, Dynamic, Kinematic)
	 * @param fixedRotation	g�vde kendi d�nyas�nda rotate olsun mu sorusunu cevaplayan boolean de�er
	 * @param isBullet		h�zl� nesneler i�in se�ilen se�enek
	 * @return				jbox2d g�vde s�n�f�
	 */
	public Body loadBody(Entity entity, BodyType bodyType, boolean fixedRotation, boolean isBullet) {
		BodyDef bodydef = new BodyDef();
		System.out.println("bodydef olu�turuldu...");
		bodydef.type = bodyType ; 
		System.out.println("bodytype de�eri girildi.." + bodyType);
		bodydef.position = entity.getPosition() ;
		System.out.println("pozisyon de�eri girildi : " + entity.getPosition().toString());
		bodydef.fixedRotation = fixedRotation ;
		System.out.println("nesnenin d�nd�r�l�p d�nd�r�lmeyece�i de�eri girildi : " + fixedRotation);
		bodydef.linearDamping = 5 ; 
		System.out.println("linear damping de�eri girildi : " + 5);
		bodydef.userData = entity ;
		System.out.println("bodye ait olan entity girildi : ");
		bodydef.bullet = isBullet ;
		System.out.println("olu�turulan entitynin h�zl� nesne olup olmad��� girildi..." + isBullet);
		Body body = world.createBody(bodydef) ;
		System.out.println("body worlde eklendi...");
		return body ;
	}
	/**
	 * Bir jbox2d g�vdesi i�in gerekli s�rt�nme, yo�unluk, iade katsay�s� gibi �zellikleri fixture ile tan�mlan�r. Bu s�n�f� olu�turan metottur.
	 * @param shape			fixture s�n�f�n�n �ekli
	 * @param body			fixture s�n�f�n�n g�vdesi
	 * @param restitution	geri iade katsay�s�
	 * @param density		yo�unluk
	 * @param friction		s�rt�nme katsay�s�
	 * @param isSensor		bir alg�lay�c� m�. Yani obje olmay�p sadece �ak��ma olup tepki olmayaca�� durumlar bu se�enek se�ilir.
	 * @return
	 */
	public Fixture loadFixture(Shape shape, Body body, float restitution, float density, float friction, boolean isSensor, short categoryBits, short maskBits) {
		FixtureDef fixturedef = new FixtureDef();
		System.out.println("fixturedef olu�turuldu...");
		fixturedef.restitution = restitution;
		System.out.println("geri iade katsay�s� girildi... " + restitution);
		fixturedef.shape = shape ;
		System.out.println("�ekli i�in olu�turulmu� shape s�n�f� atand� : ");
		fixturedef.density = density ;
		System.out.println("yo�unluk de�eri girildi : " + density);
		fixturedef.friction = friction;
		System.out.println("s�rt�nme katsay�s� girildi... " + friction);
		fixturedef.isSensor = isSensor ;
		System.out.println("alg�lay�c� m� : " + isSensor);
		fixturedef.filter.categoryBits = categoryBits ;		//kendi t�r�
		fixturedef.filter.maskBits = maskBits ;				//bunlarla collide olacak
		Fixture fixture = body.createFixture(fixturedef) ;
		System.out.println("fixture olu�turuldu... ");

		
		return fixture ;
	}
	/**
	 * T�m body, shape, fixture gibi �zelliklerin bir arada yap�ld��� bir metottur.
	 * @param entity		g�vdesi olu�turulacak nesne
	 * @param vertices		shape koordinat de�erleri
	 * @param scale			shader koordinat�n�n b�y�kl���
	 * @param bodyType		g�vdesinin tipi (Static, Dynamic, Kinematic)
	 * @param fixedRotation	g�vde kendi d�nyas�nda rotate olsun mu sorusunu cevaplayan boolean de�er
	 * @param shapeType		olu�turulan bodynin �eklinin t�r� (polygon, circle)
	 * @param restitution	geri iade katsay�s�
	 * @param density		yo�unluk
	 * @param friction		s�rt�nme katsay�s�
	 * @param isSensor		bir alg�lay�c� m�. Yani obje olmay�p sadece �ak��ma olup tepki olmayaca�� durumlar bu se�enek se�ilir.
	 * @param isBullet		h�zl� nesneler i�in se�ilen se�enek
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
	 * polygon bir shape i�in olu�turulmu� metottur.
	 * @param centerPos		polygonun merkezi
	 * @param vertices		polygonun koordinatlar�
	 * @return				yeni bir shape s�n�f� g�nderir. Olu�turulan nesnenin �arp��ma �eklini ifade eder.
	 */
	public Shape loadShape(Vec2 centerPos, Vec2[] vertices) {
		PolygonShape shape = new PolygonShape();
		System.out.println("polygon shape olu�turuldu...");
		shape.m_centroid.set(centerPos) ; 
		System.out.println("merkez koordinat� girildi : " +centerPos.toString());
		shape.set(vertices, vertices.length);
		System.out.println("vertex de�erleri girildi...");
		for(int i = 0 ; i < vertices.length ; i++) {
			System.out.println(vertices[i].toString());
		}
		return shape ;
	}
	/**
	 * Circle t�r�nde shape olu�turmak i�in kullan�lan metottur.
	 * @param shapePos	shapein merkezi
	 * @param radius	circle shapein radiusu
	 * @return			yeni bir shape s�n�f� g�nderir. Olu�turulan nesnenin �arp��ma �eklini ifade eder.
	 */
	public Shape loadShape(Vec2 shapePos, float radius) {
		CircleShape shape = new CircleShape();
		System.out.println("circle shape olu�turuldu...");
		shape.m_p.set(shapePos) ;
		System.out.println("merkezi girildi : " + shapePos.toString());
		shape.m_radius = radius ;
		System.out.println("radius de�eri girildi : " +radius);
		return shape ;
	}
	
	public void loadScreenShape(Vec2[] vertices) {
		BodyDef bodydef = new BodyDef() ;
		bodydef.type = BodyType.STATIC ; 
		bodydef.position = new Vec2(0,0) ;
		bodydef.angle = 0 ;
		bodydef.fixedRotation = true ;
		bodydef.linearDamping = 0 ; 
		Body body = world.createBody(bodydef) ;
		
		ChainShape shape = new ChainShape();
		shape.createChain(vertices, vertices.length);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.restitution = 1f;
		fixturedef.shape = shape ;
		fixturedef.density =1f ;
		fixturedef.friction = 0.2f ;
		
		Fixture fixture = body.createFixture(fixturedef) ;
		
		
	}
}
