package dataStructure;

import java.nio.ByteBuffer;

public class TextureData {
	
	/**
	 * Texture�n ilk PNGDecoder taraf�ndan okunurken uzunluk ve y�ksekli�ini ayr�ca byte �eklinde bir bufferda bilgilerini tutmak i�in kullan�lan s�n�ft�r.
	 */

	private int width;
	private int height ;
	private ByteBuffer buffer ;
	
	public TextureData(int width, int height, ByteBuffer buffer) {
		
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}
	
	
	
}
