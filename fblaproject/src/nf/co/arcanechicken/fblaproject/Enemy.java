package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Enemy extends Image implements Json.Serializable {
	public boolean dead, triggered;
	private float width = 0.35f, height = 0.58f;
	private float health = 200f;
	private Body body;
	private Texture tex;
	private TextureRegionDrawable trd;
	private TextureRegion tr;
	private World world;
	private ParticleEffectActor explosion;
	private Array<Bullet> bullets;
	private float lastShotTime = 0.0f;

	public Enemy(World world) {
		bullets = new Array<Bullet>();
	

		explosion = new ParticleEffectActor("explosion.p");

		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(width / 2, height / 2);

		this.world = world;

		BodyDef bd = new BodyDef();

		bd.type = BodyType.KinematicBody;
		body = world.createBody(bd);
		body.setUserData(this);
		FixtureDef fd = new FixtureDef();
		fd.filter.categoryBits = 0x0004;
		fd.filter.maskBits = 0x0001;
		fd.friction = 1.0f;
		fd.density = 10.0f;
		fd.isSensor = true;
		fd.shape = bodyShape;
		body.createFixture(fd);
		bodyShape.dispose();
		setSize(width, height);
		body.setTransform(width, Constants.UNIT_HEIGHT, 0);
		setPosition(body.getPosition().x, body.getPosition().y);

	}

	public Body getBody() {
		return body;
	}
	public float getHealth(){
		return health;
	}

	public void setPos(float x, float y) {
		body.setTransform(x, y, 0);

	}

	public void damage(float health) {
		this.health -= health;
	}

	public void moveLateral(float mag) {

		body.setLinearVelocity(mag, 0);

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		if (health <= 0) {
			dead = true;

		}
		float temp = MathUtils.random(200);
		if (lastShotTime >= 1.0f && temp == 5) {
			
			fire(delta);
			lastShotTime = 0f;
		} else {
			lastShotTime += delta;
		}

	}

	public ParticleEffectActor getExplosion() {
		return explosion;
	}

	public void setTexture(String fileName) {
		tex = new Texture(Gdx.files.internal(fileName));
		tr = new TextureRegion(tex);
		this.setDrawable(trd = new TextureRegionDrawable(tr));
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getWidth() {
		return width;
	}

	public void fire(float delta) {
		Bullet b = new Bullet(world, getX() + width / 2, getY() + height / 2);
		b.setTexture(true);
		bullets.add(b);
		b.accelerate(false, delta, 4);
		getStage().addActor(b);
	}

	public Array<Bullet> getBullets() {
		return bullets;
	}

	@Override
	public void write(Json json) {

	}

	@Override
	public void read(Json json, JsonValue jsonData) {

	}
}
