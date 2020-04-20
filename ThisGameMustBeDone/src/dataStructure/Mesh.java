package dataStructure;

public class Mesh {

	private int meshID ;
	private int vertexCount ;
	
	public Mesh(int meshID, int vertexCount) {
		
		this.meshID = meshID;
		this.vertexCount = vertexCount;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Mesh mesh = new Mesh(this.meshID, this.vertexCount);
		return mesh ;
	}
	public Mesh getClone() {
		Mesh mesh = null ;
		try {
			mesh = (Mesh) clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mesh ;
	}
	public int getMeshID() {
		return meshID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	
	
	
}
