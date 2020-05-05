package utils;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	/**
	 * Yazýlan programýn bir pencere çerçevesinde ekranda görülmesini saðlayan metottur.
	 */
	private long lastTime ;
	private long now ;
	private static float delta ;
	
	//ekraný belli bir oranlar çarparak geniþletme 
	//Normalde 1,1 iken 80 e 60 a çýkýyor.
	private static final Matrix4 projectionMatrix = new Orthographics(80, 60).getProjectionMatrix() ;
	
	public DisplayManager() {
		lastTime = 0 ;
		now = 0 ;
		delta = 0 ;
		
	}
	/**
	 * Ekraný oluþturma metodudur.
	 * ContextAttribs ekraný kendi isteklerime veya program isteklerine göre yapýlandýrma iþlemidir. Programýn gerekçeleri gereði normalde 3,2 olan attribute deðerleri ekleyeceðim 
	 * particlea göre 3,3 ileriye dönük yapýlandýrmalara açýk, shaderýn kendi yapýlandýrma deðeri açýk, deep test max 24, stencil test max 8 biti destekleyecek þekilde ayarlandý.
	 * 24 e 8 max desteklediði bit
	 * ekranýn full screen olmasý þimdilik kapalý, title oyunun ismi olan infection ve oyun pencere þeklinde açýlýyor. 
	 * max 60 fps destekliyor ve viewport yani görünen kýsmý ekranýn tamamýný içeriyor.
	 * @param width		ekranýn geniþliði		
	 * @param height	ekranýn yüksekliði
	 */
	public void create(int width, int height) {
		
		ContextAttribs attribs = new ContextAttribs(3,3).withForwardCompatible(true).withProfileCore(true) ;
		PixelFormat pixel = new PixelFormat();
		pixel.withDepthBits(24) ;
		pixel.withStencilBits(8) ;
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(pixel, attribs);
			Display.setTitle("Infection");
			Display.setVSyncEnabled(true);
			Display.setFullscreen(false);
			Display.setResizable(false);
			Display.sync(60);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		GL11.glViewport(0, 0, width, height);
		lastTime = getTime() ;
		
	}
	/**
	 * program içi zamaný ifade ediyor. Bu deðerin durmadan toplanmasý oyunda geçen zamaný yani saniyeyi ifade edebilir.
	 * @return	bir birim zaman miktarý
	 */
	public static float getFrameTime() {
		return delta ;
	}
	/**
	 * ekranýn durmadan güncellenmesi gerekiyor. Bu iþlemi gerçekleþtiren metot.
	 */
	public void update() {
		Display.update();
		
		now = getTime();
		delta = (now - lastTime) / 1000f ;
		lastTime = now ;
		if(Keyboard.isKeyDown(Keyboard.KEY_N)) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
		}else if(Keyboard.isKeyDown(Keyboard.KEY_M)) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
		}else if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_POINT );
		}
	}
	/**
	 * ekraný kapatýr.
	 */
	public void close() {
		Display.destroy();
	}
	/**
	 * Birim zamanu bulmak için yapýlan hesap
	 * @return
	 */
	private long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution() ;
	}

	/**
	 * belli yüzdeye göre geniþletmeyi saðlayacak matrix deðerinin döndürülmesi.
	 * @return
	 */
	public static Matrix4 getProjectionmatrix() {
		return projectionMatrix;
	}
	
	
}
