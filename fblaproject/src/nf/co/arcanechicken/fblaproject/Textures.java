package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Textures {
	public static Texture bullet = new Texture(Gdx.files.internal("laser_blue.png"));
	public static TextureRegion bulletr = new TextureRegion(bullet);
	public static TextureRegionDrawable bulletrd = new TextureRegionDrawable(bulletr);
	public static Texture bulletF = new Texture(Gdx.files.internal("laser_red.png"));
	public static TextureRegion bulletFr = new TextureRegion(bulletF);
	public static TextureRegionDrawable bulletFrd = new TextureRegionDrawable(bulletFr);
	
}
