package loader;

public class Loader {

	private MeshLoader meshLoader ;
	private TextureLoader textureLoader ;
	
	public Loader() {
	
		this.meshLoader = new MeshLoader();
		this.textureLoader = new TextureLoader();
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
	
	
}
