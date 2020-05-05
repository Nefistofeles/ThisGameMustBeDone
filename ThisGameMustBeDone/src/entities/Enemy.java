package entities;

import org.jbox2d.common.Vec2;

import animationSystem.AnimationData;
import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Creator;

public abstract class Enemy extends Entity{
	

	public Enemy(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, AnimationData animationData, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition,animationData,creator);
		
	}
	public Enemy(Mesh mesh, Texture texture, Vec2 position, float rotation, Vec2 scale, float worldPosition, Creator creator) {
		super(mesh, texture, position, rotation, scale, worldPosition, creator);
		
	}
}
