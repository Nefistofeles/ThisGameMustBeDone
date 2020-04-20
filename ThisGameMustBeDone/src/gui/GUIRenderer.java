package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dataStructure.Mesh;
import dataStructure.Texture;
import loader.Loader;
import renderer.Draw;
import renderer.EnableOpenGL;
import renderer.Renderable;
import utils.Coordinates;
import utils.DisplayManager;
import utils.MouseOrtho;

public class GUIRenderer implements Renderable{
	
	private GUIShader shader ;
	private Mesh mesh ;
	private Loader loader ;
	private Map<Texture, List<GUI>> guis ;
	private GUIIntersection guiIntersection ;
	private MouseOrtho mouse ;
	
	public GUIRenderer(Loader loader,MouseOrtho mouse) {
		this.loader = loader ;
		this.mouse = mouse ;
	}

	@Override
	public void init() {
		guiIntersection = new GUIIntersection();
		guis = new HashMap<Texture, List<GUI>>();
		mesh = loader.getMeshLoader().loadMesh(Coordinates.triangleStripVertex, Coordinates.triangleStripVertex) ;
		shader = new GUIShader();
		shader.start();
		shader.loadProjectionMatrix(DisplayManager.getProjectionmatrix());
		shader.stop();
		
	}

	@Override
	public void update() {
		Iterator<Texture> guiIterator = guis.keySet().iterator() ;
		while(guiIterator.hasNext()) {
			Texture texture = guiIterator.next() ;
			List<GUI> guiList = guis.get(texture);
			Iterator<GUI> iter = guiList.iterator();
			while (iter.hasNext()) {
				GUI t = iter.next();
				boolean isIntersect = guiIntersection.isIntersect(t, mouse.getGUIMasterPosition()) ;
				if(isIntersect) {
					System.out.println("intersect");
				}
			}
		}
		
		
	}

	@Override
	public void render() {
		EnableOpenGL.blendFunc(true);
		EnableOpenGL.disableDepthTestWithMask(true);
		// EnableOpenGL.enableDepthTest(false);
		shader.start();
		
		Draw.enableVAO(mesh.getMeshID());
		Draw.enableVertexAttribArray(2);
		
		for(Texture texture: guis.keySet()) {
			List<GUI> guiList = guis.get(texture) ;
			Draw.enableTexture(texture);
			for (GUI g : guiList) {
				
				shader.loadWorldPosition(g.getWorldPosition());
				shader.loadTransformationMatrix(g.getTransformationMatrix());
				Draw.renderTriangleStrip(mesh.getVertexCount());
			
		}
		}
		Draw.disableVertexAttribArray(2);
		Draw.disableVAO();
		
		shader.stop();
		EnableOpenGL.blendFunc(false);
		EnableOpenGL.disableDepthTestWithMask(false);
	}

	@Override
	public void clean() {
		shader.clean();
		
	}
	
	public void addGUI(GUI gui) {
		List<GUI> guiList = guis.get(gui.getTexture()) ;
		if(guiList == null) {
			guiList = new ArrayList<GUI>();
			guiList.add(gui) ;
			guis.put(gui.getTexture(), guiList) ;
		}else {
			guiList.add(gui) ;
		}
	}

	
}
