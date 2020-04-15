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

	public Mesh loadMesh(float[] vertices,float[] textureCoords, int[] indices) {
		int vao = createVAO();
		createVBO(0, 2, vertices);
		createVBO(1, 2, textureCoords);
		createIndices(indices);
		
		GL30.glBindVertexArray(0);
		return new Mesh(vao, indices.length) ;
	}
	public Mesh loadMesh(float[] vertices,float[] textureCoords) {
		int vao = createVAO();
		createVBO(0, 2, vertices);
		createVBO(1, 2, textureCoords);
		GL30.glBindVertexArray(0);
		return new Mesh(vao, vertices.length/2) ;
	}
	
	private int createVAO() {
		int vao = GL30.glGenVertexArrays() ;
		vaos.add(vao) ;
		GL30.glBindVertexArray(vao);
		return vao ;
	}
	
	private void createVBO(int index, int size, float[] data) {
		int vbo = GL15.glGenBuffers() ;
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, size * FLOAT_SIZE ,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	private void createIndices(int[] data) {
		int vbo = GL15.glGenBuffers() ;
		vbos.add(vbo) ;
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = createIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length) ;
		buffer.put(data) ;
		buffer.flip();
		return buffer ;
	}
	
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length) ;
		buffer.put(data) ;
		buffer.flip();
		return buffer ;
	}
	
	public void clean() {
		for(Integer i : vaos) {
			GL30.glDeleteVertexArrays(i);
		}
		for(Integer i : vbos) {
			GL15.glDeleteBuffers(i);
		}
	}
}
