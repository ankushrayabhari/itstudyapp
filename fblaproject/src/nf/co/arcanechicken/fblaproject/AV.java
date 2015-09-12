package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public enum AV implements GameEnum{
	AV_1(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("AV_1.png"))))), 600, 3),
	AV_2(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("AV_2.png"))))), 800, 7),
	AV_3(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("AV_3.png"))))), 1000, 9);
	
	private Image image;
	private float mag;
	private int cost;
	
	private AV(Image image, float mag, int cost){
		this.image = image;
		this.mag = mag;
		this.cost = cost;
		image.setSize(0.4f, 0.4f);
		image.setScaling(Scaling.fill);
	}
	public int getCost(){
		return cost;
	}
	public Image getImage(){
		return image;
	}
	public float getMag(){
		return mag;
	}

}
