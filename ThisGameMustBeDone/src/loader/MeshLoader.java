package loader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Mesh;

public class MeshLoader {
	
	private static final int FLOAT_SIZE = 4 ;
	
	private List<Integer> vaos ;
	private List<Integer> vbos ;
	
	public MeshLoader() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
	}

	/**
	 * 
	 * @param vertices vertex data koordinatlar�
	 * @param textureCoords texture data koordinatlar�
	 * @param indices koordinatlar�
	 * @return i�erisinde vao index verisi ve vertex veri uzunlu�u bulunan model yap�s�
	 */
	public Mesh loadMesh(float[] vertices,float[] textureCoords, int[] indices) {
		int vao = createVAO();
		createVBO(0, 2, vertices);
		createVBO(1, 2, textureCoords);
		createIndices(indices);
		
		GL30.glBindVertexArray(0);
		System.out.println("vao unbind edildi...");
		System.out.println();
		return new Mesh(vao, indices.length) ;
	}
	/**
	 * indexlerin olmad��� modeller i�in metot
	 * 
	 * @param vertices vertex data koordinatlar�
	 * @param textureCoords texture data koordinatlar�
	 * @return i�erisinde vao index verisi ve vertex veri uzunlu�u bulunan model yap�s�
	 */
	public Mesh loadMesh(float[] vertices,float[] textureCoords) {
		int vao = createVAO();
		createVBO(0, 2, vertices);
		createVBO(1, 2, textureCoords);
		GL30.glBindVertexArray(0);
		System.out.println("vao unbind edildi...");
		System.out.println();
		return new Mesh(vao, vertices.length/2) ;
	}
	/**
	 * vao olu�turma metodu
	 * @return vao idsi
	 */
	private int createVAO() {
		int vao = GL30.glGenVertexArrays() ;
		System.out.println("vao olu�turuldu : " + vao) ;
		vaos.add(vao) ;
		GL30.glBindVertexArray(vao);
		System.out.println("vao bind edildi...");
		return vao ;
	}
	/**
	 * 
	 * @param index vbo vao ya ba�layan index yani 0 ise 0. indexe ba�lan�yor.
	 * @param size i�erisindeki verinin boyutu Vec2 ise size� 2
	 * @param data i�erisinde koordinat verileri bulunan array
	 */
	private void createVBO(int index, int size, float[] data) {
		int vbo = GL15.glGenBuffers() ;
		System.out.println("vbo olu�turuldu : " + vbo);
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		System.out.println("vbo bind edildi...");
		FloatBuffer buffer = createFloatBuffer(data);
		System.out.println("float data de�eri buffer olarak kay�t edildi : " + buffer.toString());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		System.out.println("vboya �izim �ekli ile buffer datas� eklendi...");
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, size * FLOAT_SIZE ,0);
		System.out.println("vbo �zellikleri girildi : index : " + index + " size " + size  );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		System.out.println("vbo unbind edildi");
		
	}
	/**
	 * OpenGL'in hangi s�rayla verileri okumas� gerekti�inin bilgisi indis olarak kaydedilmesi gerekir. Bu metot ile indislerde vbo �eklinde vaoya kaydedilir.
	 * @param data indis de�erlerinin bulundu�u int array
	 */
	private void createIndices(int[] data) {
		int vbo = GL15.glGenBuffers() ;
		System.out.println("vbo olu�turuldu : " + vbo);
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		System.out.println("vbo bind edildi...");
		IntBuffer buffer = createIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		System.out.println("vboya �izim �ekli ile buffer datas� eklendi...");
		
	}
	/**
	 * Bir veriyi vbo �eklinde depolamak i�in bufferlar kullan�l�r. Bu metot float veri yap�s�n� bir buffera kaydeden metot.
	 * @param data float array �eklinde yaz�lm�� bir data
	 * @return i�inde data verisini bulunduran float buffer
	 */
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length) ;
		System.out.println("data uzunlu�unda buffer olu�turuldu : " + data.length);
		buffer.put(data) ;
		System.out.println("buffera data de�eri konuldu...");
		buffer.flip();
		System.out.println("buffer ters �evrildi... " );
		return buffer ;
	}
	/**
	 * Bir veriyi vbo �eklinde depolamak i�in bufferlar kullan�l�r. Bu metot integer veri yap�s�n� bir buffera kaydeden metot.
	 * @param data int array �eklinde yaz�lm�� bir data
	 * @return i�inde data verisini bulunduran integer buffer
	 */
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length) ;
		System.out.println("data uzunlu�unda buffer olu�turuldu : " + data.length);
		buffer.put(data) ;
		System.out.println("buffera data de�eri konuldu...");
		buffer.flip();
		System.out.println("buffer ters �evrildi... " );
		return buffer ;
	}
	/**
	 * program bitti�inde vao ve vbolar� silen metot.
	 */
	public void clean() {
		for(Integer i : vaos) {
			GL30.glDeleteVertexArrays(i);
		}
		for(Integer i : vbos) {
			GL15.glDeleteBuffers(i);
		}
	}
}
