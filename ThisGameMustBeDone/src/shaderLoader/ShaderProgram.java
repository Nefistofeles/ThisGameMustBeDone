package shaderLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import utils.Matrix4;

public abstract class ShaderProgram {

	/**
	 * Shader dosyalar�n�n okunmas� attributelar�n bind edilmesi, shader i�lerindeki uniform de�erlerinin y�klemesi i�in gereken s�n�ft�r.
	 */
	
	private final int programID ;
	private int vertexShaderID ;
	private int fragmentShaderID ;
	
	private FloatBuffer buffer ;
	/**
	 * 
	 * @param vertexShader		Vertex Shader urlsi
	 * @param fragmentShader	Fragment Shader urlsi
	 * 
	 * Daha sonra shader dosyalar� okunur, program id olu�turulur ve shader dosyalar� bu id ile birle�tirilir. 
	 * daha sonra program kontrol edilir.En son da uniform de�erler (shader i�inde sonradan girilen de�erler) program ile kayna�t�r�l�r.
	 * 
	 */
	public ShaderProgram(String vertexShader, String fragmentShader) {
		buffer = BufferUtils.createFloatBuffer(16) ;
		
		programID = GL20.glCreateProgram() ;
		vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getUniformLocations();
		
		
	}
	/**
	 * shader program ba�lat�l�r.
	 */
	public void start() {
		GL20.glUseProgram(programID);
	}
	/**
	 * shader program sonland�r�l�r.
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}
	/**
	 * shader da in olarak yaz�lm�� de�erlerin attribute olarak programa tan�t�lmas� gerekir.
	 */
	protected abstract void bindAttributes();
	/**
	 * Shader da tan�mlanm�� sonradan girilen uniform de�erlerin bulundu�u konumlar�n programa tan�t�lmas� i�lemini sa�lar.
	 */
	protected abstract void getUniformLocations();
	
	/**
	 * uniform ismini al�r ve shader taraf�ndan verilmi� location de�eri g�nderilir.
	 * @param name	uniform ismi
	 * @return		uniform location
	 */
	protected int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(programID, name) ;
	}
	/**
	 * in olarak girilen shader de�erlerin programa tan�t�lmas�.
	 * @param index		bulundu�u index (location)
	 * @param name		attribute�n ismi
	 */
	protected void bindAttribute(int index, String name) {
		GL20.glBindAttribLocation(programID, index, name);
	}
	/**
	 * Vector t�r�nde uniform de�erini programa y�klenmesi
	 * @param location	bulundu�u konumu getUniformLocations metodunda al�r ve burada kullan�r.
	 * @param data		Vector t�r�nde de�er
	 */
	protected void loadVec2(int location, Vec2 data) {
		GL20.glUniform2f(location,data.x , data.y);
	}

	protected void loadVec2(int location, float x, float y) {
		GL20.glUniform2f(location,x , y);
	}
	/**
	 * 3 boyutlu vekt�rler i�in kullan�lacak metot
	 * @param location	shaderdaki location de�eri
	 * @param data		3 boyutlu vector de�eri
	 */
	protected void loadVec3(int location, Vec3 data) {
		GL20.glUniform3f(location,data.x , data.y, data.z);
	}
	
	/**
	 * shadera uniform float de�erinin y�klenmesi
	 * @param location	shaderdaki location de�eri
	 * @param data		float data
	 */
	protected void loadFloat(int location, float data) {
		GL20.glUniform1f(location, data);
	}
	protected void loadMatrix4(int location, Matrix4 matrix) {
		matrix.store(buffer) ;
		buffer.flip();
		GL20.glUniformMatrix4(location, false, buffer);
		buffer.clear() ;
	}
	/**
	 * shadera uniform boolean de�erinin y�klenmesi
	 * @param location	shaderdaki location de�eri
	 * @param data		boolean data
	 */
	protected void loadBoolean(int location, boolean data) {
		if(data)
			GL20.glUniform1f(location, 1);
		else
			GL20.glUniform1f(location, 0);
	}
	/**
	 * program kapand���nda shader program ve vertex, fragment shaderlar�n bellekten silinmesini sa�lar.
	 */
	public void clean() {
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	/**
	 * shader dedi�imiz programlar asl�nda bir dosya i�ine yaz�l�yor ve bu dosyan�n okunmas�n� sa�layan metot.
	 * @param url			shader dosya adresi
	 * @param shaderType	shader�n tipi (Vertex, fragment)
	 * @return				program id d�nd�r�r.
	 */
	private int loadShader(String url, int shaderType) {
		BufferedReader reader = null ;
		StringBuilder str = new StringBuilder();
		int shaderID = 0 ;
		try {
			InputStream in = Class.class.getResourceAsStream(url) ;
			reader = new BufferedReader(new InputStreamReader(in)) ;
			String line = null ;
			
			if(reader.ready()) {
				while((line = reader.readLine()) != null) {
					str.append(line).append("\n") ;
				}
			}
			reader.close();
			
			shaderID = GL20.glCreateShader(shaderType) ;
			GL20.glShaderSource(shaderID, str);
			GL20.glCompileShader(shaderID);
			
			if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
				System.out.println(url + " is not compiled");
				System.exit(-1);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			System.exit(-1);
		}
		
		return shaderID ; 
		
	}
}
