package main;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import renderer.EnableOpenGL;
import utils.DisplayManager;

public class Game implements Runnable{
	
	private Thread thread ;
	private DisplayManager display ;
	private GameManager game ;
	private World world ;
	private boolean isRun ;

	public Game() {
		isRun = false ;
		
		
	}

	public synchronized void start() {
		if(thread == null)
			thread = new Thread(this) ;
		if(!isRun) {
			thread.start();
			isRun = true ;
		}
			
	}
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
	private void init() {
		display = new DisplayManager();
		display.create(1366,768);
		world = new World(new Vec2(0,0)) ;
		game = new GameManager(world);
		game.init();
		
		EnableOpenGL.culling(true);
		EnableOpenGL.blendFunc(false);
		EnableOpenGL.enableDepthTest(true);
		EnableOpenGL.enableStencilTest(false);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1);
	}
	@Override
	public void run() {
		init();
		
		while(!Display.isCloseRequested() && isRun) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT );
			world.step(DisplayManager.getFrameTime(), 6, 2);
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
