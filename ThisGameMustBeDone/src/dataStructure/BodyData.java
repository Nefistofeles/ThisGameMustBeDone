package dataStructure;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class BodyData {

	private BodyType bodyType ;
	private Vec2 position ;
	private float rotation ;
	private Vec2 scale ;
	private float worldPosition;
	private boolean fixedRotation ;
	private boolean isBullet ;
	
	public BodyData() {
		// TODO Auto-generated constructor stub
	}

	public BodyData(BodyType bodyType, Vec2 position, float rotation, Vec2 scale, float worldPosition,
			boolean fixedRotation, boolean isBullet) {
		
		this.bodyType = bodyType;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.worldPosition = worldPosition;
		this.fixedRotation = fixedRotation;
		this.isBullet = isBullet;
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
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

	public float getWorldPosition() {
		return worldPosition;
	}

	public void setWorldPosition(float worldPosition) {
		this.worldPosition = worldPosition;
	}

	public boolean isFixedRotation() {
		return fixedRotation;
	}

	public void setFixedRotation(boolean fixedRotation) {
		this.fixedRotation = fixedRotation;
	}

	public boolean isBullet() {
		return isBullet;
	}

	public void setBullet(boolean isBullet) {
		this.isBullet = isBullet;
	}
	
	
	
	
}
