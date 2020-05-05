package utils;

public class Color {
	
	//renk classý
	/**
	 * Renk deðerlerinin tutulduðu sýnýf.
	 */

	public float r,g,b,alpha ;

	public Color(float r, float g, float b, float alpha) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}
	@Override
	public Color clone() {
		try {
			return new Color(r,g,b,alpha);
		}catch (Exception e) {
			System.out.println("clone is not created");
			e.printStackTrace();
			System.exit(-1);
		}
		return null ;
	}
	
	public void setColor(float r, float g, float b, float alpha) {
		this.r = r ;
		this.g = g ;
		this.b = b ;
		this.alpha = alpha ;
	}
	
	
}
