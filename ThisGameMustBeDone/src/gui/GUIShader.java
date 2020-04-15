package gui;

import shaderLoader.ShaderProgram;
import utils.Matrix4;

public class GUIShader extends ShaderProgram {

	private static final String vertexShader = "/shaderFile/guiVertexShader.vert";
	private static final String fragmentShader = "/shaderFile/guiFragmentShader.frag";

	private int location_projectionMatrix;
	private int location_transformationMatrix;
	private int location_worldPosition;

	public GUIShader() {
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");

	}

	@Override
	protected void getUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_worldPosition = super.getUniformLocation("worldPosition");

	}

	public void loadProjectionMatrix(Matrix4 matrix) {
		super.loadMatrix4(location_projectionMatrix, matrix);
	}

	public void loadTransformationMatrix(Matrix4 matrix) {
		super.loadMatrix4(location_transformationMatrix, matrix);
	}

	public void loadWorldPosition(float data) {
		super.loadFloat(location_worldPosition, data);
	}

}
