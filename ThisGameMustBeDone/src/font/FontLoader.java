package font;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.Display;

public class FontLoader {

	private Map<Integer, Character> characters;
	private Map<String, String> lineParser;
	private int size;
	private int padding;
	private int lineHeight;
	private Vec2 textureScaleWH;
	private String textureFileName;
	private int charCount;
	
	/**
	 * fontun .fnt tabanlý dosyadan bilgilerinin okunamasýna olanak saðlayan sýnýftýr.
	 */

	public FontLoader() {
		characters = new HashMap<Integer, Character>();
		lineParser = new HashMap<String, String>();
	}

	/**
	 * basit dosya okuma sýnýfý
	 * @param fileName	gönderilecek .fnt tabanlý dosyanýn ismi
	 */
	public void loadFont(String fileName) {

		try {
			InputStream in = Class.class.getResourceAsStream("/res/" + fileName + ".fnt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			parseFile(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gereken özelliklere göre dosyadaki bilgilerin parse edilmesini saðlayan sýnýf
	 * @param reader		parse edilmesi için BufferedReader sýnfýnýn gönderilesi gerekiyor.
	 * @throws Exception	olabilir hatalarýn çaðrýlan metoda fýrlatýlmasý
	 */
	private void parseFile(BufferedReader reader) throws Exception {
		splitLine(reader);
		size = Integer.parseInt(lineParser.get("size"));
		String[] paddingParser = lineParser.get("padding").split(",");
		padding = Integer.parseInt(paddingParser[0]);

		splitLine(reader);
		lineHeight = Integer.parseInt(lineParser.get("lineHeight")) - 2 * padding;
		textureScaleWH = new Vec2(Integer.parseInt(lineParser.get("scaleW")),
				Integer.parseInt(lineParser.get("scaleH")));
		splitLine(reader);
		textureFileName = lineParser.get("file") ;
		textureFileName = textureFileName.substring(1, textureFileName.length()-1) ;
		
		splitLine(reader);
		charCount = Integer.parseInt(lineParser.get("count"));

		for (int i = 0; i < charCount; i++) {
			splitLine(reader);
			Character c = loadCharacter();
			if (c != null) {
				characters.put(c.getId(), c);

			}
		}

	}

	/**
	 * okunan bilgilere göre karakterler oluþturuluyor ve bir hashmap e kendi ascii kodlarýna yani idlerine göre kaydedilir.
	 * @return
	 */
	private Character loadCharacter() {
		int id = Integer.parseInt(lineParser.get("id"));

		double width = (double) Integer.parseInt(lineParser.get("width"));
		double height = (double) Integer.parseInt(lineParser.get("height"));

		double textureX = ((double) Integer.parseInt(lineParser.get("x")) / (double) textureScaleWH.x);
		double textureY = ((double) Integer.parseInt(lineParser.get("y")) / (double) textureScaleWH.y);
		double textureW = width / (double) textureScaleWH.x;
		double textureH = height / (double) textureScaleWH.y;

		double xoffset = ((double) Integer.parseInt(lineParser.get("xoffset")) / (double) lineHeight);
		double yoffset = ((double) Integer.parseInt(lineParser.get("yoffset")) / (double) lineHeight);
		double xadvance = ((double) (Integer.parseInt(lineParser.get("xadvance")) - 3 * padding) / (double) lineHeight);

		return new Character(id, width / (double) lineHeight, height / (double) lineHeight, textureX, textureY,
				textureW, textureH, xoffset, yoffset, xadvance);
	}

	/**
	 * bir satýr okunduðunda o satýr genelde sahip olunan bilgiler belli bir = sembolüne göre yazýlmýþtýr. Yani o satýrý okuyup lineParser denilen hashmape kaydediliyor.
	 * Ýstenildiði zaman o satýr için o bilgiye eriþim saðlanýyor. Bir sonraki satýr okunduðunda önceki satýrýn bilgisi silinir.
	 * @param reader		Dosya okuma sýnýfý
	 * @throws Exception	çaðrýlan sýnýfa oluþacak hatalarý fýrlatma
	 */
	private void splitLine(BufferedReader reader) throws Exception {
		lineParser.clear();
		String line = null;
		String[] parser = null;
		String[] parser2 = null;
		if ((line = reader.readLine()) != null) {

			parser = line.split(" ");
			if (parser != null) {
				for (int i = 0; i < parser.length; i++) {
					if (parser[i].contains("=")) {
						parser2 = parser[i].split("=");
						lineParser.put(parser2[0], parser2[1]);
					}

				}
			}
		}

	}

	public Map<Integer, Character> getCharacters() {
		return characters;
	}

	public String getTextureFileName() {
		return textureFileName;
	}

}
