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
	 * resim eðer atlas ise yani birden fazla resim içeriyorsa bölmek için girilmesi gereken deðerler.
	 * @param numberOfRows		satýrda kaç tane resim var
	 * @param numberOfColumn	oluþtuðu sütun sayýsý
	 */
	public void setTextureRowColumn(int numberOfRows, int numberOfColumn) {
		this.numberOfRows = numberOfRows ;
		this.numberOfColumn = numberOfColumn ;
	}
	/**
	 * parçalanmýþ resmin hangi indextekinin gösterildiðini bilgisini alan metottur.
	 * @param textureIndexX		sütun numarasý
	 * @param textureIndexY		satýr numarasý
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
	 * Temel bir texture atlas içinde bulunan birden fazla texture için parçalama metodu. textureIndexX istenilen index deðeri numberOfRows toplam satýrdaki resim sayýsýný ifade ediyor.
	 * 
	 * @return textureýn x pozisyonu
	 */
	public float getTextureXoffSet() {
		int column = textureIndexX % numberOfRows ;
		return (float)column / (float) numberOfRows ;
	}
	/**
	 * 
	 * @return textureýn y pozisyoun
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
