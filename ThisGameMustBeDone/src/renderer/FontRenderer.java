package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataStructure.Texture;
import entities.Entity;
import font.Text;
import shaders.FontShader;
import utils.DisplayManager;

public class FontRenderer implements Renderable{

	private FontShader shader ;
	private Map<Texture, List<Text>> texts ;
	
	public FontRenderer() {
		
	}

	@Override
	public void init() {
		texts = new HashMap<Texture, List<Text>>();
		shader = new FontShader();
		shader.start();
		shader.loadProjectionMatrix(DisplayManager.getProjectionmatrix());
		shader.stop();
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		shader.start();
		EnableOpenGL.blendFunc(true);
		for(Texture texture : texts.keySet()) {
			List<Text> textList = texts.get(texture) ;
			
			Draw.enableTexture(texture);
			
			for(Text text : textList) {

				Draw.enableVAO(text.getMesh().getMeshID());
				Draw.enableVertexAttribArray(2);
				
				shader.loadTransformationMatrix(text.getTransformationMatrix());
				shader.loadWorldPosition(text.getWorldPosition());
				Draw.render(text.getMesh().getVertexCount());
				
				Draw.disableVertexAttribArray(2);
				Draw.disableVAO();
				
			}
		}
		
		EnableOpenGL.blendFunc(false);
		shader.stop();
		
	}

	@Override
	public void clean() {
		shader.clean();
		
	}
	public void addText(Text text, Texture texture) {
		List<Text> textList = texts.get(texture) ;
		
		if(textList == null) {
			textList = new ArrayList<Text>();
			textList.add(text); 
			texts.put(texture, textList) ;
		}else {
			textList.add(text) ;
		}
	}
}
