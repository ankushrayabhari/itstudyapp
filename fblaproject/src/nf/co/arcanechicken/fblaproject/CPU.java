package nf.co.arcanechicken.fblaproject;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public enum CPU implements GameEnum{
	Y_1(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("CPU_Y_1.png"))))), 2, 2),
	Y_2(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("CPU_Y_2.png"))))), 4, 5),
	Y_3(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("CPU_Y_3.png"))))), 6, 8);
	
	private Image image;
	private float mag;
	private int cost;
	
	private CPU(Image image, float mag, int cost){
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
	public void get(){
		System.out.println(Arrays.asList(CPU.values()).indexOf(this));
	}
}
