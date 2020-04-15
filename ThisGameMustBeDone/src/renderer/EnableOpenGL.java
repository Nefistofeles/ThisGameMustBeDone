package renderer;

import org.lwjgl.opengl.GL11;

public class EnableOpenGL {

	public static void culling(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			
		//	GL11.glFrontFace(GL11.GL_CW);	//clockwise or CCW = counterClockWise
		}else {
			GL11.glDisable(GL11.GL_CULL_FACE);
			
		}
		
	}
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

	public static void blendAdditiveFunc(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_BLEND) ;
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);GL11.glBlendFunc(GL11.GL_SRC_ALPHA	, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}else {
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	public static void enableStencilTest(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_STENCIL_TEST);
		}else
			GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
	public static void enableDepthTest(boolean isEnable) {
		if(isEnable) {
			GL11.glEnable(GL11.GL_DEPTH_TEST) ;
			GL11.glDepthFunc(GL11.GL_LESS); //always ... 
		}else
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		
	}


}
