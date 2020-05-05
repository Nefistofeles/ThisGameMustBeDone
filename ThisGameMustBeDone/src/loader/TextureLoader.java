package loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import dataStructure.Texture;
import dataStructure.TextureData;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLoader {
	
	public static final int DEFAULT_BIAS = 0x01; 
	public static final int TextureNearest = GL11.GL_NEAREST_MIPMAP_NEAREST ;
	public static final int TextureLinear = GL11.GL_LINEAR_MIPMAP_LINEAR ;
	
	private List<Texture> textures ;
	/**
	 * S�n�f�n temel amac� .png tabanl� resim dosyalar�n� okuyup openglin ekrana basabilece�i �ekile d�n��t�r�p kullanmak.
	 */
	public TextureLoader() {
		textures = new ArrayList<Texture>();
	}
	/**
	 * �smi girilen texturein dosyadan okuyup ayr��t�r�p {@code Texture} s�n�f�na idsini g�ndererek kayd�n�n sa�land��� metottur.
	 * @param name		okunacak resmin dosyadaki ismi
	 * @param nearest	OpenGLde �izdirilecek resmin uzakla�t�k�a bulan�kla�mas�n�n olup olmayaca��n�n ayar�.
	 * @param bias		resim uzakla�t�k�a veya yak�nla�t�k�a istenilen netli�in katsay�s�. T�rt�kl� ve bozuk g�r�len resimler i�in bu ayar yap�l�r.
	 * @return			bir {@code Texture} nesnesi d�nderir.
	 */
	public Texture loadTexture(String name, int nearest, float bias) {
		TextureData data = null ;
		if(!name.contains(".png"))
			data = textureDecomposition("/res/"+ name + ".png");
		else
			data = textureDecomposition("/res/"+ name);
		
		int textureID = GL11.glGenTextures() ;
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_NEAREST);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_NEAREST);
	
	    
	    if(nearest != 0) {
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, nearest);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, nearest);
	        
	    }
	    
	    
	    if(bias == DEFAULT_BIAS) {
	    	 GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1.4f);
	    	 
	    }else {
	    	GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
	    }
	    
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        return new Texture(textureID) ;
	
	}
	
	/**
	 * PNGDecoder kullanarak resmin ayr��t�rmas�n� yapan metottur.
	 * @param url		resmin adresi
	 * @return			{@code TextureData} �eklinde bir data tutucu d�nderir.
	 */
	public TextureData textureDecomposition(String url) {
		TextureData textureData = null ;
		InputStream in = Class.class.getResourceAsStream(url);
		try {
			PNGDecoder decoder = new PNGDecoder(in);
			ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();
			textureData = new TextureData(decoder.getWidth(), decoder.getHeight(), buf) ;
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textureData ;
	}
	/**
	 * Program kapand���nda ramda tutulan resim kay�tlar� idlerine g�re silinir.
	 */
	public void clean() {
		for(Texture texture : textures) {
			GL11.glDeleteTextures(texture.getTextureID());
		}
	}
}
