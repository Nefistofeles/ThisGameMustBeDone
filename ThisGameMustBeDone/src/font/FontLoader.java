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
	 * fontun .fnt tabanl� dosyadan bilgilerinin okunamas�na olanak sa�layan s�n�ft�r.
	 */

	public FontLoader() {
		characters = new HashMap<Integer, Character>();
		lineParser = new HashMap<String, String>();
	}

	/**
	 * basit dosya okuma s�n�f�
	 * @param fileName	g�nderilecek .fnt tabanl� dosyan�n ismi
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
	 * Gereken �zelliklere g�re dosyadaki bilgilerin parse edilmesini sa�layan s�n�f
	 * @param reader		parse edilmesi i�in BufferedReader s�nf�n�n g�nderilesi gerekiyor.
	 * @throws Exception	olabilir hatalar�n �a�r�lan metoda f�rlat�lmas�
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
	 * okunan bilgilere g�re karakterler olu�turuluyor ve bir hashmap e kendi ascii kodlar�na yani idlerine g�re kaydedilir.
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
	 * bir sat�r okundu�unda o sat�r genelde sahip olunan bilgiler belli bir = sembol�ne g�re yaz�lm��t�r. Yani o sat�r� okuyup lineParser denilen hashmape kaydediliyor.
	 * �stenildi�i zaman o sat�r i�in o bilgiye eri�im sa�lan�yor. Bir sonraki sat�r okundu�unda �nceki sat�r�n bilgisi silinir.
	 * @param reader		Dosya okuma s�n�f�
	 * @throws Exception	�a�r�lan s�n�fa olu�acak hatalar� f�rlatma
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
