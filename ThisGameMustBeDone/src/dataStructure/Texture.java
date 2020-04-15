package dataStructure;

public class Texture {

	private final int textureID ;
	private int numberOfRows ;
	private int numberOfColumn ;
	private int textureIndexX ;
	private int textureIndexY ;
	

	public Texture(int textureID) {
		this.textureID = textureID;
		numberOfRows = 1 ;
		numberOfColumn = 1 ;
		textureIndexX = 0 ;
		textureIndexY = 0 ;
	}

	public void setTextureRowColumn(int numberOfRows, int numberOfColumn) {
		this.numberOfRows = numberOfRows ;
		this.numberOfColumn = numberOfColumn ;
	}
	public void setTextureIndex(int textureIndexX , int textureIndexY) {
		this.textureIndexX = textureIndexX ;
		this.textureIndexY = textureIndexY ;
	}
	public void setTextureIndexX(int textureIndexX) {
		this.textureIndexX = textureIndexX;
	}
	public void setTextureIndexY(int textureIndexY) {
		this.textureIndexY = textureIndexY;
	}
	public int getTextureID() {
		return textureID;
	}
	public int getTextureIndexX() {
		return textureIndexX;
	}
	public int getTextureIndexY() {
		return textureIndexY;
	}
	public float getTextureXoffSet() {
		int column = textureIndexX % numberOfRows ;
		return (float)column / (float) numberOfRows ;
	}
	public float getTextureYoffSet() {
		int row = textureIndexY % numberOfColumn ;
	//	int row = (int)Math.floor(textureIndexY / texture.getNumberOfRows()) ;
		return (float) row / (float) numberOfColumn ;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public int getNumberOfColumn() {
		return numberOfColumn;
	}
}
