package utils;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private long lastTime ;
	private long now ;
	private static float delta ;
	
	private static final Matrix4 projectionMatrix = new Orthographics(80, 60).getProjectionMatrix() ;
	
	public DisplayManager() {
		lastTime = 0 ;
		now = 0 ;
		delta = 0 ;
		
	}

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
	
	public static float getFrameTime() {
		return delta ;
	}
	
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
	
	public void close() {
		Display.destroy();
	}
	
	private long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution() ;
	}

	public static Matrix4 getProjectionmatrix() {
		return projectionMatrix;
	}
	
	
}
