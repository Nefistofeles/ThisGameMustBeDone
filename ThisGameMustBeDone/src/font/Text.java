package font;

import org.jbox2d.common.Vec2;

import dataStructure.Mesh;
import loader.FontMeshLoader;
import utils.Matrix4;

public class Text {

	private String text ;
	private Mesh mesh ;
	private Vec2 position ;
	private float rotation ;
	private Vec2 scale ;
	private float worldPosition ;
	private Matrix4 transformationMatrix ;
	/**
	 * Sýnýfýn temel amacý oluþturulan textin bilgilerinin tutulmasýdýr.
	 * @param text		Ekrana çizilmesi istenilen text
	 * @param position	ekrandaki pozisyonu
	 * @param rotation	döndürülmek isteniyorsa istenilen bilgi
	 * @param scale		ekrandaki boyutu
	 * 
	 * Tabi çizim yapýlýrken ilgili shadera bilgiler matrix üzerinden gönderildiði için bir transformation matrixde oluþturulur.
	 */
	
	public Text(String text, Vec2 position, float rotation, Vec2 scale) {
		
		this.text = text;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		worldPosition = 9 ;
		this.transformationMatrix = Matrix4.createTransformationMatrix(position, rotation, scale);
	}
	
	public void setText(String text, FontMeshLoader fmLoader) {
		this.text = text;
		fmLoader.loadMeshforFont(this);
	}

	public String getText() {
		return text;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Vec2 getScale() {
		return scale;
	}

	public void setScale(Vec2 scale) {
		this.scale = scale;
	}

	public Matrix4 getTransformationMatrix() {
		transformationMatrix = Matrix4.updateTransformationMatrix(transformationMatrix, position, rotation, scale) ;
		return transformationMatrix;
	}


	public Mesh getMesh() {
		return mesh ;
	}
	public void setMesh(Mesh mesh) {
		this.mesh = mesh ;
	}
	public float getWorldPosition() {
		return worldPosition;
	}
	public void setWorldPosition(float worldPosition) {
		this.worldPosition = worldPosition;
	}
}
