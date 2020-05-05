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
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Texture texture = new Texture(this.textureID) ;
		texture.setTextureRowColumn(this.numberOfRows, this.numberOfColumn);
		return texture ;
	}
	
	public Texture getClone() {
		Texture texture = null ;
		try {
			return (Texture) this.clone() ;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this ;
	}
	/**
	 * resim e�er atlas ise yani birden fazla resim i�eriyorsa b�lmek i�in girilmesi gereken de�erler.
	 * @param numberOfRows		sat�rda ka� tane resim var
	 * @param numberOfColumn	olu�tu�u s�tun say�s�
	 */
	public void setTextureRowColumn(int numberOfRows, int numberOfColumn) {
		this.numberOfRows = numberOfRows ;
		this.numberOfColumn = numberOfColumn ;
	}
	/**
	 * par�alanm�� resmin hangi indextekinin g�sterildi�ini bilgisini alan metottur.
	 * @param textureIndexX		s�tun numaras�
	 * @param textureIndexY		sat�r numaras�
	 */
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
	/**
	 * Temel bir texture atlas i�inde bulunan birden fazla texture i�in par�alama metodu. textureIndexX istenilen index de�eri numberOfRows toplam sat�rdaki resim say�s�n� ifade ediyor.
	 * 
	 * @return texture�n x pozisyonu
	 */
	public float getTextureXoffSet() {
		int column = textureIndexX % numberOfRows ;
		return (float)column / (float) numberOfRows ;
	}
	/**
	 * 
	 * @return texture�n y pozisyoun
	 */
	public float getTextureYoffSet() {
		int row = textureIndexY % numberOfColumn ;
		return (float) row / (float) numberOfColumn ;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public int getNumberOfColumn() {
		return numberOfColumn;
	}
}
