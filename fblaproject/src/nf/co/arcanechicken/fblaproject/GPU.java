package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum GPU implements GameEnum {
	AMB_1(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("GPU_AMB_1.png"))))), 3, "Displays your own health."),
	AMB_2(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("GPU_AMB_2.png"))))), 3, "Displays enemy health."),
	AMB_3(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("GPU_AMB_3.png"))))), 7, "Displays both your own and enemy health.");
	
private Image image;
private int cost;
private String desc;
	
	private GPU(Image image, int cost, String desc){
		this.image = image;
		this.cost = cost;
		this.desc = desc;
		image.setSize(0.4f, 0.4f);
	}
	public Image getImage(){
		return image;
	}
	public int getCost(){
		return cost;
	}
	public String getDescription(){
		return desc;
	}
}
