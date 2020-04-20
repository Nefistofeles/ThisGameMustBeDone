package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;

public class Draw {

	
	public static void enableVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}
	public static void enableVertexAttribArray(int size) {
		for(int i = 0 ; i < size ; i++) {
			GL20.glEnableVertexAttribArray(i);
		}
	}
	public static void enableTexture(int id) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	public static void enableTexture(Texture texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	public static void renderOptimize(int vertexCount) {
		GL11.glDrawElements(GL11.GL_TRIANGLES,vertexCount , GL11.GL_UNSIGNED_INT, 0);
	}
	public static void render(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
	}
	public static void renderTriangleStrip(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount);
	}
	public static void disableVertexAttribArray(int size) {
		for(int i = 0 ; i < size ; i++) {
			GL20.glDisableVertexAttribArray(i);
		}
	}
	public static void disableVAO() {
		GL30.glBindVertexArray(0);
	}
}
