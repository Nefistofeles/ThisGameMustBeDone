package dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import animationSystem.AnimationData;
import animationSystem.AnimationEnum;
import contactlisteners.EntityType;
import entities.Bullet;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import loader.Creator;
import loader.TextureLoader;
import utils.Coordinates;
import utils.Maths;

public class EntityData {

	private Creator creator;
	private short entityType;
	private Mesh mesh;
	private Texture texture;
	private AnimationData animationData;
	private BodyData bodyData;
	private List<PhysicModelData> modelDatas;

	public EntityData(Creator creator) {
		this.creator = creator;
		animationData = new AnimationData();
		modelDatas = new ArrayList<PhysicModelData>();
		bodyData = new BodyData();
	}

	public short getEntityType() {
		return entityType;
	}

	public void setEntityType(short entityType) {
		this.entityType = entityType;
	}

	public void loadMesh(Mesh mesh) {
		System.out.println(mesh.getMeshID() + " y�klendi");
		this.mesh = mesh;
	}

	public void loadTexture(Texture texture) {
		this.texture = texture;

	}

	public void loadBodyData(BodyType bodyType, Vec2 position, float rotation, Vec2 scale, float worldPosition,
			boolean fixedRotation, boolean isBullet) {
		bodyData.setBodyType(bodyType);
		bodyData.setBullet(isBullet);
		bodyData.setFixedRotation(fixedRotation);
		bodyData.setPosition(position);
		bodyData.setRotation(rotation);
		bodyData.setScale(scale);
		bodyData.setWorldPosition(worldPosition);
	}

	public void loadPhysicModelData(String name, Vec2[] model, ShapeType shapeType, float restitution, float density,
			float friction, float radius, boolean isSensor) {
		PhysicModelData modelData = new PhysicModelData();
		modelData.setModelName(name);
		modelData.setDensity(density);
		modelData.setFriction(friction);
		modelData.setModel(model);
		modelData.setRadius(radius);
		modelData.setRestitution(restitution);
		modelData.setSensor(isSensor);
		modelData.setShapeType(shapeType);
		modelDatas.add(modelData);
	}

	/**
	 * Resim animasyon �zelli�i ta��yorsa o resmin genel animasyon �zelliklerinin
	 * girildi�i k�s�m
	 * 
	 * @param e    Animasyon idsi
	 * @param data i�erisinde sat�r�n toplam resim say�s� ve ka��nc� sat�rda
	 *             bulundu�una dair string de�er. Parse edilip de�erler
	 *             g�nderiliyor.
	 */
	public void loadAnimationData(AnimationEnum e, String data) {
		if (data == "0") {
			return;
		}
		String[] parser = data.split("/");
		System.out.println(data);
		animationData.addAnimationData(e, Integer.parseInt(parser[0]), Integer.parseInt(parser[1]));

	}

	/**
	 * Entityi olu�turan yap�
	 * 
	 * @param entityType    Entity nesnesinin yap�s� (Player veya Enemynin alt
	 *                      s�n�flar�) tabi daha sonra ekleme yap�l�rsa buras�
	 *                      g�ncellenecektir.
	 * @param position      entitiynin d�nyadaki koordinat�
	 * @param rotation      entitynin rotation de�eri. Yani ne kadar d�nd�r�lm��
	 * @param scale         entitynin scale de�eri. Sadece koordinatlar� y�klemek
	 *                      yetmez belli bir katsay� ile �arp�p b�y�tmek veya
	 *                      k���ltmek i�in kullan�l�r.
	 * @param worldPosition normalde 2 boyutlu bir oyun fakat baz� �zel durumlar
	 *                      i�in worldposition yani z ekseni ile de oynama
	 *                      yap�labiliyor.
	 */
	private Entity loadEntity(short entityType, Vec2 position, float rotation, Vec2 scale, float worldPosition) {
		Entity entity = null;
		if (entityType == EntityType.BIT_PLAYER) {
			entity = new Player(mesh.getClone(), texture.getClone(), position, rotation, scale, worldPosition,
					animationData.getClone(), creator);
			System.out.println("player");
		} else if (entityType == EntityType.BIT_ZOMBIE) {
			entity = new Zombie(mesh.getClone(), texture.getClone(), position, rotation, scale, worldPosition,
					animationData.getClone(), creator);
			System.out.println("zombie");
		} else if (entityType == EntityType.BIT_BULLET) {
			entity = new Bullet(mesh.getClone(), texture.getClone(), position, rotation, scale, worldPosition,
					animationData.getClone(), creator);
			System.out.println("bullet");
		}

		return entity;

	}

