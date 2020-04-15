package fileParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import loader.Loader;

public class OBJFileLoader {

	public Mesh loadObjFile(String name, Loader loader) {
		try {
			InputStream in = Class.class.getResourceAsStream("/res/" + name + ".obj") ;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ;
			List<Vec2> vertices = new ArrayList<Vec2>();
			List<Vec2> textureCoords = new ArrayList<Vec2>();
			List<Integer> indices = new ArrayList<Integer>();
			float[] verticesArray ;
			float[] textureCoordsArray ;
			int[] indicesArray ;
			String line = null ;
			String[] lineParser = null ;
			while((line = reader.readLine()) != null) {
				if(line.startsWith("v ")) {
					lineParser = line.split(" ") ;
					vertices.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("vt ")) {
					lineParser = line.split(" ") ;
					textureCoords.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("f ")) {
					lineParser = line.split(" ") ;
					parseIndices(lineParser, indices);
				}
				
			}
			indicesArray = new int[indices.size()] ;
			verticesArray = new float[vertices.size() * 2];
			textureCoordsArray = new float[vertices.size() * 2];
			representForOpenGL(vertices, textureCoords, indices, textureCoordsArray, indicesArray);
			
			int pointer = 0 ;
			for(Vec2 vertex : vertices) {
				verticesArray[pointer++] = vertex.x ;
				verticesArray[pointer++] = vertex.y ;
			}
			
			return loader.getMeshLoader().loadMesh(verticesArray, textureCoordsArray, indicesArray) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	private void parseIndices(String[] lineParser, List<Integer> indices) {
		String[] indiceParser = null ;
		for(int i = 1 ; i < lineParser.length ; i++) {
			indiceParser = lineParser[i].split("/") ;
			indices.add(Integer.parseInt(indiceParser[0])) ;
			indices.add(Integer.parseInt(indiceParser[1])) ;
			indices.add(Integer.parseInt(indiceParser[2])) ;	//normal için ama bizim normal hesabýna þu ana için ihtiyacýmýz yok
		}
	}
	
	private void representForOpenGL(List<Vec2> vertices, List<Vec2> textureCoords, List<Integer> indices, float[] textureCoordsArray, int[] indicesArray) {
		for(int i = 0 ; i < indices.size() ; i+=3) {
			int v = indices.get(i + 0) ;
			int t = indices.get(i + 1) ;
			int n = indices.get(i + 2) ;	//normal için ama bizim normal hesabýna þu ana için ihtiyacýmýz yok
			
			int currentVertexPointer = v - 1 ;
			indicesArray[(int)i/3] = currentVertexPointer ;
			Vec2 currentTex = textureCoords.get(t -1 );
			textureCoordsArray[currentVertexPointer * 2 ] = currentTex.x;
			textureCoordsArray[currentVertexPointer * 2 + 1 ] = 1 - currentTex.y;
		}
	}
}
