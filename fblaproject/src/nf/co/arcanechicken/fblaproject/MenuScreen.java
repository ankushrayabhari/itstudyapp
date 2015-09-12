package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends AbstractScreen{
	
	public MenuScreen(final FBLAProject fp){
		super(fp);
		Table table = super.getTable();
		table.setTransform(true);
		table.setFillParent(true);
//		table.debug();
		
		Label label = new Label("Intro to IT Game", getSkin());

		label.getStyle().font.scale(1.25f);
		label.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		table.add(label);
		
		TextButton start = new TextButton("Start", getSkin());
		start.addListener(new DefaultActorListener(){
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				fp.setScreen(fp.getScreenManager().osChooseScreen);
			}
		});
		
		
		table.row();
		table.add(start).fill();
	}
	public void show(){
		super.show();

		
		
	}
}
