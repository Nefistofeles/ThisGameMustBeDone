package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
	
	/**
	 * Dosya okuma metodu
	 * @param name	dosya urlsi
	 * @return		dosyanýn string halindeli içinde bulunan deðerler
	 * @throws Exception	oluþan hatalarý bu metodu çaðýran metoda fýrlatma iþlemi
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
