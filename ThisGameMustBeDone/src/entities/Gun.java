package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jbox2d.common.Vec2;

import animationSystem.AnimationData;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;

public class Gun extends Entity{

	 private Queue<Bullet> bullets ;

	public Gun(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, AnimationData animationData,Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition, animationData,creator);
		bullets = new LinkedList<>();
	}

	@Override
	public void attack(Entity entity) {
		// TODO Auto-generated method stub
		
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
		
		
	}
	public void shot() {
		/*Iterator<Bullet> it = bullets.iterator() ;
		if(it.hasNext()) {
			Bullet b = it.next() ;
			b.isDead = true ;
			it.remove(); 
		}*/
		bullets.remove() ;
		
	}
	public void addBullet(Bullet bullet) {
		bullets.add(bullet) ;
	}
}
