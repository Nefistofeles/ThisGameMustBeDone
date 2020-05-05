package utils;

import org.lwjgl.opengl.Display;

public class Orthographics {

	/**
	 * Belli bir orana göre daha güzel görsellik oluþturulsun diye kullanýlan ekran geniþletme sýnýfýdýr.
	 * 
	 */
	private static final float scaleFactor = 1.77f ;
	private Matrix4 projectionMatrix ;
	private float x, y, width, height ;

	public Orthographics(float start, float length) {
		this.x = -start ;
		this.y = start ;
		this.width = -length ;
		this.height = length ;
		projectionMatrix = Maths.orthoMatrix(x , y , width, height, -10, 10) ;
	}

	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	
	
	
	
	

}
