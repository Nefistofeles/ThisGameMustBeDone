package renderer;

import org.lwjgl.opengl.GL11;

public class EnableOpenGL {
	/**
	 * Bazen OpenGL'de çizim yapýlýrken bazý özellikleri kapatmak veya açmak gerekir. Bu sýnýf bunu gerçekleþtirmektedir.
	 *
	 */

	/**
	 * 
	 * @param isEnable	Çizilen objenin ekranda görünmeyen arka tarafýnýn çizilip çizilmemesinin kararý
	 */
	public static void culling(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			
		//	GL11.glFrontFace(GL11.GL_CW);	//clockwise or CCW = counterClockWise
		}else {
			GL11.glDisable(GL11.GL_CULL_FACE);
			
		}
		
	}
	/**
	 * 
	 * @param isDisable	Derinlik faktörünün (z ekseni) tamamen iptal edilmesi
	 */
	public static void disableDepthTestWithMask(boolean isDisable) {
		if(isDisable) {
			GL11.glDepthMask(false);
		}else {
			GL11.glDepthMask(true);
		}
	}
	public static void blendFunc(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_BLEND) ;
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA	, GL11.GL_ONE_MINUS_SRC_ALPHA);
	//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
		}else {
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	/**
	 * 
	 * @param isEnable	Alpha deðeri 0 olan resmin ekranda çizilmemesi
	 */
	public static void blendAdditiveFunc(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_BLEND) ;
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);GL11.glBlendFunc(GL11.GL_SRC_ALPHA	, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}else {
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	/**
	 * 
	 * @param isEnable	ekrandaki resim çizilen bölgesinin veya çizilmeyen bölgesinin olmasýnýn ayarý
	 * ben hiç kullanmadýðým için daha tam bilmiyorum
	 */
	public static void enableStencilTest(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_STENCIL_TEST);
		}else
			GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
	/**
	 * 
	 * @param isEnable derinlik faktörünün açýlýp kapanmasý.
	 */
	public static void enableDepthTest(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_DEPTH_TEST) ;
			GL11.glDepthFunc(GL11.GL_LESS); //always ... 
		}else
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		
	}


}
