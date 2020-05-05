package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;
import renderer.Draw;
import renderer.EnableOpenGL;
import renderer.Renderable;
import utils.Camera;
import utils.DisplayManager;
import utils.Maths;

public class EntityRenderer implements Renderable{
	
	private EntityShader shader ;
	private Map<Texture, List<Entity>> entities ;
	private List<Body> worldPhysics ;
	private Camera camera ;
	private Player player ;
	private World world ;
	
	public EntityRenderer(Camera camera, World world) {
		this.camera = camera ;
		this.world = world ;
	}
	

	@Override
	public void init() {
		shader = new EntityShader();
		entities = new HashMap<Texture, List<Entity>>();
		worldPhysics = new ArrayList<Body>();
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
				
				if(entity instanceof Enemy) {
					entity.attack(player);
				}
				if(entity.isDead()) {
					delete(entity, world);
				}
				
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
			//texture atlasda sadece se�ilen resmin ekrana �izdirilmesi i�in shadera g�nderilen bilgiler. Ayr�ca animasyon i�inde kullan�l�yor.
			shader.loadTextureProperty(texture.getNumberOfRows(), texture.getNumberOfColumn(), texture.getTextureXoffSet(), texture.getTextureYoffSet());
			
			for(Entity entity : entityList) {
				if(canRender(entity)) {
					Draw.enableVAO(entity.getMesh().getMeshID());
					Draw.enableVertexAttribArray(2);
					
					shader.loadTransformationMatrix(entity.getTransformationMatrix());
					shader.loadWorldPosition(entity.getWorldPosition());
					Draw.renderOptimize(entity.getMesh().getVertexCount());
					
					Draw.disableVertexAttribArray(2);
					Draw.disableVAO();
				}
			}
		}
		
		//EnableOpenGL.blendFunc(false);
		shader.stop();
		
	}
	/**
	 * G�nderilen entitynin kameran�n i�erisinde ise �izdir de�il ise �izdirme durumlar�n� kontrol eden bir metot.Asl�nda metot uzunlu�u eni 80 boyu 60 olan bir kutunun i�erisinde olan 
	 * entityleri tespit edip �izilmesi i�in gereken bayra�� �al��t�r�yor.
	 * @param entity
	 * @return
	 */
	private boolean canRender(Entity entity) {
		if(entity.getPosition().x + Maths.min.x * entity.getScale().x> camera.getPosition().x + 80 ||
				entity.getPosition().x +Maths.max.x * entity.getScale().x < camera.getPosition().x - 80) {
			return false ;
		}
		if(entity.getPosition().y + Maths.min.y * entity.getScale().y> camera.getPosition().y + 60 ||
				entity.getPosition().y + Maths.max.y * entity.getScale().y< camera.getPosition().y - 60) {
			return false ;
		}
		return true ;
	}
	/**
	 * G�nderilen entityi hem tutuldu�u hashmapden hem de Jbox2ddeki bodysini silmeye yarayan metot.
	 * @param entityDelete	silinecek entity
	 * @param world			entitynin bodysinin silinece�i d�nya
	 */
	public void delete(Entity entityDelete, World world) {
		Iterator<Texture> iter1= entities.keySet().iterator() ;
		while(iter1.hasNext()) {
			Texture texture = iter1.next();
			List<Entity> entitylist = entities.get(texture);
			Iterator<Entity> iter = entitylist.iterator();
			while (iter.hasNext()) {
				Entity entity = iter.next() ;
				if(entity == entityDelete) {
					if(entity.getBody() != null) {
						world.destroyBody(entity.getBody());
					}
					
					iter.remove();
				}
			}
			if (entitylist.isEmpty()) {
				iter1.remove();
			}
			
		}
	}
	/**
	 * T�m entityleri silmeye yarayan metottur.
	 * @param world		d�nyadaki t�m bodylari silmek i�in g�nderilen parametre
	 */
	public void clear(World world) {
		Iterator<Texture> iter1= entities.keySet().iterator() ;
		while(iter1.hasNext()) {
			Texture texture = iter1.next();
			List<Entity> entitylist = entities.get(texture);
			Iterator<Entity> iter = entitylist.iterator();
			while (iter.hasNext()) {
				Entity entity = iter.next() ;
				if(entity.getBody() != null)
					world.destroyBody(entity.getBody());
				iter.remove();
				
			}
			if (entitylist.isEmpty()) {
				iter1.remove();
			}
			
		}
		for(int i = 0 ; i < worldPhysics.size(); i++) {
			world.destroyBody(worldPhysics.get(i));
		}
	}
	@Override
	public void clean() {
		shader.clean();
		
	}
	/**
	 * Entitynin kaydedilmesi i�lemi. �izilmesi istenilen entity hashmape kaydedilir.
	 * Bu metot ayr�ca entitynin sahip oldu�u bodyi de world a kaydeder.
	 * @param entity	�izilmesi istenilen entity parametre olarak g�nderiliyor.
	 */
	public void addEntity(Entity entity) {
		List<Entity> entityList = entities.get(entity.getTexture()) ;
		
		if(entityList == null) {
			entityList = new ArrayList<Entity>();
			entityList.add(entity); 
			worldPhysics.add(entity.getBody()) ;
			entities.put(entity.getTexture(), entityList) ;
		}else {
			entityList.add(entity) ;
			worldPhysics.add(entity.getBody()) ;
		}
		if(entity instanceof Player) {
			this.player = (Player) entity ;
		}
	}

}
