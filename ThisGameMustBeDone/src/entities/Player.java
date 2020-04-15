package entities;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;

import com.sun.javafx.scene.traversal.Direction;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;

public class Player extends Entity{
	
	private AnimationData data ;
	private Animation animation ;
	private Vec2 speed ;
	private Vec2 walkDirection ;


	public Player(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition) {
		super(mesh, texture, position, rotation, scale, worldPosition);
		data = new AnimationData();
		data.addAnimationData(AnimationEnum.idle, 4, 0);
		data.addAnimationData(AnimationEnum.run, 6, 1);
		animation = new Animation(texture, data) ;
		
		
		speed = new Vec2(1,1) ;
		walkDirection = new Vec2(0,0) ;

	}

	@Override
	public void update() {
		move();
		
	}
	private void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			walkDirection.y = 1 ;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			walkDirection.y = -1 ;
		}else {
			walkDirection.y = 0 ;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			walkDirection.x = -1 ;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			walkDirection.x = 1 ;
		}else {
			walkDirection.x = 0 ;
		}
		animation.animate(AnimationEnum.run) ;
		
		
		
		body.applyForceToCenter(new Vec2(speed.x * walkDirection.x, speed.y * walkDirection.y));
		
		
	}

}
