package utils;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import renderer.Renderer;


public class MouseOrtho {
	
	/**
	 * Orthographics cameraya uygun mouse sýnýfý
	 * Temel mantýðý tüm ekrana çizilen objeler projection ve viewmatrix yani ekran geniþletme ve kamera matrixleri ile çarpýlýr. Bunu mouse içinde yaptýklarýný düþünürsek 
	 * bu matrixleri tersleyerek mouseun konumunu benim normalde bulunduðum dünya koordinatlarýna döndürebilir ve objeler ile çakýþýp çakýþmadýðýný algýlayabilirim
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
	 * Camera hareketi olan dünya için mouse konumunu hesaplayan metottur.
	 * ilk mouse pozisyonlarý display geniþliði ve yüksekliðine bölünür.
	 * Daha sonra inversi alýnan Matrixler ile çarpýlýr.  
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
	 * Normal yapýlan iþlemin kameranýn sahip olduðu matrix ile çarpýlmayan halidir. Kamera hareket etse bile ekranda hareket etmeyen nesneler (button vs) için kullanýlýr.
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
