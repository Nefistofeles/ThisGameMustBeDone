package animationSystem;

import org.jbox2d.common.Vec2;
import org.lwjgl.util.vector.Vector2f;

import dataStructure.Texture;
import utils.DisplayManager;

public class Animation {

	private Texture texture ;
	private AnimationData data;
	private float time;
	

	public Animation(Texture texture, AnimationData data) {
		this.texture = texture;
		this.data = data;
		time = 0;
		
	}
	/**
	 * 
	 * @param a ekranda d�necek resim setinin idsi
	 * @param fps oynat�lacak resmin fpsi
	 * 
	 */
	public void animate(AnimationEnum a, int fps) {
		Vector2f v = data.getAnimationData().get(a);
		time += DisplayManager.getFrameTime();
		texture.setTextureIndexY((int)v.y);
		if (time > DisplayManager.getFrameTime() * fps) {
			time = 0;
			texture.setTextureIndexX(texture.getTextureIndexX() + 1);
			if (texture.getTextureIndexX() >=(int) v.x) {
				texture.setTextureIndexX((int) 0);
				//return false ;
			}

		}
		
		//return true ;

	}

}
