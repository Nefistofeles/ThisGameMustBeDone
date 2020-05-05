package entities;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;
import utils.DisplayManager;

public class Bullet extends Entity{
	
	private float time ;

	public Bullet(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition, creator);
		this.speed.x = 100 ;
		this.speed.y = 100 ;
		
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Entity entity = new Bullet(this.mesh, texture.getClone(), new Vec2(this.position.x, this.position.y),this.rotation , new Vec2(this.scale.x , this.scale.y), this.worldPosition,creator) ;
		return entity;
	}

	@Override
	protected void attack(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * kendine özgü hareket metodu
	 */
	private void move() {
		body.applyForceToCenter(new Vec2(speed.x * direction.x , speed.y * direction.y));
	}

	@Override
	protected void hurt(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void died() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void update() {
		move();
		time += DisplayManager.getFrameTime() ;
		if(time > 2) {
			this.isDead = true ;
		}
		
	}

}
