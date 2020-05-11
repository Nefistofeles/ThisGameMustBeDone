package contactlisteners;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import entities.Bullet;
import entities.Enemy;
import entities.Entity;
import entities.Player;

public class EntityContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Entity entity1 = (Entity) contact.getFixtureA().getBody().getUserData() ;
		Entity entity2 = (Entity) contact.getFixtureB().getBody().getUserData() ;

		if(contact.isTouching()) {
			
		}
		if(entity1 instanceof Bullet && entity2 instanceof Enemy) {
			entity2.setDead(true);
			entity1.setDead(true);
		}else if(entity2 instanceof Bullet && entity1 instanceof Enemy) {
			entity1.setDead(true);
			entity2.setDead(true);
		}else if(entity1 instanceof Player && entity2 instanceof Bullet) {
			
		}else if(entity2 instanceof Bullet && entity1 instanceof Player) {
			
		}
		
		
			
	}

	@Override
	public void endContact(Contact contact) {
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
