package loader;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import entities.Entity;


public class PhysicsDataLoader {

	private World world ;
	
	public PhysicsDataLoader(World world) {
		this.world = world ;
	}
	public Body loadBody(Entity entity, BodyType bodyType, boolean fixedRotation, boolean isBullet) {
		BodyDef bodydef = new BodyDef();
		bodydef.type = bodyType ; 
		bodydef.position = entity.getPosition() ;
		bodydef.fixedRotation = fixedRotation ;
		bodydef.linearDamping = 5 ; 
		bodydef.userData = entity ;
		bodydef.bullet = isBullet ;
		return world.createBody(bodydef) ;
	}
	public Fixture loadFixture(Shape shape, Body body, float restitution, float density, float friction, boolean isSensor) {
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.restitution = restitution;
		fixturedef.shape = shape ;
		fixturedef.density = density ;
		fixturedef.friction = friction;
		fixturedef.isSensor = isSensor ;
		Fixture fixture = body.createFixture(fixturedef) ;
		
		return fixture ;
	}
	public void createPhysics(Entity entity, Vec2[] vertices,Vec2 scale,BodyType bodyType, boolean fixedRotation,
			ShapeType shapeType, float restitution, float density, float friction,boolean isSensor, boolean isBullet ) {
		Body body = loadBody(entity, bodyType, fixedRotation, isBullet) ;
		
		Shape shape = null ;
		if(shapeType == ShapeType.POLYGON) {
			shape = loadShape(entity.getPosition(), vertices) ;
		}else if(shapeType == ShapeType.CIRCLE) {
			shape = loadShape(entity.getPosition(), scale.x) ;
		}
		Fixture fixture = loadFixture(shape, body, restitution, density, friction, isSensor); 
		entity.setPhysics(body, shape, fixture);
		
	}
	public Shape loadShape(Vec2 centerPos, Vec2[] vertices) {
		PolygonShape shape = new PolygonShape();
		shape.m_centroid.set(centerPos) ; 
		shape.set(vertices, vertices.length);
		return shape ;
	}
	public Shape loadShape(Vec2 shapePos, float radius) {
		CircleShape shape = new CircleShape();
		shape.m_p.set(shapePos) ;
		shape.m_radius = radius ;
		return shape ;
	}
}
