package utils;

public class Vec4 {
	
	//sadece veri depolama ve yükleme için kullanýyorum. 4 boyutlu vectorlere uygun matematik fonksiyonlarý uyarlanmasý yapýlmadý.

	public float x,y,z,w ;
	
	public Vec4() {
		
	}

	public Vec4(float x, float y, float z, float w) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void set(float x , float y, float z , float w) {
		this.x = x ;
		this.y = y ;
		this.w = w ;
		this.z = z ;
		
	}
	
	
	
	
}
