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
		System.out.println("move metodu çaðrýldý.");
		if(Mouse.isButtonDown(0)){
			creator.loadEntity("bullet", new Vec2(this.getPosition().x, this.getPosition().y), new Vec2(3,3), EntityType.BIT_BULLET, (short)(EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET)) ;
		}else if(Mouse.isButtonDown(1)){
			creator.loadEntity("zombie", new Vec2(creator.getMouse().getMousePos2().clone()), new Vec2(7,7),EntityType.BIT_ZOMBIE,(short)(EntityType.BIT_ZOMBIE | EntityType.BIT_BULLET | EntityType.BIT_PLAYER)) ;
		}
		//animasyonun çalýþtýrýlmasý
		//animation.animate(AnimationEnum.idle, 15);
	}

	/**
	 * karakterin hareket metodudur. 
	 * karakterin mouseun durumuna göre mause doðru bakmasýný saðlayan matematiksel iþlemleri de içerir.
	 */
	private void move() {
		
		//karakterin fareye doðru dönmesi için yazýlmýþ matematiksel formül
		/*Vec2 mousePosition = creator.getMouse().getMousePos2();
		float radians = (float)Math.atan2(mousePosition.y - position.y, mousePosition.x - position.x) ;
		float degree = (float) (radians * 180 / Math.PI) ; 
		rotation = degree - 90;*/
		//Vec2 degreeVector = new Vec2((float)Math.cos(radians),(float)Math.sin(radians)); 
		//body.applyForceToCenter(new Vec2( speed.x*degreeVector.x , speed.y * degreeVector.y));
		
		//klavye ile karakterin dünyada hareket etmesi
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			direction.y = 1;
			System.out.println("w tuþuna basýldý");
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			direction.y = -1;
			System.out.println("s tuþuna basýldý");
		} else {
			direction.y = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			System.out.println("a tuþuna basýldý");
			direction.x = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			direction.x = 1;
			System.out.println("d tuþuna basýldý");
		} else {
			direction.x = 0;
		}
		if(direction.x == 1) {
			animation.animate(AnimationEnum.walk_right, 4);
			System.out.println("saða doðru karakterin animasyonu etkilenleþtirildi");
		}else if(direction.x == -1) {
			animation.animate(AnimationEnum.walk_left, 4);
			System.out.println("sola doðru karakterin animasyonu etkilenleþtirildi");
		}else if(direction.y == 1) {
			animation.animate(AnimationEnum.walk_up, 4);
			System.out.println("üste doðru karakterin animasyonu etkilenleþtirildi");
		}else if(direction.y == -1) {
			animation.animate(AnimationEnum.walk_down, 4);
			System.out.println("aþaðý doðru karakterin animasyonu etkilenleþtirildi");
		}else {
			animation.animate(AnimationEnum.idle, 4);
			System.out.println("normal duruþ karakterin animasyonu etkilenleþtirildi");
		}
		creator.getCamera().setPosition(this.position.x, this.position.y);
		System.out.println("camera pozisyonu karakterin pozisyonuna tekrar güncellendi");
		body.applyForceToCenter(new Vec2(speed.x * direction.x , speed.y * direction.y));
		System.out.println("karakterin bodysinin merkezine güç uygulandý : " + new Vec2(speed.x * direction.x , speed.y * direction.y));
		System.out.println("karakter pozisyonu güncellendi.");
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
