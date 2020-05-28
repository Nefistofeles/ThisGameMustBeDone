package entities;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;

import animationSystem.Animation;
import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.DisplayManager;

public class Zombie extends Enemy {

	private float time ;
	public Zombie(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, AnimationData animationData, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition,animationData, creator);
		/*animationData = new AnimationData();
		animationData.addAnimationData(AnimationEnum.idle, 4, 0);
		animationData.addAnimationData(AnimationEnum.walk, 6, 1);
		animation = new Animation(texture, animationData);*/
		time = 0 ;
	}


	@Override
	public void update() {
		//animation.animate(AnimationEnum.idle,15) ;
		if(this.health <= 0) {
			died(); 
		}else {
			if(this.player != null) {
				attack(player);
			}
		}
	}
	/**
	 * Enemyler için belirlenen yürüme metodudur. öklid üçgeni dediðimiz formülü kullanarak belirlenen karakterin pozisyonuna doðru yürür.
	 * Belirli bir mesafede ilerlediði karaktere saldýrý yapar.
	 * @param entity gönderilen entitye doðru yürür.
	 */
	private void move() {
		if(player != null) {
			Vec2 entityPosition = player.getPosition();
			float bMinusaY = entityPosition.y - position.y;
			float bMinusaX = entityPosition.x - position.x;
			
			float hipotenus = (float) Math.hypot(bMinusaY, bMinusaX);
			
			float radians = (float) Math.atan2(bMinusaY, bMinusaX);
			float degree = (float) (radians * 180 / Math.PI);
			//rotation = degree - 90;

			if (hipotenus > 12) {
				Vec2 degreeVector = new Vec2((float) Math.cos(radians), (float) Math.sin(radians));
				direction.x = degreeVector.x ;
				direction.y = degreeVector.y ;
				body.applyForceToCenter(new Vec2(speed.x * direction.x, speed.y * direction.y));
				animation.animate(AnimationEnum.walk_left,15) ;
			}else {
				player.hurt(this);
				animation.animate(AnimationEnum.idle,15) ;
				body.applyForceToCenter(new Vec2(0, 0));
			}
			
			
		}
		

	}

	@Override
	public void attack(Entity entity) {
		move();

	}

	@Override
	public void hurt(Entity entity) {
		if(health > 0)
			health -= 10 ;

	}

	@Override
	public void died() {
		animation.animate(AnimationEnum.dead, 15) ;
		time += DisplayManager.getFrameTime() ;
		if(time >= 0.75f) {
			this.isDead = true ;
			this.player.setScore(this.player.getScore() + 20 );
		}
	}

}
