package utils;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import renderer.Renderer;


public class MouseOrtho {
	
	/**
	 * Orthographics cameraya uygun mouse s�n�f�
	 * Temel mant��� t�m ekrana �izilen objeler projection ve viewmatrix yani ekran geni�letme ve kamera matrixleri ile �arp�l�r. Bunu mouse i�inde yapt�klar�n� d���n�rsek 
	 * bu matrixleri tersleyerek mouseun konumunu benim normalde bulundu�um d�nya koordinatlar�na d�nd�rebilir ve objeler ile �ak���p �ak��mad���n� alg�layabilirim
	 * 
	 * {@link http://antongerdelan.net/opengl/raycasting.html}
	 */
	
	private Camera camera;
	private Vec2 mousePos2 ;
	private Vec3 mousePos3 ;
	
	public MouseOrtho(Camera camera) {
		this.camera = camera ;
		mousePos2 = new Vec2(0,0) ;
		mousePos3 = new Vec3(0,0,0) ;
	}
	
	/**
	 * Camera hareketi olan d�nya i�in mouse konumunu hesaplayan metottur.
	 * ilk mouse pozisyonlar� display geni�li�i ve y�ksekli�ine b�l�n�r.
	 * Daha sonra inversi al�nan Matrixler ile �arp�l�r.  
	 * @return
	 */
	private Vec3 getMousePosition() {
		float mouseX = (float)2 * Mouse.getX() / Display.getWidth() -1 ;
		float mouseY = (float)2 * Mouse.getY() / Display.getHeight() -1 ;
		Vec4 v = new Vec4(mouseX, mouseY , -1 , 1) ;
		Matrix4.transform(Matrix4.inverse(DisplayManager.getProjectionmatrix(),null), v, v) ;
		Matrix4.transform(Matrix4.inverse(camera.getViewMatrix(),null), v, v) ;
		
		return new Vec3(v.x, v.y ,-1); 
	}
	/**
	 * Normal yap�lan i�lemin kameran�n sahip oldu�u matrix ile �arp�lmayan halidir. Kamera hareket etse bile ekranda hareket etmeyen nesneler (button vs) i�in kullan�l�r.
	 * @return
	 */
	private Vec3 getGUIMousePosition() {
		float mouseX = (float)2 * Mouse.getX() / Display.getWidth() -1 ;
		float mouseY = (float)2 * Mouse.getY() / Display.getHeight() -1 ;
		Vec4 v = new Vec4(mouseX, mouseY , -1 , 1) ;
		Matrix4.transform(Matrix4.inverse(DisplayManager.getProjectionmatrix(),null), v, v) ;
	//	Matrix4f.transform(Matrix4f.invert(camera.getViewMatrix(),null), v, v) ;
		
		return new Vec3(v.x, v.y ,-1); 
	}

	private Vec2 normalizeDeviceCoords() {
		float x = (float)Mouse.getX() / (float)Display.getWidth() ; 
		float y = (float)Mouse.getY() / (float)Display.getHeight() ;
		
		return new Vec2(x,y) ;
	}
	public Vec2 getNormalizeDeviceCoords() {
		return  normalizeDeviceCoords()  ;
	}

	public Vec2 getMousePos2() {
		Vec3 v = getMousePosition() ;
		mousePos2.set(v.x, v.y);
		return mousePos2;
	}
	public Vec3 getMousePos3() {
		mousePos3 = getMousePosition() ;
		return mousePos3;
	}
	public Vec2 getGUIMasterPosition() {
		Vec3 v = getGUIMousePosition() ;
		mousePos2.set(v.x, v.y);
		return mousePos2;
	}
}
