package loader;

import org.jbox2d.dynamics.World;

public class Loader {

	private MeshLoader meshLoader ;
	private TextureLoader textureLoader ;
	private OBJFileLoader objLoader ;
	private PhysicsDataLoader physicsLoader;
	
	/**
	 * t�m loader s�n�flar�n�n bir arada topland��� kullan��l� bir ara s�n�f.
	 * @param world		Jbox2d y�netici s�n�f�
	 */
	
	public Loader(World world) {
	
		this.meshLoader = new MeshLoader();
		this.textureLoader = new TextureLoader();
		objLoader = new OBJFileLoader(meshLoader);
		physicsLoader = new PhysicsDataLoader(world);
	}

	public MeshLoader getMeshLoader() {
		return meshLoader;
	}

	public TextureLoader getTextureLoader() {
		return textureLoader;
	}
	
	public void clean() {
		meshLoader.clean();
		textureLoader.clean();
	}
	
	public OBJFileLoader getObjLoader() {
		return objLoader;
	}
	public PhysicsDataLoader getPhysicsLoader() {
		return physicsLoader;
	}
}
