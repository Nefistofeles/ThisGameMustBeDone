package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;
import entities.Entity;
import gameEntity.Camera;
import shaders.EntityShader;
import utils.DisplayManager;

public class EntityRenderer implements Renderable{
	
	private EntityShader shader ;
	private Map<Texture, List<Entity>> entities ;
	private Camera camera ;
	
	public EntityRenderer(Camera camera) {
		this.camera = camera ;
	}
	
	@Override
	public void init() {
		shader = new EntityShader();
		entities = new HashMap<Texture, List<Entity>>();
		shader.start();
		shader.loadProjectionMatrix(DisplayManager.getProjectionmatrix());
		shader.stop();
		
	}

	@Override
	public void update() {
		for(Texture texture : entities.keySet()) {
			List<Entity> entitylist = entities.get(texture) ;
			for(Entity entity : entitylist) {
				entity.update();
			}
		}
	}

	@Override
	public void render() {
		shader.start();
		shader.loadViewMatrix(camera.getViewMatrix());
		//EnableOpenGL.blendFunc(true);
		for(Texture texture : entities.keySet()) {
			List<Entity> entityList = entities.get(texture) ;
			
			Draw.enableTexture(texture);
			shader.loadTextureProperty(texture.getNumberOfRows(), texture.getNumberOfColumn(), texture.getTextureXoffSet(), texture.getTextureYoffSet());
			
			for(Entity entity : entityList) {

				Draw.enableVAO(entity.getMesh().getMeshID());
				Draw.enableVertexAttribArray(2);
				
				shader.loadTransformationMatrix(entity.getTransformationMatrix());
				shader.loadWorldPosition(entity.getWorldPosition());
				Draw.renderOptimize(entity.getMesh().getVertexCount());
				
				Draw.disableVertexAttribArray(2);
				Draw.disableVAO();
				
			}
		}
		
		//EnableOpenGL.blendFunc(false);
		shader.stop();
		
	}

	@Override
	public void clean() {
		shader.clean();
		
	}
	
	public void addEntity(Entity entity) {
		List<Entity> entityList = entities.get(entity.getTexture()) ;
		
		if(entityList == null) {
			entityList = new ArrayList<Entity>();
			entityList.add(entity); 
			entities.put(entity.getTexture(), entityList) ;
		}else {
			entityList.add(entity) ;
		}
	}

}
