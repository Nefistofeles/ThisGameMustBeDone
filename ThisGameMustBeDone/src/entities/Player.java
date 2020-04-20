package entities;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.sun.javafx.scene.traversal.Direction;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.DisplayManager;
import utils.MouseOrtho;

public class Player extends Entity {

	private Creator creator ;
	private Entity gun ;

	public Player(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition);
		this.creator = creator ;
		animationData = new AnimationData();
		animationData.addAnimationData(AnimationEnum.idle, 4, 0);
		animationData.addAnimationData(AnimationEnum.run, 6, 1);
		animation = new Animation(texture, animationData);

		
		this.speed.x = 300 ;
		this.speed.y = 300 ;

	}

	@Override
	public void update() {
		move();
		if(Mouse.isButtonDown(0)){
			
		}
	}

	private void move() {
		
		Vec2 mousePosition = creator.getMouse().getMousePos2();
		float radians = (float)Math.atan2(mousePosition.y - position.y, mousePosition.x - position.x) ;
		float degree = (float) (radians * 180 / Math.PI) ; 
		rotation = degree - 90;
		//Vec2 degreeVector = new Vec2((float)Math.cos(radians),(float)Math.sin(radians)); 
		//body.applyForceToCenter(new Vec2( speed.x*degreeVector.x , speed.y * degreeVector.y));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			direction.y = 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			direction.y = -1;
		} else {
			direction.y = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			direction.x = -1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			direction.x = 1;
		} else {
			direction.x = 0;
		}
		animation.animate(AnimationEnum.idle);

		body.applyForceToCenter(new Vec2(speed.x * direction.x , speed.y * direction.y));
		
	}

	@Override
	protected void attack(Entity entity) {
		
	}

	@Override
	protected void hurt(Entity entity) {
		System.out.println("yardim");
		
	}

	@Override
	protected void died() {
		
		
	}
	public void setGun(Entity gun) {
		this.gun = gun ;
	}

}
