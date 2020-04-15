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
	
	public FontMeshLoader(String fontName, Loader loader) {
		font = new FontLoader();
		font.loadFont(fontName);
		this.loader = loader ;
		texture = loader.getTextureLoader().loadTexture(font.getTextureFileName(), TextureLoader.TextureLinear, 0) ;
	}
	
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
	private void loadTextureCoords(List<Float> textureCoords, Character c) {
		Maths.addMeshAttachment(textureCoords, c.getTextureX(), c.getTextureY(), c.getTextureX() + c.getTextureWidth(),c.getTextureY() + c.getTextureHeight());
	}
	
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
