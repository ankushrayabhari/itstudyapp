package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	private ParticleEffect effect;
	private Vector2 pos;
	
	public ParticleEffectActor(String fileName){
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("effects/" + fileName), Gdx.files.internal("effects/"));
		
		pos = new Vector2();
	}
	@Override
	public void act(float delta){
		super.act(delta);
		
		effect.setPosition(pos.x, pos.y);
		effect.update(delta);
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		effect.draw(batch);
		
		
	}
	public ParticleEffect getEffect(){
		return effect;
	}
	public Vector2 getPos(){
		return pos;
	}
}
