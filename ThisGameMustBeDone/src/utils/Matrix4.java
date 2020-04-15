package utils;

import java.nio.FloatBuffer;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Matrix4 {
	//lwjgl içinde bulunan Matrix4f classýný kullanarak 2 boyuta uyarlanmýþ yeni Matrix4 classý.

	private Matrix4f matrix;

	public Matrix4() {
		matrix = new Matrix4f();
	}

	public static Matrix4 createTransformationMatrix(Vec2 position , float rotation, Vec2 scale) {
		Matrix4 matrix = new Matrix4();
		matrix.getMatrix().setIdentity() ;
		Matrix4.translate(position, matrix, matrix);
		Matrix4.rotate(rotation, matrix) ;
		if(scale.x < 0 ) {
			scale.x = 0 ;
		}
		if(scale.y < 0) {
			scale.y = 0 ;
		}
		Matrix4.scale(scale, matrix, matrix) ;
		
		return matrix ;
		
	}

	public static Matrix4 updateTransformationMatrix(Matrix4 matrix, Vec2 position, float rotation, Vec2 scale) {
		matrix.getMatrix().setIdentity();
		Matrix4.translate(position, matrix, matrix);
		Matrix4.rotate(rotation, matrix);
		if (scale.x < 0) {
			scale.x = 0;
		}
		if (scale.y < 0) {
			scale.y = 0;
		}
		Matrix4.scale(scale, matrix, matrix);

		return matrix;
	}

	public static Vec2 MatrixMul(Matrix4 m, Vec2 v) {
		float x = m.getMatrix().m00 * v.x;
		float y = m.getMatrix().m10 * v.y;
		float w = m.getMatrix().m01 * v.x;
		float z = m.getMatrix().m11 * v.y;
		return new Vec2(x + y, w + z);
	}

	public static Vec2 MatrixMulPlusPos(Matrix4 m, Vec2 v, Vec2 pos) {
		float x = m.getMatrix().m00 * v.x;
		float y = m.getMatrix().m10 * v.y;
		float w = m.getMatrix().m01 * v.x;
		float z = m.getMatrix().m11 * v.y;
		return new Vec2(x + y + pos.x, w + z + pos.y);
	}

	public static void rotate(float radians, Matrix4 matrix) {
		float radian = (float) Math.toRadians(radians);
		float c = (float) Math.cos(radian);
		float s = (float) Math.sin(radian);

		matrix.getMatrix().m00 = c;
		matrix.getMatrix().m01 = s;
		matrix.getMatrix().m10 = -s;
		matrix.getMatrix().m11 = c;

	}
	
	public static Vec4 transform(Matrix4 src, Vec4 v , Vec4 dest) {
		Vector4f d = new Vector4f(dest.x, dest.y, dest.z, dest.w) ;
		Matrix4f.transform(src.getMatrix(), new Vector4f(v.x, v.y, v.z , v.w), d) ;
		dest.x = d.x ;
		dest.y = d.y ;
		dest.z = d.z ;
		dest.w = d.w ;
		return dest ;
	}
	public void store(FloatBuffer buffer) {
		matrix.store(buffer);
	}
	public static Matrix4 load(Matrix4 src, Matrix4 dest) {
		if(dest == null) {
			dest = new Matrix4();
		}
		
		Matrix4f.load(src.getMatrix(), dest.getMatrix()) ;
		
		return dest ;
	}
	public static Matrix4 inverse(Matrix4 src, Matrix4 dest) {
		if(dest == null) {
			dest = new Matrix4();
		}
		Matrix4f.invert(src.getMatrix(), dest.getMatrix()) ;
		return dest ;
	}
	public static Matrix4 translate(Vec2 v, Matrix4 src, Matrix4 dest) {
		if(dest == null) {
			dest = new Matrix4();
		}
		Matrix4f.translate(new Vector2f(v.x, v.y), src.getMatrix(), dest.getMatrix()) ;
		return dest ;
	}
	public static Matrix4 translate(Vec3 v, Matrix4 src, Matrix4 dest) {
		if(dest == null) {
			dest = new Matrix4();
		}
		Matrix4f.translate(new Vector3f(v.x, v.y, v.z), src.getMatrix(), dest.getMatrix()) ;
		return dest ;
	}
	public static void scale(Vec2 v, Matrix4 src, Matrix4 dest) {
		if(dest == null) {
			dest = new Matrix4();
		}
		Matrix4f.scale(new Vector3f(v.x, v.y, 0), src.getMatrix(), dest.getMatrix()) ;
	}
	public Matrix4f getMatrix() {
		return matrix;
	}
	
}
