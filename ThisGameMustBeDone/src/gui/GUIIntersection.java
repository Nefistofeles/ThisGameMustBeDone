package gui; 

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Mouse;

import utils.Maths;
import utils.Matrix4;

public class GUIIntersection {

	/**
	 * Gonderilen GUI mouse ile çakýþýyor mu onun kontrolünü yapar.
	 * @param gui		kontrol edilmesi istenilen gui
	 * @param point		mouseun ekrandaki pozisyonu
	 * @return			çakýþýp çakýþmadýðýna dair bilgi
	 */
	public boolean isIntersect(GUI gui, Vec2 point) {
		
		Vec2 min = new Vec2(-0,-1);
		Vec2 max = new Vec2(1,0);
		
		max = Matrix4.MatrixMulPlusPos(gui.getTransformationMatrix(), max, gui.getPosition());
		min = Matrix4.MatrixMulPlusPos(gui.getTransformationMatrix(), min, gui.getPosition());
		
		
		if(min.x < point.x && max.x > point.x ) {
			if(min.y < point.y && max.y > point.y ) {
				
				return true ;
					
			}
		}
		
		return false ;
		
		
	}
}
