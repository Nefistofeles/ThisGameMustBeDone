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
		System.out.println(mesh.getMeshID() + " yüklendi");
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
	 * Resim animasyon özelliði taþýyorsa o resmin genel animasyon özelliklerinin
	 * girildiði kýsým
	 * 
	 * @param e    Animasyon idsi
	 * @param data içerisinde satýrýn toplam resim sayýsý ve kaçýncý satýrda
	 *             bulunduðuna dair string deðer. Parse edilip deðerler
	 *             gönderiliyor.
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
	 * Entityi oluþturan yapý
	 * 
	 * @param entityType    Entity nesnesinin yapýsý (Player veya Enemynin alt
	 *                      sýnýflarý) tabi daha sonra ekleme yapýlýrsa burasý
	 *                      güncellenecektir.
	 * @param position      entitiynin dünyadaki koordinatý
	 * @param rotation      entitynin rotation deðeri. Yani ne kadar döndürülmüþ
	 * @param scale         entitynin scale deðeri. Sadece koordinatlarý yüklemek
	 *                      yetmez belli bir katsayý ile çarpýp büyütmek veya
	 *                      küçültmek için kullanýlýr.
	 * @param worldPosition normalde 2 boyutlu bir oyun fakat bazý özel durumlar
	 *                      için worldposition yani z ekseni ile de oynama
	 *                      yapýlabiliyor.
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
	 * Oluþturulan entitye gerekli fizik özellikleri eklemek yani bodysini
	 * oluþturmak için kullanýlan metot.
	 * 
	 * @param bodyType      oluþturulacak gövdenin türü statik dinamik veya
	 *                      kinematik
	 * @param fixedRotation bodynin döndürülüp döndürülmemesini ayarlamak için
	 *                      girilen deðer
	 * @param isBullet      hýzlý nesneler için girilen deðer.
	 */
	private Body loadBody(Entity entity, BodyType bodyType, boolean fixedRotation, boolean isBullet) {
		return creator.getLoader().getPhysicsLoader().loadBody(entity, bodyType, fixedRotation, isBullet);

	}

	/**
	 * Oluþturulan bodynin bir bir þeklini oluþturan metot
	 * 
	 * TODO : Shape e girilen koordinatlar geçici onlar karaktere göre tekrar
	 * yapýlandýrýlmalý
	 */
	private Shape loadShapePolygon(Entity entity, Vec2[] data) {
		return creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(),
				Maths.multipleVec2Datas(data, entity.getScale()));

	}

	/**
	 * Oluþturulacak þekil eðer daire ise kullanýlmasý gereken metottur.
	 * 
	 * @param radius dairenin yarýçapý
	 */
	private Shape loadShapeCircle(Entity entity, float radius) {
		return creator.getLoader().getPhysicsLoader().loadShape(entity.getPosition(), radius);
	}

	/**
	 * Oluþturulan bodynin yoðunluðu, sürtünme katsayýsý gibi özelliklerin girilip
	 * bodye fiziksel özelliklerin eklenmesinin saðlayan sýnýf.
	 * 
	 * @param restitution esneklik katsayýsý
	 * @param density     yoðunluk katsayýsý
	 * @param friction    sürtünme katsayýsý
	 * @param isSensor    bu özellik algýlayýcý mý (ileri düzey bir özellik
	 *                    kullanacak mýyým bilmiyorum.)
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
