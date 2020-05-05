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
	 * Shader dosyalarýnýn okunmasý attributelarýn bind edilmesi, shader içlerindeki uniform deðerlerinin yüklemesi için gereken sýnýftýr.
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
	 * Daha sonra shader dosyalarý okunur, program id oluþturulur ve shader dosyalarý bu id ile birleþtirilir. 
	 * daha sonra program kontrol edilir.En son da uniform deðerler (shader içinde sonradan girilen deðerler) program ile kaynaþtýrýlýr.
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
	 * shader program baþlatýlýr.
	 */
	public void start() {
		GL20.glUseProgram(programID);
	}
	/**
	 * shader program sonlandýrýlýr.
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}
	/**
	 * shader da in olarak yazýlmýþ deðerlerin attribute olarak programa tanýtýlmasý gerekir.
	 */
	protected abstract void bindAttributes();
	/**
	 * Shader da tanýmlanmýþ sonradan girilen uniform deðerlerin bulunduðu konumlarýn programa tanýtýlmasý iþlemini saðlar.
	 */
	protected abstract void getUniformLocations();
	
	/**
	 * uniform ismini alýr ve shader tarafýndan verilmiþ location deðeri gönderilir.
	 * @param name	uniform ismi
	 * @return		uniform location
	 */
	protected int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(programID, name) ;
	}
	/**
	 * in olarak girilen shader deðerlerin programa tanýtýlmasý.
	 * @param index		bulunduðu index (location)
	 * @param name		attributeýn ismi
	 */
	protected void bindAttribute(int index, String name) {
		GL20.glBindAttribLocation(programID, index, name);
	}
	/**
	 * Vector türünde uniform deðerini programa yüklenmesi
	 * @param location	bulunduðu konumu getUniformLocations metodunda alýr ve burada kullanýr.
	 * @param data		Vector türünde deðer
	 */
	protected void loadVec2(int location, Vec2 data) {
		GL20.glUniform2f(location,data.x , data.y);
	}

	protected void loadVec2(int location, float x, float y) {
		GL20.glUniform2f(location,x , y);
	}
	/**
	 * 3 boyutlu vektörler için kullanýlacak metot
	 * @param location	shaderdaki location deðeri
	 * @param data		3 boyutlu vector deðeri
	 */
	protected void loadVec3(int location, Vec3 data) {
		GL20.glUniform3f(location,data.x , data.y, data.z);
	}
	
	/**
	 * shadera uniform float deðerinin yüklenmesi
	 * @param location	shaderdaki location deðeri
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
	 * shadera uniform boolean deðerinin yüklenmesi
	 * @param location	shaderdaki location deðeri
	 * @param data		boolean data
	 */
	protected void loadBoolean(int location, boolean data) {
		if(data)
			GL20.glUniform1f(location, 1);
		else
			GL20.glUniform1f(location, 0);
	}
	/**
	 * program kapandýðýnda shader program ve vertex, fragment shaderlarýn bellekten silinmesini saðlar.
	 */
	public void clean() {
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	/**
	 * shader dediðimiz programlar aslýnda bir dosya içine yazýlýyor ve bu dosyanýn okunmasýný saðlayan metot.
	 * @param url			shader dosya adresi
	 * @param shaderType	shaderýn tipi (Vertex, fragment)
	 * @return				program id döndürür.
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
