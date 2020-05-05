package gui;

import org.jbox2d.common.Vec2;

import dataStructure.Texture;
import utils.Matrix4;

public class GUI {

	private Texture texture ;
	private Vec2 position ;
	private Vec2 scale ;
	private Matrix4 transformationMatrix ;
	private float worldPosition ;
	
	/**
	 * Ekrana çizilecek buttonlar vs. gibi þekillerin bilgilerini tutan sýnýftýr.
	 * @param texture	ekrandaki resmi
	 * @param position	ekrandaki pozisyonu
	 * @param scale		ekrandaki boyutu
	 */
	
	public GUI(Texture texture, Vec2 position, Vec2 scale) {
		this.texture = texture ;
		this.position = position;
		this.scale = scale;
		worldPosition = 8 ;
		
		transformationMatrix = Matrix4.createTransformationMatrix(position, 0, scale) ;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getScale() {
		return scale;
	}

	public void setScale(Vec2 scale) {
		this.scale = scale;
	}

	public Matrix4 getTransformationMatrix() {
		transformationMatrix = Matrix4.updateTransformationMatrix(transformationMatrix, position, 0, scale) ;
		return transformationMatrix;
	}

	public float getWorldPosition() {
		return worldPosition;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	
}
