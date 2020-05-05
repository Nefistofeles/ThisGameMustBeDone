package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;

public class Draw {
	/**
	 *	OpenGL (LWGJL) bir objeyi ekrana çizdirirken statik metotlar kullanýr. Bu metotlarýn durmadan yazýlmasý yerine statik bir sýnýfta hazýr olarak 
	 *	durmadan çaðrýlmasýný saðlanmasý gerekir. Bu durumu gerçekleþtiren sýnýftýr.  
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
	 * vao içerisinde kayýtlý halde vbolarda aktif edilir.
	 * @param size	aktif edilecek vbo sayýsý.
	 */
	public static void enableVertexAttribArray(int size) {
		for(int i = 0 ; i < size ; i++) {
			GL20.glEnableVertexAttribArray(i);
		}
	}
	/**
	 * Ekrana çizdirilecek texture aktif edilir.
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
	 * index deðerleri vbo ile kaydedilmiþ vaolar bu metodu kullanarak çizdirme iþlemi yapar.
	 * @param vertexCount	vbodaki bir verinin vertex sayýsý.
	 */
	public static void renderOptimize(int vertexCount) {
		GL11.glDrawElements(GL11.GL_TRIANGLES,vertexCount , GL11.GL_UNSIGNED_INT, 0);
	}
	/**
	 * içerisinde index deðerleri bulunmayan bir vao bu çizim metodunu kullanmak zorundadýr.
	 * @param vertexCount	vbodaki bir verinin vertex sayýsý.
	 */
	public static void render(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
	}
	/**
	 * Triangle strip þeklinde yapýlacak çizimlerin kullanacapý metot.
	 * @param vertexCount
	 */
	public static void renderTriangleStrip(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount);
	}
	/**
	 * vbo veri tutucularýný kapatmak için kullanýlan metot.
	 * @param size	kapatýlacak vbo sayýsý.
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
