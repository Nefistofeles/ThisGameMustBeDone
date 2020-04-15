package font;

public class Character {

	private int id;
	private double xMax;
	private double yMax;
	private double textureX ;
	private double textureY ;
	private double textureWidth ;
	private double textureHeight ;
	private double xoffset;
	private double yoffset;
	private double xadvance;


	public Character(int id, double xMax, double yMax, double textureX, double textureY, double textureWidth,
			double textureHeight, double xoffset, double yoffset, double xadvance) {
		
		this.id = id;
		this.xMax = xMax;
		this.yMax = yMax;
		this.textureX = textureX;
		this.textureY = textureY;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xadvance = xadvance;
	}

	public int getId() {
		return id;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMax() {
		return yMax;
	}

	public double getXoffset() {
		return xoffset;
	}

	public double getYoffset() {
		return yoffset;
	}

	public double getXadvance() {
		return xadvance;
	}

	
	public double getTextureX() {
		return textureX;
	}

	public double getTextureY() {
		return textureY;
	}

	public double getTextureWidth() {
		return textureWidth;
	}

	public double getTextureHeight() {
		return textureHeight;
	}

	@Override
	public String toString() {

		return id + " " + xMax + " " + yMax + " " + xoffset + " " + yoffset + " " + xadvance;
	}

}
