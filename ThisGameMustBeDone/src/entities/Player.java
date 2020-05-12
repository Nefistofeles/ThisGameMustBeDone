package entities;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.sun.javafx.scene.traversal.Direction;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import contactlisteners.EntityType;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.DisplayManager;
import utils.Maths;
import utils.MouseOrtho;

public class Player extends Entity {

	
	public Player(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition,AnimationData animationData, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition,animationData, creator);

		this.speed.x = 300 ;
		this.speed.y = 300 ;

	}


	@Override
	public void update() {
		move();
		System.out.println("move metodu �a�r�ld�.");
		if(Mouse.isButtonDown(0)){
			creator.loadEntity("bullet", new Vec2(this.getPosition().x, this.getPosition().y), new Vec2(3,3), EntityType.BIT_BULLET, (short)(EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET)) ;
		}else if(Mouse.isButtonDown(1)){
			creator.loadEntity("zombie", new Vec2(creator.getMouse().getMousePos2().clone()), new Vec2(7,7),EntityType.BIT_ZOMBIE,(short)(EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER)) ;
		}
		//animasyonun �al��t�r�lmas�
		//animation.animate(AnimationEnum.idle, 15);
	}

	/**
	 * karakterin hareket metodudur. 
	 * karakterin mouseun durumuna g�re mause do�ru bakmas�n� sa�layan matematiksel i�lemleri de i�erir.
	 */
	private void move() {
		
		//karakterin fareye do�ru d�nmesi i�in yaz�lm�� matematiksel form�l
		/*Vec2 mousePosition = creator.getMouse().getMousePos2();
		float radians = (float)Math.atan2(mousePosition.y - position.y, mousePosition.x - position.x) ;
		float degree = (float) (radians * 180 / Math.PI) ; 
		rotation = degree - 90;*/
		//Vec2 degreeVector = new Vec2((float)Math.cos(radians),(float)Math.sin(radians)); 
		//body.applyForceToCenter(new Vec2( speed.x*degreeVector.x , speed.y * degreeVector.y));
		
		//klavye ile karakterin d�nyada hareket etmesi
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			direction.y = 1;
			System.out.println("w tu�una bas�ld�");
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			direction.y = -1;
			System.out.println("s tu�una bas�ld�");
		} else {
			direction.y = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			System.out.println("a tu�una bas�ld�");
			direction.x = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			direction.x = 1;
			System.out.println("d tu�una bas�ld�");
		} else {
			direction.x = 0;
		}
		if(direction.x == 1) {
			animation.animate(AnimationEnum.walk_right, 4);
			System.out.println("sa�a do�ru karakterin animasyonu etkilenle�tirildi");
		}else if(direction.x == -1) {
			animation.animate(AnimationEnum.walk_left, 4);
			System.out.println("sola do�ru karakterin animasyonu etkilenle�tirildi");
		}else if(direction.y == 1) {
			animation.animate(AnimationEnum.walk_up, 4);
			System.out.println("�ste do�ru karakterin animasyonu etkilenle�tirildi");
		}else if(direction.y == -1) {
			animation.animate(AnimationEnum.walk_down, 4);
			System.out.println("a�a�� do�ru karakterin animasyonu etkilenle�tirildi");
		}else {
			animation.animate(AnimationEnum.idle, 4);
			System.out.println("normal duru� karakterin animasyonu etkilenle�tirildi");
		}
		creator.getCamera().setPosition(this.position.x, this.position.y);
		System.out.println("camera pozisyonu karakterin pozisyonuna tekrar g�ncellendi");
		body.applyForceToCenter(new Vec2(speed.x * direction.x , speed.y * direction.y));
		System.out.println("karakterin bodysinin merkezine g�� uyguland� : " + new Vec2(speed.x * direction.x , speed.y * direction.y));
		System.out.println("karakter pozisyonu g�ncellendi.");
	}

	@Override
	public void attack(Entity entity) {
		
	}

	@Override
	public void hurt(Entity entity) {
	
		
	}

	@Override
	public void died() {
		
	}

}
