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
	 * Bu s�n�f�n temel amac� .obj dosyalar�n� okuyup opengl koordinat sistemine uygun modeller olu�turmak.
	 * @param meshLoader okunan koordinat verilerinin opengl'e y�klenmesini sa�layan s�n�f.
	 */
	private MeshLoader meshLoader ;
	
	public OBJFileLoader(MeshLoader meshLoader) {
		this.meshLoader = meshLoader ;
	}

	/**
	 * 
	 * @param name res dosyas�ndaki y�klenecek olan .obj tabanl� file ismi.
	 * @return e�er dosya bulunursa Mesh s�n�f�ndan nesne d�nd�recek.
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
				System.out.println("\nokunan sat�r : " + line);
				if(line.startsWith("v ")) {
					lineParser = line.split(" ") ;
					System.out.println("vertex koordinat de�eri : x : " + lineParser[1] + " " + "y : " + lineParser[2]);
					vertices.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("vt ")) {
					lineParser = line.split(" ") ;
					System.out.println("texture koordinat de�eri : x : " + lineParser[1] + " " + "y : " + lineParser[2]);
					textureCoords.add(new Vec2(Float.parseFloat(lineParser[1]), Float.parseFloat(lineParser[2]))) ;
				}
				else if(line.startsWith("f ")) {
					lineParser = line.split(" ") ;
					System.out.print("index de�erleri : ");
					for(int i = 0 ; i < lineParser.length ; i++)
						System.out.print(lineParser[i] + " ");
					System.out.println();
					parseIndices(lineParser, indices);
				}
				
			}
			System.out.println();
			indicesArray = new int[indices.size()] ;
			System.out.println("index array olu�turuldu size : " + indicesArray.length);
			verticesArray = new float[vertices.size() * 2];
			System.out.println("vertex array olu�turuldu size : " + verticesArray.length);
			textureCoordsArray = new float[vertices.size() * 2];
			System.out.println("texture array olu�turuldu size : " + textureCoordsArray.length);
			System.out.println("Opengl koordinat sistemine g�re tekrar d�zenleniyor.");
			representForOpenGL(vertices, textureCoords, indices, textureCoordsArray, indicesArray);
			System.out.println();
			int pointer = 0 ;
			for(Vec2 vertex : vertices) {
				verticesArray[pointer++] = vertex.x ;
				verticesArray[pointer++] = vertex.y ;
				System.out.println("vertex de�erleri : " + vertex.toString());
			}
			
			return meshLoader.loadMesh(verticesArray, textureCoordsArray, indicesArray) ;
			
		} catch (Exception e) {
			System.out.println(name + " is not found");
			e.printStackTrace();
		}
		return null ;
	}
	/**
	 * blender taraf�ndan verilen do�ru s�raya uydurulmas� i�in indices verisinin ayr��t�r�l�p texture koordinatlar�n�n d�zenlenmesi i�in yaz�lm��t�r.
	 * 
	 * @param lineParser indices verilerini i�eren string de�erleri
	 * @param indices indices verilerini tutan liste
	 */
	private void parseIndices(String[] lineParser, List<Integer> indices) {
		String[] indiceParser = null ;
		for(int i = 1 ; i < lineParser.length ; i++) {
			indiceParser = lineParser[i].split("/") ;
			System.out.println("\nparse edilecek de�er : " + lineParser[i]);
			indices.add(Integer.parseInt(indiceParser[0])) ;
			indices.add(Integer.parseInt(indiceParser[1])) ;
			indices.add(Integer.parseInt(indiceParser[2])) ;	//normal i�in ama bizim normal hesab�na �u ana i�in ihtiyac�m�z yok
		}
	}
	/**
	 * Opengl'e array ile y�kleme yap�ld��� i�in d�n���m yap�l�yor. Ayr�ca kar���k verilmi� texture koordinatlar� da d�zenleniyor. 
	 * @param vertices vertex verilerini tutan liste
	 * @param textureCoords texture verilerini tutan liste
	 * @param indices index verilerini tutan liste
	 * @param textureCoordsArray texture koordinatlar� tutan float array
	 * @param indicesArray index koordinatlar�n� tutan int array
	 */
	private void representForOpenGL(List<Vec2> vertices, List<Vec2> textureCoords, List<Integer> indices, float[] textureCoordsArray, int[] indicesArray) {
		for(int i = 0 ; i < indices.size() ; i+=3) {
			int v = indices.get(i + 0) ;
			int t = indices.get(i + 1) ;
			int n = indices.get(i + 2) ;	//normal i�in ama bizim normal hesab�na �u ana i�in ihtiyac�m�z yok
			System.out.println("index vertex de�eri : " + v);
			System.out.println("index texture de�eri : " + t);
			System.out.println("index normal de�eri : " + n);
			int currentVertexPointer = v - 1 ;
			System.out.println("\nuygulanacak vertex de�eri : " + currentVertexPointer);
			indicesArray[(int)i/3] = currentVertexPointer ;
			System.out.println("index de�erine olmas� gereken vertex de�eri : " + currentVertexPointer);
			Vec2 currentTex = textureCoords.get(t -1 );
			System.out.println("\ntexture de�eri  : " + currentTex.toString());
			textureCoordsArray[currentVertexPointer * 2 ] = currentTex.x;
			textureCoordsArray[currentVertexPointer * 2 + 1 ] = 1 - currentTex.y;
			System.out.println("opengl koordinat d�zlemine g�re tekrar d�zenlenen texture de�eri : " + textureCoordsArray[currentVertexPointer * 2 ] + " " + textureCoordsArray[currentVertexPointer * 2 + 1 ]);
		}
	}
}
