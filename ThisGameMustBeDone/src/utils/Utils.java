package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
	
	/**
	 * Dosya okuma metodu
	 * @param name	dosya urlsi
	 * @return		dosyan�n string halindeli i�inde bulunan de�erler
	 * @throws Exception	olu�an hatalar� bu metodu �a��ran metoda f�rlatma i�lemi
	 */
	public static StringBuilder loadFile(String name) throws Exception{
		
		InputStream in = Class.class.getResourceAsStream(name) ;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ;
		String line = "" ;
		StringBuilder string = new StringBuilder();
		
		if(reader.ready()) {
			while((line = reader.readLine()) != null) {
				string.append(line).append("\n") ;
			}
		}
		
		reader.close();
		
		return string ;
	}
	
}
