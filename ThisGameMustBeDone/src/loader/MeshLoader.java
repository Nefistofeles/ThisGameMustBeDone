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
	 * @param vertices vertex data koordinatlarý
	 * @param textureCoords texture data koordinatlarý
	 * @param indices koordinatlarý
	 * @return içerisinde vao index verisi ve vertex veri uzunluðu bulunan model yapýsý
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
	 * indexlerin olmadýðý modeller için metot
	 * 
	 * @param vertices vertex data koordinatlarý
	 * @param textureCoords texture data koordinatlarý
	 * @return içerisinde vao index verisi ve vertex veri uzunluðu bulunan model yapýsý
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
	 * vao oluþturma metodu
	 * @return vao idsi
	 */
	private int createVAO() {
		int vao = GL30.glGenVertexArrays() ;
		System.out.println("vao oluþturuldu : " + vao) ;
		vaos.add(vao) ;
		GL30.glBindVertexArray(vao);
		System.out.println("vao bind edildi...");
		return vao ;
	}
	/**
	 * 
	 * @param index vbo vao ya baðlayan index yani 0 ise 0. indexe baðlanýyor.
	 * @param size içerisindeki verinin boyutu Vec2 ise sizeý 2
	 * @param data içerisinde koordinat verileri bulunan array
	 */
	private void createVBO(int index, int size, float[] data) {
		int vbo = GL15.glGenBuffers() ;
		System.out.println("vbo oluþturuldu : " + vbo);
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		System.out.println("vbo bind edildi...");
		FloatBuffer buffer = createFloatBuffer(data);
		System.out.println("float data deðeri buffer olarak kayýt edildi : " + buffer.toString());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		System.out.println("vboya çizim þekli ile buffer datasý eklendi...");
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, size * FLOAT_SIZE ,0);
		System.out.println("vbo özellikleri girildi : index : " + index + " size " + size  );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		System.out.println("vbo unbind edildi");
		
	}
	/**
	 * OpenGL'in hangi sýrayla verileri okumasý gerektiðinin bilgisi indis olarak kaydedilmesi gerekir. Bu metot ile indislerde vbo þeklinde vaoya kaydedilir.
	 * @param data indis deðerlerinin bulunduðu int array
	 */
	private void createIndices(int[] data) {
		int vbo = GL15.glGenBuffers() ;
		System.out.println("vbo oluþturuldu : " + vbo);
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		System.out.println("vbo bind edildi...");
		IntBuffer buffer = createIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		System.out.println("vboya çizim þekli ile buffer datasý eklendi...");
		
	}
	/**
	 * Bir veriyi vbo þeklinde depolamak için bufferlar kullanýlýr. Bu metot float veri yapýsýný bir buffera kaydeden metot.
	 * @param data float array þeklinde yazýlmýþ bir data
	 * @return içinde data verisini bulunduran float buffer
	 */
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length) ;
		System.out.println("data uzunluðunda buffer oluþturuldu : " + data.length);
		buffer.put(data) ;
		System.out.println("buffera data deðeri konuldu...");
		buffer.flip();
		System.out.println("buffer ters çevrildi... " );
		return buffer ;
	}
	/**
	 * Bir veriyi vbo þeklinde depolamak için bufferlar kullanýlýr. Bu metot integer veri yapýsýný bir buffera kaydeden metot.
	 * @param data int array þeklinde yazýlmýþ bir data
	 * @return içinde data verisini bulunduran integer buffer
	 */
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length) ;
		System.out.println("data uzunluðunda buffer oluþturuldu : " + data.length);
		buffer.put(data) ;
		System.out.println("buffera data deðeri konuldu...");
		buffer.flip();
		System.out.println("buffer ters çevrildi... " );
		return buffer ;
	}
	/**
	 * program bittiðinde vao ve vbolarý silen metot.
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
