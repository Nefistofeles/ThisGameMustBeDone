package utils;

import java.nio.IntBuffer;

import org.jbox2d.common.Vec2;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;


public class Coordinates {
	
	/**
	 * Bu sýnýf hazýr koordinat düzlemi deðerlerin hazýr kayýtlý halini barýndýrýr. Deneme amaçlý kullanýlan sýnýftýr.
	 *
	 */
	
/*	public static MeshData createMeshModel(Loader loader) {
		return loader.getModelLoader().loadMeshG(Vertex1, TextureCoords) ;
	}*/
	
	/**
	 * OpenGL'de daire oluþturmak için yazýlan özel metot.
	 * @param radius		dairerin yarýçapý
	 * @param position		dairenin pozisyonu
	 * @return				dairenin tüm koordinatlarýný tutan float array
	 */
	public static float[] create(float radius, Vector2f position) {
		int segment = 20 ;
		Vector2f p = new Vector2f(0, 0) ;
		Vector2f[] model = new Vector2f[segment] ;
		float[] mA = new float[segment * 2] ;
		float theta =  0 ;
		float inc =  (float) (Math.PI * 2 / segment);
		
		for(int i = 0 ; i < segment ; i++) {
			
			theta += inc; 
			p = new Vector2f((float)Math.cos(theta), (float)Math.sin(theta)) ;
			p.x *= radius ;
			p.y *= radius ;

			model[i] = new Vector2f(p);
		}
		
		for(int i = 0 ; i < segment ; i++) {
			int b = (i) * 2 ;
			
			mA[b] = model[i].x ;
			mA[b + 1] = model[i].y ;
		}
		
		return mA ;
		
	}
	public static final float[] triangleStripVertex = {
			-0 , 0 ,
			-0 , -1 
			, 1 ,0 
			,1 , -1
			
	};
	public static final float[] triangleStripVertexLight = {
	        0.f, 0.f,
	        1, 0.f,
	        1, 1,
	        0.f, 1
			
	};
	

/*	public static final float VertexLine[] = {
			-1.0f, -1.0f,
			1.0f , -1.0f,
			1.0f , 1.0f,
			-1.0f, 1.0f 
	};
	private static final float Vertex1[] = {
			-1.0f, -1.0f,
			1.0f , -1.0f,
			1.0f , 1.0f,
			-1.0f, 1.0f 
	};*/

	public static Vec2[] getVertexVector(Vec2 scale) {
		Vec2 VertexVector[] = {
				new Vec2(-1.0f * scale.x, -1.0f *scale.y),
				new Vec2(1.0f *scale.x, -1.0f*scale.y),
				new Vec2(1.0f *scale.x, 1.0f*scale.y),
				new Vec2(-1.0f*scale.x, 1.0f *scale.y) 
		};
		return VertexVector; 
	}


	/*public static final Vec2 VertexVector[] = {
			new Vec2(-1.0f * 5, -1.0f * 5),
			new Vec2(1.0f *5 , -1.0f * 5),
			new Vec2(1.0f *5 , 1.0f*5),
			new Vec2(-1.0f*5, 1.0f *5) 
			
			
	};*/


	public static final float Vertex[] = {
		-1.0f, 1.0f,
		-1.0f, -1.0f,
		1.0f,-1.0f,
		1.0f,1.0f
				
	};
	public static final float TextureCoords[] = {
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f
	};
	public static final float TextureCoordsIndic[] = {
			0.0f, 0.0f,1.0f,
			0.0f, 0.0f,1.0f,
			0.0f, 0.0f,1.0f,
			0.0f, 0.0f,1.0f
	};
	public static final int indices[] = {
			0,1,3,
			3,1,2
	};
	public static final float ParticleVertex[] = {
			-1.0f,1.0f,
			-1.0f,-1.0f,
			1.0f,1.0f,
			1.0f,-1.0f
	};
	/**
	 * iki noktadan oluþan bir vektörün normalini bulan fonksiyon.
	 *TODO : Düzenlenmesi gerek.
	 * 
	 */
	public static float[] normal() {
		Vec2[] vertices = getVertexVector(new Vec2(1,1)) ; 
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
		
		/*					Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());*/
		return normalsArray ;
	}
	public static IntBuffer getIndicesBuffer() {
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length) ;
		indicesBuffer.put(indices) ;
		indicesBuffer.flip() ;
		return indicesBuffer ; 
	}


}
