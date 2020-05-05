package renderer;

public interface Renderable {
	/**
	 * bu sýnýf oluþtuðunda yüklenmesi gereken durumlar burada gerçekleþiyor.
	 */
	void init();
	/**
	 * renderlanmadan önce entitynin hareket ve diðer bilgileri burada güncelleniyor.
	 */
	void update();
	/**
	 * OpenGL'in entityi sahip olduðu Mesh(model) sýnýfý, resim ve resim özellikleri ile ekrana çiziyor
	 */
	void render();
	/**
	 * program kapandýðýnda silme iþlemi metodu.
	 */
	void clean();
}
