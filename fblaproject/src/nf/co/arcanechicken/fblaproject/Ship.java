package nf.co.arcanechicken.fblaproject;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Ship extends Image {
	public float lastShotTime = 1f, shotCapO = 1f, shotCap = 2f;
	private Body body;
	private float width = 1.0f, height = 0.75f, health = 500f, multiplier = 1f;
	private Array<Bullet> bullets;
	private World world;
	private OS os;
	private Array<CPU> cpus;
	private CPU cpu;
	private Array<GPU> gpus;
	private GPU gpu;
	private Array<RAM> rams;
	private RAM ram;
	private Array<AV> avs;
	private AV av;
	private int cash;
	private int cashPerE = 1;
	private int cashMultiplier;

	public int getCash() {
		return cash;
	}
	public void setCashPerE(int c){
		cashPerE = c;
	}
	public int getCashPerE(){
		return cashPerE;
	}

	public Array<CPU> getCpus() {
		return cpus;
	}

	public void setCpus(Array<CPU> cpus) {
		this.cpus = cpus;
	}

	public Array<GPU> getGpus() {
		return gpus;
	}

	public void setGpus(Array<GPU> gpus) {
		this.gpus = gpus;
	}

	public Array<RAM> getRams() {
		return rams;
	}

	public void setRams(Array<RAM> rams) {
		this.rams = rams;
	}

	public Array<AV> getAvs() {
		return avs;
	}
	public AV getAV(){
		return av;
	}

	public void setAvs(Array<AV> avs) {
		this.avs = avs;
	}

	public void addCash(int cash) {
		this.cash += cash;
	}

	public AV getHdd() {
		return av;
	}

	public GPU getGpu() {
		return gpu;
	}

	public void setGPU(GPU gpu) {
		this.gpu = gpu;
	}

	public void setAV(AV av) {
		this.av = av;
		if(av != null)
			health = av.getMag();
	}

	public void setRAM(RAM ram) {
		this.ram = ram;
	}

	public RAM getRAM() {
		return ram;
	}

	public OS getOS() {
		return os;
	}

	public void setCPU(CPU cpu) {
		this.cpu = cpu;
	}

	public void damage(float damage) {
		health -= damage;
	}

	private float sens = 90f;

	public Ship(Texture tex, float x, float y, World world) {
		super(tex);

		this.world = world;

		bullets = new Array<Bullet>();
		cpus = new Array<CPU>();
		gpus = new Array<GPU>();
		avs = new Array<AV>();
		rams = new Array<RAM>();
		

		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(width / 2.5f, height / 2.5f);

		BodyDef bd = new BodyDef();
		

		bd.type = BodyType.KinematicBody;
		body = world.createBody(bd);
		body.setUserData(this);
		FixtureDef fd = new FixtureDef();
		fd.friction = 1.0f;
		fd.filter.categoryBits = 0x0002;
		fd.filter.maskBits = 0x001;
		fd.density = 10.0f;
		fd.isSensor = true;
		fd.shape = bodyShape;
		body.createFixture(fd);
		bodyShape.dispose();
		setSize(width, height);
		body.setTransform(Constants.UNIT_WIDTH / 2 - width / 2, height / 2, 0);
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

	}

	public void addCPU(CPU cpu) {
		cpus.add(cpu);
	}

	public void addGPU(GPU gpu) {
		gpus.add(gpu);
	}

	public void addRAM(RAM ram) {
		rams.add(ram);
	}

	public void addAV(AV av) {
		avs.add(av);
	}

	public CPU getCPU() {
		return cpu;
	}

	public float getSens() {
		return sens;
	}

	public void setOS(OS os) {
		this.os = os;
		setCashPerE(os.getCashPerE());
		cashMultiplier = os.getPriceMultiplier();
	}

	public int getCashMultiplier(){
		return cashMultiplier;
	}
	public Body getBody() {
		return body;
	}

	public Array<Bullet> getBullets() {
		return bullets;
	}

	public float getHealth() {
		return health;
	}

	public static Ship create(String fileName, float x, float y, World world) {
		Texture tex = new Texture(Gdx.files.internal(fileName));
		return new Ship(tex, x, y, world);
	}

	public World getWorld() {
		return world;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

		if(cpu != null)
		multiplier = cpu.getMag();
		if(ram != null)
		shotCap = ram.getMag();
		

	}

	public void moveLateral(float mag) {

		if (mag < 0 && body.getPosition().x > width / 2f) {
			body.setLinearVelocity(mag * multiplier, 0);
		} else {

			if (mag > 0 && body.getPosition().x < Constants.UNIT_WIDTH - width / 2f) {
				body.setLinearVelocity(mag * multiplier, 0);
			} else {
				body.setLinearVelocity(0, 0);
			}
		}
		if (mag == 0) {
			body.setLinearVelocity(0, 0);
		}
	}

}
