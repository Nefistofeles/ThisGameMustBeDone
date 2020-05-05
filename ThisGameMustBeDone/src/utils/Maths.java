package utils;

import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

public class Maths {
	
	//Gerekebilecek Matematik fonksiyonlarý ve deðerleri
	/**
	 * Matematik sýnýfýdýr. Programýn ihtiyaçlarýna göre þekillenmiþ bazý matematik formmüllerinin koda dökülmüþ halidir.
	 */
	
	public static final float FPS =DisplayManager.getFrameTime() ;
	public static final Vec2 GRAVITY = new Vec2(0 , -9.81f * 8) ;	//0 , -50
	public static final double LINE_HEIGHT = 0.7f ;//0.03f;
	public static final Random random = new Random();
	public static final float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight() ;
	public static final Vec2 min = new Vec2(-1,-1) ;
	public static final Vec2 max = new Vec2(1,1) ;
	
	/**
	 * belli aralýkta random float deðer hesaplama
	 * @param min	min deðer
	 * @param max	max deðer
	 * @return		min-max arasý random deðer
	 */
	public static float random( float min, float max )
	{
		return (float)((max - min) * Math.random() + min);
	}
	/**
	 * belli aralýkta random int deðer hesaplama
	 * @param min	min deðer
	 * @param max	max deðer
	 * @return		min-max arasý random deðer
	 */
	public static int random( int min, int max )
	{
		return (int)((max - min + 1) * Math.random() + min);
	}
	/**
	 * listeyi arraya dönüþtüren metot.
	 * @param data	list data
	 * @return		array data
	 */
	public static float[] listToArray(List<Float> data) {
		float[] array = new float[data.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = data.get(i);
			
		}
		return array;
	}
	/**
	 * listeyi Vec2 türünde arraya dönüþtüren metot.
	 * @param data	list türünde data
	 * @return		Vec2 türünde array
	 */
	public static Vec2[] listToVec2Array(List<Float> data) {
		Vec2[] array = new Vec2[data.size() / 2];
		for (int i = 0; i < array.length; i+=2) {
			array[i] = new Vec2(data.get(i), data.get(i+1)) ;
			
		}
		return array;
	}
	/**
	 * girilen deðerlere göre opengle uygun koordinatlarý giren bir metot.
	 * @param data		girilen deðerlerin kaydedileceði liste
	 * @param x			baþlangýç noktasý x deðeri
	 * @param y			baþlangýç noktasý y deðeri
	 * @param width		objenin geniþliði
	 * @param height	objenin uzunluðu
	 */
	public static void addMeshAttachment(List<Float> data, double x, double y, double width, double height) {
		data.add((float) x);		data.add((float) y);
		data.add((float) x);		data.add((float) height);
		data.add((float) width);	data.add((float) height);
		data.add((float) width);	data.add((float) height);
		data.add((float) width);	data.add((float) y);
		data.add((float) x);		data.add((float) y);
	}
/*	public static Matrix4 createTransformationMatrix(Vec2 position , float rotation, Vec2 scale) {
		Matrix4 matrix = new Matrix4();
		matrix.setIdentity() ;
		Matrix4.translate(position, matrix, matrix);
		Matrix4.rotate(rotation, matrix) ;
		Matrix4.scale(scale, matrix, matrix) ;
		
		return matrix ;
		
	}
	
	public static Matrix4 updateTransformationMatrix(Matrix4 matrix , Vec2 position, float rotation , Vec2 scale) {
		matrix.setIdentity() ;
		Matrix4.translate(position, matrix, matrix);
		Matrix4.rotate(rotation, matrix) ;
		Matrix4.scale(scale, matrix, matrix) ;
		
		return matrix ;
	}*/

