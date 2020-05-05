package loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;

public class OBJFileLoader {
	
	/**
	 * Bu sýnýfýn temel amacý .obj dosyalarýný okuyup opengl koordinat sistemine uygun modeller oluþturmak.
	 * @param meshLoader okunan koordinat verilerinin opengl'e yüklenmesini saðlayan sýnýf.
	 */
	private MeshLoader meshLoader ;
	
	public OBJFileLoader(MeshLoader meshLoader) {
		this.meshLoader = meshLoader ;
	}

	/**
	 * 
	 * @param name res dosyasýndaki yüklenecek olan .obj tabanlý file ismi.
	 * @return eðer dosya bulunursa Mesh sýnýfýndan nesne döndürecek.
	 */
	public Mesh loadObjFile(String name) {
		try {
			System.out.println("loaded file name : " + name);
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
				System.out.println("\nokunan satýr : " + line);
				if(line.startsWith("v ")) {
					lineParser = line.split(" ") ;
					System.out.println("vertex koordinat deðeri : x : " + lineParser[1] + " " + "y : " + lineParser[2]);
					vertices.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("vt ")) {
					lineParser = line.split(" ") ;
					System.out.println("texture koordinat deðeri : x : " + lineParser[1] + " " + "y : " + lineParser[2]);
					textureCoords.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("f ")) {
					lineParser = line.split(" ") ;
					System.out.print("index deðerleri : ");
					for(int i = 0 ; i < lineParser.length ; i++)
						System.out.print(lineParser[i] + " ");
					System.out.println();
					parseIndices(lineParser, indices);
				}
				
			}
			System.out.println();
			indicesArray = new int[indices.size()] ;
			System.out.println("index array oluþturuldu size : " + indicesArray.length);
			verticesArray = new float[vertices.size() * 2];
			System.out.println("vertex array oluþturuldu size : " + verticesArray.length);
			textureCoordsArray = new float[vertices.size() * 2];
			System.out.println("texture array oluþturuldu size : " + textureCoordsArray.length);
			System.out.println("Opengl koordinat sistemine göre tekrar düzenleniyor.");
			representForOpenGL(vertices, textureCoords, indices, textureCoordsArray, indicesArray);
			System.out.println();
			int pointer = 0 ;
			for(Vec2 vertex : vertices) {
				verticesArray[pointer++] = vertex.x ;
				verticesArray[pointer++] = vertex.y ;
				System.out.println("vertex deðerleri : " + vertex.toString());
			}
			
			return meshLoader.loadMesh(verticesArray, textureCoordsArray, indicesArray) ;
			
		} catch (Exception e) {
			System.out.println(name + " is not found");
			e.printStackTrace();
		}
		return null ;
	}
	/**
	 * blender tarafýndan verilen doðru sýraya uydurulmasý için indices verisinin ayrýþtýrýlýp texture koordinatlarýnýn düzenlenmesi için yazýlmýþtýr.
	 * 
	 * @param lineParser indices verilerini içeren string deðerleri
	 * @param indices indices verilerini tutan liste
	 */
	private void parseIndices(String[] lineParser, List<Integer> indices) {
		String[] indiceParser = null ;
		for(int i = 1 ; i < lineParser.length ; i++) {
			indiceParser = lineParser[i].split("/") ;
			System.out.println("\nparse edilecek deðer : " + lineParser[i]);
			indices.add(Integer.parseInt(indiceParser[0])) ;
			indices.add(Integer.parseInt(indiceParser[1])) ;
			indices.add(Integer.parseInt(indiceParser[2])) ;	//normal için ama bizim normal hesabýna þu ana için ihtiyacýmýz yok
		}
	}
	/**
	 * Opengl'e array ile yükleme yapýldýðý için dönüþüm yapýlýyor. Ayrýca karýþýk verilmiþ texture koordinatlarý da düzenleniyor. 
	 * @param vertices vertex verilerini tutan liste
	 * @param textureCoords texture verilerini tutan liste
	 * @param indices index verilerini tutan liste
	 * @param textureCoordsArray texture koordinatlarý tutan float array
	 * @param indicesArray index koordinatlarýný tutan int array
	 */
	private void representForOpenGL(List<Vec2> vertices, List<Vec2> textureCoords, List<Integer> indices, float[] textureCoordsArray, int[] indicesArray) {
		for(int i = 0 ; i < indices.size() ; i+=3) {
			int v = indices.get(i + 0) ;
			int t = indices.get(i + 1) ;
			int n = indices.get(i + 2) ;	//normal için ama bizim normal hesabýna þu ana için ihtiyacýmýz yok
			System.out.println("index vertex deðeri : " + v);
			System.out.println("index texture deðeri : " + t);
			System.out.println("index normal deðeri : " + n);
			int currentVertexPointer = v - 1 ;
			System.out.println("\nuygulanacak vertex deðeri : " + currentVertexPointer);
			indicesArray[(int)i/3] = currentVertexPointer ;
			System.out.println("index deðerine olmasý gereken vertex deðeri : " + currentVertexPointer);
			Vec2 currentTex = textureCoords.get(t -1 );
			System.out.println("\ntexture deðeri  : " + currentTex.toString());
			textureCoordsArray[currentVertexPointer * 2 ] = currentTex.x;
			textureCoordsArray[currentVertexPointer * 2 + 1 ] = 1 - currentTex.y;
			System.out.println("opengl koordinat düzlemine göre tekrar düzenlenen texture deðeri : " + textureCoordsArray[currentVertexPointer * 2 ] + " " + textureCoordsArray[currentVertexPointer * 2 + 1 ]);
		}
	}
}
