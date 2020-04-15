package fileParser;

public class ObjFileData {

	private float[] vertices ;
	private float[] textureCoords ;
	private int[] indices ;
	
	public ObjFileData(float[] vertices, float[] textureCoords, int[] indices) {
		
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.indices = indices;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public int[] getIndices() {
		return indices;
	}
	
	
	
	
}