	/**
	 * Olu�turulan entitye gerekli fizik �zellikleri eklemek yani bodysini
	 * olu�turmak i�in kullan�lan metot.
	 * 
	 * @param bodyType      olu�turulacak g�vdenin t�r� statik dinamik veya
	 *                      kinematik
	 * @param fixedRotation bodynin d�nd�r�l�p d�nd�r�lmemesini ayarlamak i�in
	 *                      girilen de�er
	 * @param isBullet      h�zl� nesneler i�in girilen de�er.
	 */
	private Body loadBody(Entity entity, BodyType bodyType, boolean fixedRotation, boolean isBullet) {
		return creator.getLoader().getPhysicsLoader().loadBody(entity, bodyType, fixedRotation, isBullet);

	}

	/**
	 * Olu�turulan bodynin bir bir �eklini olu�turan metot
	 * 
	 * TODO : Shape e girilen koordinatlar ge�ici onlar karaktere g�re tekrar
	 * yap�land�r�lmal�
	 */
	private Shape loadShapePolygon(Entity entity, Vec2[] data) {
		return creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(),
				Maths.multipleVec2Datas(data, entity.getScale()));

	}

	/**
	 * Olu�turulacak �ekil e�er daire ise kullan�lmas� gereken metottur.
	 * 
	 * @param radius dairenin yar��ap�
	 */
	private Shape loadShapeCircle(Entity entity, float radius) {
		return creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), radius);
	}

	/**
	 * Olu�turulan bodynin yo�unlu�u, s�rt�nme katsay�s� gibi �zelliklerin girilip
	 * bodye fiziksel �zelliklerin eklenmesinin sa�layan s�n�f.
	 * 
	 * @param restitution esneklik katsay�s�
	 * @param density     yo�unluk katsay�s�
	 * @param friction    s�rt�nme katsay�s�
	 * @param isSensor    bu �zellik alg�lay�c� m� (ileri d�zey bir �zellik
	 *                    kullanacak m�y�m bilmiyorum.)
	 */
	private Fixture loadFixture(Body body, Shape shape, float restitution, float density, float friction,
			boolean isSensor, short categoryBits, short maskBits) {
		return creator.getLoader().getPhysicsLoader().loadFixture(shape, body, restitution, density, friction, isSensor,
				categoryBits, maskBits);
	}

	public Entity loadEntity(Vec2 position, Vec2 scale, short categoryBits, short maskBits) {
		if (position == null)
			position = new Vec2(bodyData.getPosition().x, bodyData.getPosition().y);
		if (scale == null)
			scale = new Vec2(bodyData.getScale().x, bodyData.getScale().y);

		Entity entity = loadEntity(entityType, position, bodyData.getRotation(), scale, bodyData.getWorldPosition());
		Body body = loadBody(entity, bodyData.getBodyType(), bodyData.isFixedRotation(), bodyData.isBullet());

		for (PhysicModelData modeldata : modelDatas) {
			Shape shape = null;
			Fixture fixture = null;
			if (modeldata.getShapeType() == ShapeType.POLYGON) {
				shape = loadShapePolygon(entity, modeldata.cloneModel());
			} else if (modeldata.getShapeType() == ShapeType.CIRCLE) {
				shape = loadShapeCircle(entity, modeldata.getRadius());
			}
			fixture = loadFixture(body, shape, modeldata.getRestitution(), modeldata.getDensity(),
					modeldata.getFriction(), modeldata.isSensor(), categoryBits, maskBits);
			entity.addFixture(modeldata.getModelName(), fixture);
		}
		entity.addBody(body);
		return entity;
	}

}
