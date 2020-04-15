package entities;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import dataStructure.Texture;

public class Zombie extends Enemy{

	public Zombie(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition) {
		super(mesh, texture, position, rotation, scale, worldPosition);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
