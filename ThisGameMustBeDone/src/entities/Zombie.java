package entities;

import org.jbox2d.common.Vec2;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;

public class Zombie extends Enemy {
	
	public Zombie(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition) {
		super(mesh, texture, position, rotation, scale, worldPosition);
		animationData = new AnimationData();
		animationData.addAnimationData(AnimationEnum.idle, 4, 0);
		animationData.addAnimationData(AnimationEnum.run, 6, 1);
		animation = new Animation(texture, animationData);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private void move(Entity entity) {
		Vec2 entityPosition = entity.getPosition();
		float bMinusaY = entityPosition.y - position.y;
		float bMinusaX = entityPosition.x - position.x;
		
		float hipotenus = (float) Math.hypot(bMinusaY, bMinusaX);
		
		float radians = (float) Math.atan2(bMinusaY, bMinusaX);
		float degree = (float) (radians * 180 / Math.PI);
		rotation = degree - 90;

		if (hipotenus > 20) {
			Vec2 degreeVector = new Vec2((float) Math.cos(radians), (float) Math.sin(radians));
			direction.x = degreeVector.x ;
			direction.y = degreeVector.y ;
			body.applyForceToCenter(new Vec2(speed.x * direction.x, speed.y * direction.y));
			
		}else {
			entity.hurt(this);
			animation.animate(AnimationEnum.run) ;
			body.applyForceToCenter(new Vec2(0, 0));
		}

	}

	@Override
	protected void attack(Entity entity) {
		move(entity);

	}

	@Override
	protected void hurt(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void died() {
		// TODO Auto-generated method stub

	}

}
