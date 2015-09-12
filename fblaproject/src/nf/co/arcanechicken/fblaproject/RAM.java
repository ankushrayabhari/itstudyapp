package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public enum RAM  implements GameEnum{
	RAM_1(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("RAM_1.png"))))), 2, 2),
	RAM_2(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("RAM_2.png"))))), 4, 5),
	RAM_3(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("RAM_3.png"))))), 6, 8);
	
	private Image image;
	private float mag;
	private int cost;
	
	private RAM(Image image, float mag, int cost){
		this.image = image;
		this.mag = mag;
		this.cost = cost;
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
