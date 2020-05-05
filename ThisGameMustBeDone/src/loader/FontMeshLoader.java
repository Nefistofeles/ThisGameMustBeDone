package loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import dataStructure.Texture;
import font.FontLoader;
import font.Text;
import utils.Maths;
import font.Character;

public class FontMeshLoader {

	private FontLoader font ;
	private Loader loader ;
	private Texture texture ;
	
	/**
	 *  fontun gerekli bilgileri alýndýktan sonra {@code Text} openglin bunu ekran çizdirebilmesi için gereken koordinat bilgileri bu sýnýfta girilir.
	 * @param fontName		yüklenecek font resminin ismi
	 * @param loader		vao ve vbo kaydý için gereken sýnýf
	 */
	public FontMeshLoader(String fontName, Loader loader) {
		font = new FontLoader();
		font.loadFont(fontName);
		this.loader = loader ;
		texture = loader.getTextureLoader().loadTexture(font.getTextureFileName(), TextureLoader.TextureLinear, 0) ;
	}
	
	/**
	 * textin içindeki kullanýcýnýn girdiði string deðeri bu metotta karakterlerine ayrýlýp {@code FontLoader} kýsmýnda okunan her karaterin deðerine göre burada yerine koyulup gerekli iþlemler yapýlýr.
	 * TODO : yeni satýra geçme satýr sonu güncellenmeli
	 * @param text	kullanýcýnýn ekranda görmesini istediði yazýnýn bilgisini barýndýran sýnýf.
	 */
	public void loadMeshforFont(Text text) {
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		Character c = null ;
		Map<Integer, Character> characters = font.getCharacters() ;
		char[] parse = text.getText().toCharArray() ;
		
		double originX = 0.5f - characters.get((int)parse[0]).getXoffset() ;
		double originY = 0.5f - characters.get((int)parse[0]).getYoffset();

		for(int i = 0 ; i < parse.length ; i++) {
			c = characters.get((int)parse[i]) ;
			loadVertices(vertices, c, originX, originY);
			loadTextureCoords(textureCoords, c);
			originX += c.getXadvance();
		}
		
		text.setMesh(loader.getMeshLoader().loadMesh(listToArray(vertices), listToArray(textureCoords)));
		
	}
	/**
	 * opengl koordinat deðerlerinin düzenlendiði ve yazýldýðý metottur.
	 * @param data		koordinatlarýn kaydedileceði liste
	 * @param c			kaydedilecek karakter
	 * @param originX	yazýnýn baþladýðý yerin x pozisyonu
	 * @param originY	yazýnýn baþladýðý yerin y pozisyonu
	 */
	private void loadVertices(List<Float> data,Character c,double originX, double originY) {
		double x = originX + (c.getXoffset());
		double y = originY + (c.getYoffset());
		double width = x + (c.getxMax());
		double height = y + (c.getyMax());

		double xC = (2 * x) - 1;
		double yC = (-2 * y) + 1;
		double wC = (2 * width) - 1;
		double hC = (-2 * height) + 1;
		Maths.addMeshAttachment(data, xC, yC, wC, hC);
	}
	/**
	 * yüklenecek harflerin üzerine basýlacak texturelarýn koordinatlarý.Bunlarda vbo þeklinde yüklenecek
	 * @param textureCoords		texture koordinatlarýnýn tutulacaðý liste
	 * @param c					yüklenecek karakter
	 */
	private void loadTextureCoords(List<Float> textureCoords, Character c) {
		Maths.addMeshAttachment(textureCoords, c.getTextureX(), c.getTextureY(), c.getTextureX() + c.getTextureWidth(),c.getTextureY() + c.getTextureHeight());
	}
	/**
	 * listeyi arraye dönüþtüren metot
	 * @param data	datanýn liste hali
	 * @return		datanýn array hali
	 */
	private float[] listToArray(List<Float> data) {
		float[] returndata = new float[data.size()] ;
		for(int i = 0 ; i < data.size() ; i++) {
			returndata[i] = data.get(i) ;
		}
		
		return returndata ;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
