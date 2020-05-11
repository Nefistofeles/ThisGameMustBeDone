package entities;

import org.jbox2d.common.Vec2;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;

public class Zombie extends Enemy {

	
	public Zombie(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, AnimationData animationData, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition,animationData, creator);
		/*animationData = new AnimationData();
		animationData.addAnimationData(AnimationEnum.idle, 4, 0);
		animationData.addAnimationData(AnimationEnum.walk, 6, 1);
		animation = new Animation(texture, animationData);*/

	}

	@Override
	public void update() {
		//animation.animate(AnimationEnum.idle,15) ;

	}
	/**
	 * Enemyler için belirlenen yürüme metodudur. öklid üçgeni dediðimiz formülü kullanarak belirlenen karakterin pozisyonuna doðru yürür.
	 * Belirli bir mesafede ilerlediði karaktere saldýrý yapar.
	 * @param entity gönderilen entitye doðru yürür.
	 */
	private void move(Entity entity) {
		if(entity != null) {
			Vec2 entityPosition = entity.getPosition();
			float bMinusaY = entityPosition.y - position.y;
			float bMinusaX = entityPosition.x - position.x;
			
			float hipotenus = (float) Math.hypot(bMinusaY, bMinusaX);
			
			float radians = (float) Math.atan2(bMinusaY, bMinusaX);
			float degree = (float) (radians * 180 / Math.PI);
			//rotation = degree - 90;

			if (hipotenus > 20) {
				Vec2 degreeVector = new Vec2((float) Math.cos(radians), (float) Math.sin(radians));
				direction.x = degreeVector.x ;
				direction.y = degreeVector.y ;
				body.applyForceToCenter(new Vec2(speed.x * direction.x, speed.y * direction.y));
				
			}else {
				entity.hurt(this);
				animation.animate(AnimationEnum.idle,15) ;
				body.applyForceToCenter(new Vec2(0, 0));
			}
		}
		

	}

	@Override
	public void attack(Entity entity) {
		move(entity);

	}

	@Override
	public void hurt(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void died() {
		
	}

}
