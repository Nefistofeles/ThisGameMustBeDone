package entities;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;

public class Background extends Entity{

	public Background(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition,
			Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition, creator);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		
	}

}
