package loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import dataStructure.EntityData;
import dataStructure.Mesh;
import dataStructure.Texture;
import entities.Entity;
import entities.Player;
import renderer.Renderer;
import utils.Camera;
import utils.MouseOrtho;

public class Creator {

	private Loader loader;
	private World world;
	private MouseOrtho mouse;
	private Camera camera;
	private Renderer renderer ;
	private LoadCharacterInformation lc ;
	
	private Map<String, Mesh> meshes ;
	private Map<String,Texture> textures ;
	private Map<String, EntityData> entityDatas ;
	/**
	 * Bir varlýk oluþturulacak ise bu sýnýfa ihtiyacý vardýr.
	 * @param world		varlýðýn dünyadaki fizik özelliðinin kaydedildiði sýnýf.
	 */

	public Creator(World world) {
		this.world = world ;
		meshes = new HashMap<String, Mesh>();
		textures = new HashMap<String, Texture>();
		entityDatas = new HashMap<String, EntityData>();
		loader = new Loader(world);
		camera = new Camera();
		mouse = new MouseOrtho(camera);

		renderer = new Renderer(loader, mouse, camera, world);
		lc = new LoadCharacterInformation(this) ;
	}
	
	public Entity loadEntity(String name, Vec2 position, Vec2 scale, short categoryBits, short maskBits) {
		EntityData entitydata = entityDatas.get(name) ;
		if(entitydata == null) {
			entitydata = lc.loadCharacter(name) ;
			entityDatas.put(name, entitydata) ;
			
		}
		Entity entity = entitydata.loadEntity(position, scale,categoryBits, maskBits); 
		renderer.addEntity(entity);
		return entity ;
		
	}
	/**
	 * Birden fazla mesh bilgisi dosyasý okunmasýn diye bir hashmape kaydediliyor. Gerektiði zaman klonlayýp kullanýr.
	 * @param name		okunacak .obj tabanlý dosyanýn ismi
	 * @return			istenilen mesh deðeri
	 */
	public Mesh loadMesh(String name) {
		Mesh meshdata = meshes.get(name) ;
		if(meshdata == null) {
			meshdata = loader.getObjLoader().loadObjMeshData(name);
			meshes.put(name, meshdata) ;
			return meshdata ;
		}else {
			System.out.println("this mesh already exist");
			return meshdata.getClone() ;
			
		}
		
	}
	/**
	 * Birden fazla textureýn okunmasýný engelleyip bir hashmape kaydeden metottur. Bu metot gerektiðin zaman olan textureý klonlayýp kullanýlmasýný saðlar.
	 * @param name		okunacak .png tabanlý dosyanýn ismi
	 * @param nearest	opengl texture özelliði. Texture uzaklaþtýkça oluþan bulanýklaþma gerçekleþsin mi
	 * @param bias		opengl texture özelliði. Texture uzaklaþtýkça ne kadar net olsun. Genelde texturelarda oluþan çatallaþma yani kesikli çizgileri düzeltmek için kullanýlýr.
	 * @return
	 */
	public Texture loadTexture(String name,int nearest, float bias) {
		Texture texturedata = textures.get(name) ;
		if(texturedata == null) {
			texturedata =loader.getTextureLoader().loadTexture(name, nearest, bias) ;
			textures.put(name, texturedata) ;
			return texturedata ;
		}else {
			System.out.println("this mesh already exist");
			return texturedata.getClone() ;
		}
		
	}
	/**
	 * Birden fazla textureýn okunmasýný engelleyip bir hashmape kaydeden metottur. Bu metot gerektiðin zaman olan textureý klonlayýp kullanýlmasýný saðlar.
	 * @param name		okunacak .png tabanlý dosyanýn ismi
	 * @param nearest	opengl texture özelliði. Texture uzaklaþtýkça oluþan bulanýklaþma gerçekleþsin mi
	 * @param bias		opengl texture özelliði. Texture uzaklaþtýkça ne kadar net olsun. Genelde texturelarda oluþan çatallaþma yani kesikli çizgileri düzeltmek için kullanýlýr.
	 * @param row		texture atlas satýrda bulunan toplam texture
	 * @param column	texture atlas satýr sayýsý
	 * @return
	 */
	public Texture loadTexture(String name,int nearest, float bias, int row, int column) {
		Texture texturedata = textures.get(name) ;
		if(texturedata == null) {
			texturedata = loader.getTextureLoader().loadTexture(name, nearest, bias) ;
			texturedata.setTextureRowColumn(row, column);
			textures.put(name, texturedata) ;
			return texturedata ;
		}else {
			System.out.println("this mesh already exist");
			Texture texture = texturedata.getClone() ;
			texture.setTextureRowColumn(row, column);
			return texture ;
		}
	}

	public Mesh getMeshClone(String name) {
		return meshes.get(name).getClone() ;
	}
	public Texture getTextureClone(String name) {
		return textures.get(name).getClone() ;
	}

	public Renderer getRenderer() {
		return renderer;
	}
	public Loader getLoader() {
		return loader;
	}

	public MouseOrtho getMouse() {
		return mouse;
	}

	public World getWorld() {
		return world;
	}
	
	public Camera getCamera() {
		return camera;
	}

}