	/**
	 * Oyun kamerasýnýn matrixe dökülmesi
	 * @param camera	Camera sýnýfýndan bir nesne
	 * @return			camera sýnýfýnýn özelliklerini taþýyan matrix
	 */
	public static Matrix4 createViewMatrix(Camera camera) {
		Matrix4 matrix = new Matrix4();
		matrix.getMatrix().setIdentity() ;

		Vec3 pos = new Vec3(-camera.getPosition().x , -camera.getPosition().y, -camera.getPosition().z) ;
		Matrix4.translate(pos, matrix, matrix) ;
		
		return matrix ;
	}
	/**
	 * Oyundaki kamera sýnýfýnýn durmadan bir matrix sýnfý oluþturmasýný engellemek için olan matrixi kullanarak güncelleme yapmasýný saðlayan metot.
	 * @param matrix	Matrix türünde sýnýf
	 * @param camera	Camera türünde sýnýf
	 * @return			matrix deðerinin tekrar camera sýnýfýna göre þekillendirmiþ hali.
	 */
	public static Matrix4 updateViewMatrix(Matrix4 matrix, Camera camera) {
		matrix.getMatrix().setIdentity() ;
		Vec3 pos = new Vec3(-camera.getPosition().x , -camera.getPosition().y, -camera.getPosition().z) ;
		Matrix4.translate(pos, matrix, matrix) ;
		
		return matrix ;
	}
/*	public static float[] normal() {
		Vec2[] vertices = Coordinates.getVertexVector(new Vec2(1,1)) ; 
		Vec2[] normals = new Vec2[vertices.length];
		for(int i = 0 ; i < vertices.length ; i++) {
			Vec2 currentVertex = vertices[i];
			Vec2 nextVertex = vertices[(i + 1) % vertices.length];
			Vec2 perp = Maths.perpendicular(nextVertex, currentVertex) ;
			perp.normalize() ;
			normals[i] = perp ;
			
		}
		
		float[] normalsArray = new float[normals.length * 2] ;
		
		for(int i = 0 ; i < normalsArray.length ; i+=2) {
			Vec2 v = normals[i/2] ;
			normalsArray[i] = v.x ;
			normalsArray[i +1 ] = v.y ;
			
		}
		
							Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
		return normalsArray ;
	}*/
/*	public static void rotate(float radians, Matrix4 matrix) {
			float radian = (float) Math.toRadians(radians) ;
			float c = (float)Math.cos( radians );
			float s = (float)Math.sin( radians );

			matrix.m00 = c;
			matrix.m01 = s;
			matrix.m10 = -s;
			matrix.m11 = c;
		
	}*/
	
/*	public static Matrix4f orthoMatrix(float left ,float right , float bottom , float top, float near , float far ) {

		Matrix4f matrix = new Matrix4f();
    	matrix.setIdentity() ;
    	matrix.m00 = 2 / (right - left) ;
    	matrix.m11 = 2 / (top - bottom) ;
    	matrix.m22 = -2 / (far - near) ;
    	matrix.m30 = -(right+left)/(right-left) ;
    	matrix.m31 = -(top+bottom)/(top-bottom) ; 
    	matrix.m32 = -(far+near)/(far-near) ; 
    	return matrix ;

        
	}*/
	/**
	 *  dünya oluþturulurken bu dünyanýn oluþturulmasý aslýnda verilen koordinat verilerinin bir çarpan ile çarpýlýp ekrana gösterilmesi ile oluþur. Ýki boyutlu dünya için bu orthographics
	 * camera matrix metodu kullanýlýr ve shaderlarda bu deðer verilen koordinat deðerleri ile çarpýlýr.
	 * Bir kutunun koordinatlarý giriliyormuþ gibi düþünülebilir.
	 * 
	 * {@link https://en.wikipedia.org/wiki/Orthographic_projection}
	 */
	public static Matrix4 orthoMatrix(float left ,float right , float bottom , float top, float near , float far ) {
		Matrix4 matrix = new Matrix4();
		matrix.getMatrix().setIdentity() ;
		matrix.getMatrix().m00 =  2 / (right - left ) ;
		matrix.getMatrix().m01 = 0 ;
		matrix.getMatrix().m02 = 0 ;
		matrix.getMatrix().m03 = 0 ;
		
		matrix.getMatrix().m10 = 0 ;
		matrix.getMatrix().m11 = 2 / (top - bottom) ;
		matrix.getMatrix().m12 = 0 ;
		matrix.getMatrix().m13 = 0 ;
		
		matrix.getMatrix().m20 = 0 ;
		matrix.getMatrix().m21 = 0 ;
		matrix.getMatrix().m22 = -2 / (far - near) ;
		matrix.getMatrix().m23 = 0 ;
		
		matrix.getMatrix().m30 = -(right + left) / (right - left) ;
		matrix.getMatrix().m31 = -(top + bottom) / (top - bottom) ;
		matrix.getMatrix().m32 = -(far + near) / (far - near) ;
		matrix.getMatrix().m33 = 1 ;
		return matrix ;
	}
	
