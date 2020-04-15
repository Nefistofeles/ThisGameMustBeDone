package animationSystem;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

public class AnimationData {

	private Map<AnimationEnum, Vector2f> animationData ;
	
	public AnimationData() {
		animationData = new HashMap<AnimationEnum , Vector2f>();
		
	}
	public void addAnimationData(AnimationEnum e, int textureX, int textureY) {
		animationData.put(e, new Vector2f(textureX,textureY)) ;
	}
	public Map<AnimationEnum, Vector2f> getAnimationData() {
		return animationData;
	}
	
}
