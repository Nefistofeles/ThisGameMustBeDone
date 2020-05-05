package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;

public class Draw {
	/**
	 *	OpenGL (LWGJL) bir objeyi ekrana �izdirirken statik metotlar kullan�r. Bu metotlar�n durmadan yaz�lmas� yerine statik bir s�n�fta haz�r olarak 
	 *	durmadan �a�r�lmas�n� sa�lanmas� gerekir. Bu durumu ger�ekle�tiren s�n�ft�r.  
	 * 
	 */
	
	/**
	 * vao ilk olarak bind edilir. 
	 * @param vaoID	vaonun idsi
	 */
	public static void enableVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}
	/**
	 * vao i�erisinde kay�tl� halde vbolarda aktif edilir.
	 * @param size	aktif edilecek vbo say�s�.
	 */
	public static void enableVertexAttribArray(int size) {
		for(int i = 0 ; i < size ; i++) {
			GL20.glEnableVertexAttribArray(i);
		}
	}
	/**
	 * Ekrana �izdirilecek texture aktif edilir.
	 * @param id	texture idsi
	 */
	public static void enableTexture(int id) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public static void enableTexture(Texture texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	/**
	 * index de�erleri vbo ile kaydedilmi� vaolar bu metodu kullanarak �izdirme i�lemi yapar.
	 * @param vertexCount	vbodaki bir verinin vertex say�s�.
	 */
	public static void renderOptimize(int vertexCount) {
		GL11.glDrawElements(GL11.GL_TRIANGLES,vertexCount , GL11.GL_UNSIGNED_INT, 0);
	}
	/**
	 * i�erisinde index de�erleri bulunmayan bir vao bu �izim metodunu kullanmak zorundad�r.
	 * @param vertexCount	vbodaki bir verinin vertex say�s�.
	 */
	public static void render(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
	}
	/**
	 * Triangle strip �eklinde yap�lacak �izimlerin kullanacap� metot.
	 * @param vertexCount
	 */
	public static void renderTriangleStrip(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount);
	}
	/**
	 * vbo veri tutucular�n� kapatmak i�in kullan�lan metot.
	 * @param size	kapat�lacak vbo say�s�.
	 */
	public static void disableVertexAttribArray(int size) {
		for(int i = 0 ; i < size ; i++) {
			GL20.glDisableVertexAttribArray(i);
		}
	}
	/**
	 * vaoyu kapatmak
	 */
	public static void disableVAO() {
		GL30.glBindVertexArray(0);
	}
}
