package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import contactlisteners.EntityContactListener;
import dataStructure.Texture;
import renderer.Draw;
import renderer.EnableOpenGL;
import renderer.Renderable;
import utils.Camera;
import utils.DisplayManager;
import utils.Maths;

public class EntityRenderer implements Renderable{
	
	private EntityShader shader ;
	private Map<Integer, List<Entity>> entities ;
	private Camera camera ;
	private World world ;
	private Player player ;
	
	public EntityRenderer(Camera camera, World world) {
		this.camera = camera ;
		this.world = world ;
		player = null ;

	}
	

	@Override
	public void init() {
		shader = new EntityShader();
		entities = new HashMap<Integer, List<Entity>>();
		shader.start();
		shader.loadProjectionMatrix(DisplayManager.getProjectionmatrix());
		shader.stop();
		
	}

	@Override
	public void update() {
		Iterator<Integer> iter1 = entities.keySet().iterator();
		while (iter1.hasNext()) {
			int texture = iter1.next();
			
			List<Entity> entitylist = entities.get(texture);
			Iterator<Entity> iter = entitylist.iterator();
			while (iter.hasNext()) {
				Entity e = (Entity) iter.next();
				e.update();
				if(e.isDead()) {
					deleteDirectly(iter, e);
				}
			}
			/*if(entitylist.isEmpty()) {
				iter1.remove();
			}*/
			

		}
	}
	@Override
	public void render() {
		shader.start();
		System.out.println("shader baþlatýldý");
		shader.loadViewMatrix(camera.getViewMatrix());
		EnableOpenGL.enableDepthTest(true);
		System.out.println("deep test aktif edildi");
		//EnableOpenGL.blendFunc(true);
		
		for(int texture : entities.keySet()) {
			List<Entity> entityList = entities.get(texture) ;
			System.out.println("çizilecek entitynin resminin idsi : " + texture);
			Draw.enableTexture(texture);
			System.out.println("texture enable edildi");
			//texture atlasda sadece seçilen resmin ekrana çizdirilmesi için shadera gönderilen bilgiler. Ayrýca animasyon içinde kullanýlýyor.
			
			for(Entity entity : entityList) {
				if(canRender(entity)) {
					Draw.enableVAO(entity.getMesh().getMeshID());
					System.out.println("vao aktil edildi : " + entity.getMesh().getMeshID());
					Draw.enableVertexAttribArray(2);
					System.out.println("vertex attribute deðerleri aktif edildi.");
					shader.loadTextureProperty(entity.getTexture().getNumberOfRows(), entity.getTexture().getNumberOfColumn(), entity.getTexture().getTextureXoffSet(), entity.getTexture().getTextureYoffSet());
					System.out.println("texture deðerleri aktif edildi " + entity.getTexture().getNumberOfRows()+" "+ entity.getTexture().getNumberOfColumn()+" "+ entity.getTexture().getTextureXoffSet()+ " "+entity.getTexture().getTextureYoffSet());
					shader.loadTransformationMatrix(entity.getTransformationMatrix());
					System.out.println("matrix shadera yüklendi");
					if(entity instanceof Wall) {
						Wall wall = (Wall)entity ;
						if(wall.isxDown())
							shader.loadTextureMultiplier(new Vec2(20,1));
						else
							shader.loadTextureMultiplier(new Vec2(1,20));
					}else {
						shader.loadTextureMultiplier(new Vec2(1,1));
					}
					shader.loadWorldPosition(entity.getWorldPosition());
					System.out.println("entity z deðeri shadera yüklendi");
					Draw.renderOptimize(entity.getMesh().getVertexCount());
					System.out.println("index deðerleri kullanýlacak þekilde entity mesh load edildi");
					
					Draw.disableVertexAttribArray(2);
					System.out.println("vertex attributelar disable edildi");
					Draw.disableVAO();
					System.out.println("vao disable edildi.");
				}
			}
		}
		EnableOpenGL.enableDepthTest(false);
		System.out.println("deep test disable edildi");
		//EnableOpenGL.blendFunc(false);
		shader.stop();
		System.out.println("shader durduruldu");
	}
	/**
	 * Gönderilen entitynin kameranýn içerisinde ise çizdir deðil ise çizdirme durumlarýný kontrol eden bir metot.Aslýnda metot uzunluðu eni 80 boyu 60 olan bir kutunun içerisinde olan 
	 * entityleri tespit edip çizilmesi için gereken bayraðý çalýþtýrýyor.
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
	 * Gönderilen entityi hem tutulduðu hashmapden hem de Jbox2ddeki bodysini silmeye yarayan metot.
	 * @param entityDelete	silinecek entity
	 * @param world			entitynin bodysinin silineceði dünya
	 */
	private void deleteDirectly(Iterator<Entity> iter, Entity entity) {
		world.destroyBody(entity.getBody());
		iter.remove();
	}
	public void delete(Entity entityDelete, World world) {
		Iterator<Integer> iter1= entities.keySet().iterator() ;
		while(iter1.hasNext()) {
			int id = iter1.next();
			List<Entity> entitylist = entities.get(id);
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
			/*if (entitylist.isEmpty()) {
				iter1.remove();
			}*/
			
		}
	}
	/**
	 * Tüm entityleri silmeye yarayan metottur.
	 * @param world		dünyadaki tüm bodylari silmek için gönderilen parametre
	 */
	public void clear(World world) {
		Iterator<Integer> iter1= entities.keySet().iterator() ;
		while(iter1.hasNext()) {
			int id = iter1.next();
			List<Entity> entitylist = entities.get(id);
			Iterator<Entity> iter = entitylist.iterator();
			while (iter.hasNext()) {
				Entity entity = iter.next() ;
				if(entity.getBody() != null)
					world.destroyBody(entity.getBody());
				iter.remove();
				
			}
			/*if (entitylist.isEmpty()) {
				iter1.remove();
			}*/
			
		}
	}
	@Override
	public void clean() {
		shader.clean();
		
	}
	public void createList(Texture texture) {
		List<Entity> entitylist = entities.get(texture.getTextureID()) ;
		if(entitylist == null) {
			entitylist = new ArrayList<Entity>();
			entities.put(texture.getTextureID(), entitylist) ;
		}
	}
	/**
	 * Entitynin kaydedilmesi iþlemi. Çizilmesi istenilen entity hashmape kaydedilir.
	 * Bu metot ayrýca entitynin sahip olduðu bodyi de world a kaydeder.
	 * @param entity	çizilmesi istenilen entity parametre olarak gönderiliyor.
	 */
	public void addEntity(Entity entity) {
		List<Entity> entityList = entities.get(entity.getTexture().getTextureID()) ;
		
		if(entityList == null) {
			entityList = new ArrayList<Entity>();
			entityList.add(entity); 
			System.out.println("entity listeye eklendi");
			entities.put(entity.getTexture().getTextureID(), entityList) ;
		}else {
			entityList.add(entity) ;
			System.out.println("entity listeye eklendi");
		}
		if(entity instanceof Player) {
			this.player = (Player) entity ;
			System.out.println("entity player nesnesi");
		}
	}

}
