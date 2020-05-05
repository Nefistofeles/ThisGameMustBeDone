package utils;

import org.jbox2d.common.Vec3;
import org.lwjgl.input.Keyboard;

public class Camera {

	private Matrix4 viewMatrix ;
	private Vec3 position ;
	/**
	 * Yapýlan dünyanýn büyüklüðü ve ekrana hepsinin gösterilememesi durumunda dünyada hareket etmeyi saðlayan bir metottur.
	 * Temel mantýðý dünyadaki tüm objelerin belli bir orana göre kaydýrýlmasý anlamýna gelir.
	 * shaderlarda objenin pozisyonu ile çarpýlýr.
	 */
	
	public Camera() {
		position = new Vec3(0,0,0);
		viewMatrix = Maths.createViewMatrix(this);
	}


	public Matrix4 getViewMatrix() {
		viewMatrix = Maths.updateViewMatrix(viewMatrix, this);
		return viewMatrix;
	}

	public Vec3 getPosition() {
		return position;
	}
	public void setPosition(float x, float y) {
		this.position.x = x ;
		this.position.y = y ;
	}
	/**
	 * Matrix hareket metodu
	 */
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.y += 45 * DisplayManager.getFrameTime() ;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.y -= 45* DisplayManager.getFrameTime() ;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			position.x -= 45 * DisplayManager.getFrameTime();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			position.x += 45 * DisplayManager.getFrameTime();
		}
	}
	
}
