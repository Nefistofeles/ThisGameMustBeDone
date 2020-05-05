package entities;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.Matrix4;

public abstract class Entity {

	protected Texture texture ;
	protected Vec2 position ;
	protected float rotation ;
	protected Vec2 scale ;
	protected float worldPosition ;
	protected Mesh mesh ;
	protected Matrix4 transformationMatrix ;
	protected Vec2 speed ;
	protected Creator creator ;
	
	protected Body body ;
	protected Fixture fixture ;
	protected Shape shape ;
	protected Animation animation ;
	protected Vec2 direction ;
	protected boolean isDead ;
	
	public Entity(Mesh mesh,Texture texture,  Vec2 position, float rotation, Vec2 scale, float worldPosition, AnimationData animationData, Creator creator ) {
		this.creator = creator ;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
		this.worldPosition = worldPosition ;
		this.texture = texture ;
		this.body = null ;
		this.fixture = null ;
		this.shape = null ;
		transformationMatrix = Matrix4.createTransformationMatrix(position, rotation, scale) ;
		speed = new Vec2(100,100) ;
		animation = new Animation(texture, animationData) ;
		direction = new Vec2(0,0) ;
		isDead = false ;
	
	}
	public Entity(Mesh mesh,Texture texture,  Vec2 position, float rotation, Vec2 scale, float worldPosition, Creator creator ) {
		this.creator = creator ;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
		this.worldPosition = worldPosition ;
		this.texture = texture ;
		this.body = null ;
		this.fixture = null ;
		this.shape = null ;
		transformationMatrix = Matrix4.createTransformationMatrix(position, rotation, scale) ;
		speed = new Vec2(100,100) ;
		animation = null ;
		direction = new Vec2(0,0) ;
		isDead = false ;
	
	}
	/**
	 * JBox2d fiziðinin entitye entegre edilmesi kýsmý
	 * @param body		entitynin bodysini temsil eder.
	 * @param shape		entitynin çarpýþma þeklini temsil eder
	 * @param fixture	entitynin yoðunluðunu, sürtünmesini veya esnekliði gibi özelliklerinin ayarlandýðý kýsmý temsil eder.
	 */
	public void setPhysics(Body body, Shape shape, Fixture fixture) {
		this.body = body ;
		this.fixture = fixture ;
		this.shape = shape ;
		this.position = body.getPosition() ;
		this.rotation = body.getAngle() ;
	}
	/**
	 * seçilen entitye saldýrý yapmasýna olanak saðlayacak metot.
	 */
	protected abstract void attack(Entity entity);
	/**
	 * yaralandýðýnda ne olacaðýnýn girileceði metot.
	 */
	protected abstract void hurt(Entity entity);
	
	/**
	 * ölünce ne olacaðýnýn programlanacaðý metot.
	 */
	protected abstract void died();
	/**
	 * Her entity kendine olacak durumlarýn durmadan güncellenmesi için gerekli olan update metodu
	 */
	public abstract void update() ;
	
	/**
	 * entitynin hareketinin, dönmesinin veya þeklinin büyüyüp küçülmesinin shadera aktaracak matrixin güncellenmesinin saðlýyor. Shader da bu matrix oluþturulan varlýðýn koordinatlarý ile çarpýlýyor.
	 * @return
	 */
	public Matrix4 getTransformationMatrix() {
		transformationMatrix = Matrix4.updateTransformationMatrix(transformationMatrix, position, rotation, scale) ;
		return transformationMatrix;
	}
	


	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Vec2 getScale() {
		return scale;
	}

	public void setScale(Vec2 scale) {
		this.scale = scale;
	}

	public Mesh getMesh() {
		return mesh;
	}
	public float getWorldPosition() {
		return worldPosition;
	}
	public void setWorldPosition(float worldPosition) {
		this.worldPosition = worldPosition;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Body getBody() {
		return body;
	}
	public Fixture getFixture() {
		return fixture;
	}
	public Shape getShape() {
		return shape;
	}
	public Vec2 getDirection() {
		return direction;
	}
	public void setDirection(Vec2 direction) {
		this.direction = direction;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
}