	/**
	 * 3 boyutlu dünyalar oluþturmak için kullanýlan matrix metot.
	 * 
	 * {@link https://en.wikipedia.org/wiki/3D_projection}
	 * 
	 * @return
	 */
	public static Matrix4 createProjectionMatrix() {
		final float FOV = 70;
		final float NEAR_PLANE = 0.1f;
		final float FAR_PLANE = 1000;
		Matrix4 projectionMatrix ;
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4();
		projectionMatrix.getMatrix().m00 = x_scale;
		projectionMatrix.getMatrix().m11 = y_scale;
		projectionMatrix.getMatrix().m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.getMatrix().m23 = -1;
		projectionMatrix.getMatrix().m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.getMatrix().m33 = 0;
		return projectionMatrix;
	}
/*
	public static float cross(Vec2 left , Vec2 right) {
		return left.x * right.y - left.y * right.x ;
	}

	public static Vec2 cross(float left , Vec2 right) {
		Vec2 v = new Vec2(0,0) ;
		v.x = right.y * -left ;
		v.y = right.x * left ;
		return v ;
	}
	public static Vec2 cross(Vec2 left , float right) {
		Vec2 v = new Vec2(0,0) ;
		v.x = left.y * right ;
		v.y = left.x * -right ;
		return v ;
	}
	*/
	//sütun satýr olacak
	/**
	 * normalde 4x4 matrix 4x1 vector ile çarpýlýr fakat bu metot sadece matrixin soldan 2x2 lik kýsmý ile çarpým yapar. Çünkü 2 boyutlu dünyayý ilgilendiren kýsým oraýdýr.
	 * @param m	Matrix deðeri
	 * @param v	Vector deðeri
	 * @return	yeni matrix ile çarpýlmýþ vector deðeri
	 */
	public static Vec2 MatrixMul(Matrix4 m , Vec2 v) {
		float x = m.getMatrix().m00 * v.x ;
		float y =m.getMatrix().m10 * v.y ;
		float w = m.getMatrix().m01 * v.x ;
		float z = m.getMatrix().m11 * v.y ;
		return new Vec2(x+y , w+z) ;
	}
	/**
	 * Matrix transpozunu alan metottur.
	 * @param matrix	transpozu alýnacak matrix sýnýfý
	 * @return		transpozu alýnmýþ yeni matrix
	 */
	public static Matrix4 transpose(Matrix4 matrix) {
		float x = matrix.getMatrix().m00 ;
		float y=  matrix.getMatrix().m01 ;
		float z = matrix.getMatrix().m10 ;
		float w = matrix.getMatrix().m11 ;
		Matrix4 matrix1 = new Matrix4();
		Matrix4.load(matrix, matrix1);
		matrix1.getMatrix().m00 = x ;
		matrix1.getMatrix().m01 = z ;
		matrix1.getMatrix().m10 = y ;
		matrix1.getMatrix().m11 = w ;
		return matrix1 ;
	}
	/**
	 * Ýki noktadan oluþan doðruya inilen dikmeyi hesaplar
	 */
	public static Vec2 perpendicular(Vec2 left , Vec2 right) {
		float x = -(left.y - right.y ) ;
		float y = (left.x - right.x ) ;
		return new Vec2(x,y) ;
	}
	/**
	 * iki vectorün dot product iþlemini gerçekleþtirir.
	 * @param left		2 boyutlu vector.
	 * @param right		2 boyutlu vector.
	 * @return			float dot product sonucu.
	 */
	public static float dot(Vec2 left , Vec2 right) {
		return left.x * right.x + left.y * right.y ;
	}
	/**
	 * gönderilen bir vectorü belli bir sayý ile geniþletme
	 * @param v		vector deðeri
	 * @param s		çarpan
	 * @return		geniþletilmiþ vector.
	 */
	public static Vec2 VectorScale(Vec2 v, float s) {
		Vec2 a = new Vec2(0,0) ;
		a.x = v.x * s ;
		a.y = v.y * s ;
		return a ;
	}
	/**
	 * pisagor teoremi sonucunu bulan formül
	 * @param f1	float deðer
	 * @param f2	float deðer
	 * @return		hipotenüs
	 */
	public static float PythagoreanSolve(float f1 , float f2) {
		return (float) Math.sqrt(f1* f1 + f2* f2) ;
	}

	/**
	 * Bir vectörü negatif deðerini alýr
	 * @param v		Vector deðeri
	 * @return		negatifi alýnmýþ vector.
	 */
	public static Vec2 negate(Vec2 v) {
		Vec2 a = new Vec2(-v.x , -v.y);
		return a ;
	}
/*	public static Vec2 normalise(Vec2 src, Vec2 dest){
		
		float lenSq = src.lengthSquared();

		if (lenSq > EPSILONSQ)
		{
			float invLen = 1.0f / (float)Math.sqrt( lenSq );
			dest.x *= invLen;
			dest.y *= invLen;
		}
		return dest ;
	}
	*/
	/**
	 * Vectorü belli bir radian deðerinde döndüren matematiksel formül
	 * {@link https://matthew-brett.github.io/teaching/rotation_2d.html}
	 * @param v			döndürülecek vector
	 * @param radians	radian deðeri
	 * @return			döndürülmüþ vector.
	 */
	public static Vec2 rotateVector2(Vec2 v , float radians) {
		Vec2 r = new Vec2(0,0);
		float x =(float) ((float) v.x * Math.cos(radians) - (float)v.y *Math.sin(radians)) ;
		float y =(float) ((float) v.x * Math.sin(radians) +(float)v.y * Math.cos(radians)) ;
		
		r.x = x ;
		r.y = y ;
		return r ;
	}

}
