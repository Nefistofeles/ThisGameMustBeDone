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

	/**
	 * font program� : {@link https://github.com/libgdx/libgdx/wiki/Hiero}
	 * hierro ile olu�turulan fontun �zelliklerinin okunup her bir karakterin �zelli�inin kaydedildi�i s�n�ft�r.
	 * @param id			ascii karakter idsi
	 * @param xMax			opengl taban�nda olu�turulan karakterin x ekseninin uzunlu�u
	 * @param yMax			opengl taban�nda olu�turulan karakterin y ekseninin uzunlu�u
	 * @param textureX		font atlasda texture se�ilmesi i�in gereken font atlas x koordinat�
	 * @param textureY		font atlasda texture se�ilmesi i�in gereken font atlas y koordinat�
	 * @param textureWidth	font atlasda texture se�ilmesi i�in gereken font atlas geni�lik koordinat�
	 * @param textureHeight font atlasda texture se�ilmesi i�in gereken font atlas uzunluk koordinat�
	 * @param xoffset		karakterin boyutuna g�re x eksenindeki pozisyonu
	 * @param yoffset		karakterin boyutuna g�re y eksenindeki pozisyonu
	 * @param xadvance		olu�turulan harften sonra di�er harfin ne kadar uzakl�kta ba�layaca��n� belirten k�s�m
	 */

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
