package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Bullet extends Image{
	private Body body;
	private float damage = 100f;
	private float width = 0.16f, height = 0.7f;
	private boolean dead;
	private World world;
	
	public Bullet(World world, float x, float y){
		this.world = world;
		
		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(width / 2, height / 2);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.filter.categoryBits = 0x0001;
		fd.filter.maskBits = 0x0002 | 0x0004;
		fd.friction = 1.0f;
		fd.density = 10.0f;
		fd.shape = bodyShape;
		body.createFixture(fd);
		body.setUserData(this);
		bodyShape.dispose();
		setSize(width, height);
		setScaling(Scaling.stretch);
		body.setTransform(x - width, y - height, 0);
		setPosition(body.getPosition().x, body.getPosition().y);
	}

	
	public Body getBody(){
		return body;
	}
	public void setDamage(float damage){
		this.damage = damage;
	}
	public float getDamage(){
		return damage;
	}
	public void setDead(boolean dead){
		this.dead = dead;
	}
	public boolean isDead(){
		return dead;
	}
	@Override
	public void act(float delta){
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		
	}
	
	public void accelerate(boolean forward, float delta, float mag){
		if(forward)
			body.setLinearVelocity(0, mag);
		else {
			body.setLinearVelocity(0, -mag);
			
			}
	}
	
	public void setTexture(boolean flip){
		if(flip)
			setDrawable(Textures.bulletFrd);
		else
			setDrawable(Textures.bulletrd);
		
	}
}
