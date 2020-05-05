package main;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import loader.Creator;
import renderer.EnableOpenGL;
import utils.DisplayManager;

public class Game implements Runnable{
	
	private Thread thread ;
	private DisplayManager display ;
	private GameManager game ;
	private Creator creator ;
	private World world ;
	private boolean isRun ;
	/**
	 * Oyunun genel çalýþtýrma yönetim kýsmýdýr.
	 */
	public Game() {
		isRun = false ;
		
		
	}
	/**
	 * Threadi çalýþtýrýp oyunun baþlamasýný saðlayan metot.
	 */
	public synchronized void start() {
		if(thread == null)
			thread = new Thread(this) ;
		if(!isRun) {
			thread.start();
			isRun = true ;
		}
			
	}
	/**
	 * Threadin durdurulup oyunun son bulmasýný ve gereksiz tüm oyun öðelerinin silinmesini saðlayan sýnýftýr.
	 */
	public synchronized void stop() {
		try {
			if(isRun) {
				game.clean();
				thread.interrupt();
				display.close();
				isRun = false ;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	 * Program baþladýðýnda gerekli olan öðelerin programa yüklenmesini veya gerekli sýnýflarýn oluþturulmasýný saðlayan metottur.
	 */
	private void init() {
		display = new DisplayManager();
		display.create(1366,768);
		world = new World(new Vec2(0,0)) ;
		creator = new Creator(world);
		game = new GameManager(creator);
		game.init();
		
		EnableOpenGL.culling(true);
		EnableOpenGL.blendFunc(false);
		EnableOpenGL.enableDepthTest(true);
		EnableOpenGL.enableStencilTest(false);
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1);
	}
	/**
	 * Programýn ekran kapatma tuþuna veya kapatma isteði gönderilene kadar sonsuz bir döngüde çalýþmasýný saðlayan metottur.
	 */
	@Override
	public void run() {
		init();
		
		while(!Display.isCloseRequested() && isRun) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT );
			creator.getWorld().step(DisplayManager.getFrameTime(), 6, 2);
			game.update();
			game.render();
			display.update();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		stop();
		
	}
	
	
}
