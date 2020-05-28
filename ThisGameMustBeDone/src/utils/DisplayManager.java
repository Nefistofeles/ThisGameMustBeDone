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
	 * Yaz�lan program�n bir pencere �er�evesinde ekranda g�r�lmesini sa�layan metottur.
	 */
	private long lastTime ;
	private long now ;
	private static float delta ;
	
	//ekran� belli bir oranlar �arparak geni�letme 
	//Normalde 1,1 iken 80 e 60 a ��k�yor.
	private static final Orthographics ortho = new Orthographics(80, 60) ;
	
	
	public DisplayManager() {
		lastTime = 0 ;
		now = 0 ;
		delta = 0 ;
		
	}
	/**
	 * Ekran� olu�turma metodudur.
	 * ContextAttribs ekran� kendi isteklerime veya program isteklerine g�re yap�land�rma i�lemidir. Program�n gerek�eleri gere�i normalde 3,2 olan attribute de�erleri ekleyece�im 
	 * particlea g�re 3,3 ileriye d�n�k yap�land�rmalara a��k, shader�n kendi yap�land�rma de�eri a��k, deep test max 24, stencil test max 8 biti destekleyecek �ekilde ayarland�.
	 * 24 e 8 max destekledi�i bit
	 * ekran�n full screen olmas� �imdilik kapal�, title oyunun ismi olan infection ve oyun pencere �eklinde a��l�yor. 
	 * max 60 fps destekliyor ve viewport yani g�r�nen k�sm� ekran�n tamam�n� i�eriyor.
	 * @param width		ekran�n geni�li�i		
	 * @param height	ekran�n y�ksekli�i
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
	 * program i�i zaman� ifade ediyor. Bu de�erin durmadan toplanmas� oyunda ge�en zaman� yani saniyeyi ifade edebilir.
	 * @return	bir birim zaman miktar�
	 */
	public static float getFrameTime() {
		return delta ;
	}
	/**
	 * ekran�n durmadan g�ncellenmesi gerekiyor. Bu i�lemi ger�ekle�tiren metot.
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
	 * ekran� kapat�r.
	 */
	public void close() {
		Display.destroy();
	}
	/**
	 * Birim zamanu bulmak i�in yap�lan hesap
	 * @return
	 */
	private long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution() ;
	}

	/**
	 * belli y�zdeye g�re geni�letmeyi sa�layacak matrix de�erinin d�nd�r�lmesi.
	 * @return
	 */
	public static Matrix4 getProjectionmatrix() {
		return ortho.getProjectionMatrix();
	}
	public static Orthographics getOrtho() {
		return ortho ;
	}
	
	
}
