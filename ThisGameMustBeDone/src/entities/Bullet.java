package entities;

import org.jbox2d.common.Vec2;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.DisplayManager;

public class Bullet extends Entity {

	private float time;

	public Bullet(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition,
			AnimationData animationData, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition, animationData, creator);
		this.speed.x = 1000;
		this.speed.y = 1000;
	}

	@Override
	public void attack(Entity entity) {
		// TODO Auto-generated method stub

	}

	/**
	 * kendine özgü hareket metodu
	 */
	private void move() {
		Vec2 entityPosition = creator.getMouse().getMousePos2() ;
		float bMinusaY = entityPosition.y - position.y;
		float bMinusaX = entityPosition.x - position.x;

		float hipotenus = (float) Math.hypot(bMinusaY, bMinusaX);

		float radians = (float) Math.atan2(bMinusaY, bMinusaX);
		float degree = (float) (radians * 180 / Math.PI);
		rotation = degree - 90;
		body.applyForceToCenter(new Vec2(speed.x * direction.x, speed.y * direction.y));
		if (hipotenus > 20) {
			Vec2 degreeVector = new Vec2((float) Math.cos(radians), (float) Math.sin(radians));
			direction.x = degreeVector.x ;
			direction.y = degreeVector.y ;
			body.applyForceToCenter(new Vec2(speed.x * direction.x, speed.y * direction.y));
			
		}else {
			body.applyForceToCenter(new Vec2(0, 0));
		}
	}

	@Override
	public void hurt(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void died() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		move();
		time += DisplayManager.getFrameTime();
		if (time > 2) {
			this.isDead = true;
		}

	}

}
