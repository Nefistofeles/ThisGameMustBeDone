package entities;

import org.jbox2d.common.Vec2;

import shaderLoader.ShaderProgram;
import utils.Matrix4;

public class EntityShader extends ShaderProgram {
	
	private static final String vertexShaderFile = "/shaderFile/entityVertexShader.vert";
	private static final String fragmentShaderFile = "/shaderFile/entityFragmentShader.frag";
	
	private int location_projectionMatrix ;
	private int location_transformationMatrix ;
	private int location_viewMatrix ;
	private int location_worldPosition ;
	private int location_textureSize ;
	private int location_offset ;
	private int location_tex_multp ;
	
	public EntityShader() {
		super(vertexShaderFile, fragmentShaderFile);
		
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	public void getUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix") ;
		location_transformationMatrix = super.getUniformLocation("transformationMatrix") ;
		location_viewMatrix = super.getUniformLocation("viewMatrix") ;
		location_worldPosition = super.getUniformLocation("worldPosition") ;
		location_textureSize = super.getUniformLocation("textureSize") ;
		location_offset = super.getUniformLocation("offset") ;
		location_tex_multp = super.getUniformLocation("tex_multp") ;
	}
	
	public void loadProjectionMatrix(Matrix4 matrix) {
		super.loadMatrix4(location_projectionMatrix, matrix);
	}
	public void loadViewMatrix(Matrix4 matrix) {
		super.loadMatrix4(location_viewMatrix, matrix);
	}
	public void loadTransformationMatrix(Matrix4 matrix) {
		super.loadMatrix4(location_transformationMatrix, matrix);
	}
	
	public void loadWorldPosition(float data) {
		super.loadFloat(location_worldPosition, data);
	}
	
	public void loadTextureProperty(int numberOfRow, int numberOfColumn, float offsetX, float offsetY) {
		super.loadVec2(location_textureSize, numberOfRow, numberOfColumn);
		super.loadVec2(location_offset, offsetX, offsetY);
	}
	public void loadTextureMultiplier(Vec2 data) {
		super.loadVec2(location_tex_multp, data);
	}

}
