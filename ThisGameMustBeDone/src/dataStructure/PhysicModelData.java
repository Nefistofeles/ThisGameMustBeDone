package dataStructure;

import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;

public class PhysicModelData {

	private String modelName ;
	private Vec2[] model ;
	private ShapeType shapeType ;
	private float radius ;
	private float restitution ;
	private float density ;
	private float friction ;
	private boolean isSensor ;
	
	public PhysicModelData() {
		
	}
	
	public PhysicModelData(String modelName, Vec2[] model, ShapeType shapeType, float radius, float restitution, float density,
			float friction, boolean isSensor) {
		this.modelName = modelName ;
		this.model = model;
		this.shapeType = shapeType;
		this.radius = radius;
		this.restitution = restitution;
		this.density = density;
		this.friction = friction;
		this.isSensor = isSensor;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Vec2[] getModel() {
		return model;
	}
	public Vec2[] cloneModel() {
		Vec2[] clone = new Vec2[model.length] ;
		for(int i = 0 ; i < clone.length ; i++) {
			clone[i] = model[i].clone() ;
		}
		
		return clone ;
	}

	public void setModel(Vec2[] model) {
		this.model = model;
	}

	public ShapeType getShapeType() {
		return shapeType;
	}

	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public boolean isSensor() {
		return isSensor;
	}

	public void setSensor(boolean isSensor) {
		this.isSensor = isSensor;
	}
	
	
	
	
	
}
