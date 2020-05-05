package renderer;

public interface Renderable {
	/**
	 * bu s�n�f olu�tu�unda y�klenmesi gereken durumlar burada ger�ekle�iyor.
	 */
	void init();
	/**
	 * renderlanmadan �nce entitynin hareket ve di�er bilgileri burada g�ncelleniyor.
	 */
	void update();
	/**
	 * OpenGL'in entityi sahip oldu�u Mesh(model) s�n�f�, resim ve resim �zellikleri ile ekrana �iziyor
	 */
	void render();
	/**
	 * program kapand���nda silme i�lemi metodu.
	 */
	void clean();
}
