package animationSystem;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

public class AnimationData {
	/**
	 * ��erisinde animasyon texture atlas�n�n verisinin nas�l okunmas� gerekti�inin bilgisini tutan s�n�f. 
	 * �rne�in idle yani karakterin bo�taki durumu i�in AnimationEnum.idle ve texture par�as� say�s� 4 ise 4 e kadar okuyup tekrar texture�n ba��na d�n�p tekrar okuyor.
	 */

	private Map<AnimationEnum, Vector2f> animationData ;
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AnimationData animation = new AnimationData();
		for(AnimationEnum anim : animationData.keySet())
			animation.addAnimationData(anim,(int)animationData.get(anim).x , (int)animationData.get(anim).y);
		
		return animation ;
	}
	public AnimationData getClone() {
		try {
			return (AnimationData) clone() ;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	public AnimationData() {
		animationData = new HashMap<AnimationEnum , Vector2f>();
		
	}
	/**
	 * 
	 * @param e animasyon idsi
	 * @param textureX sat�r ka� par�a resimden olu�uyor.
	 * @param textureY ka��nc� sat�rda bulunuyor.
	 */
	public void addAnimationData(AnimationEnum e, int textureX, int textureY) {
		animationData.put(e, new Vector2f(textureX,textureY)) ;
	}
	public Map<AnimationEnum, Vector2f> getAnimationData() {
		return animationData;
	}
	public void setAnimationData(Map<AnimationEnum, Vector2f> animationData) {
		this.animationData = animationData;
	}
}
