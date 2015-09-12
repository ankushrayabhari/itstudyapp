package nf.co.arcanechicken.fblaproject;


import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameOverScreen extends AbstractScreen {

	public GameOverScreen(FBLAProject fp) {
		super(fp);
		
		Table table = super.getTable();
		table.add("Game Over");
		table.setTransform(true);
		table.setFillParent(true);
		
		TextButton button = new TextButton("Back to Game", getSkin());
		button.getStyle().font.scale(1.5f);
		button.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button.addListener(new BackListener());
		
		table.row();
		
		table.add(button).height(Constants.HEIGHT / 8).width(Constants.WIDTH / 2);
	
		
	}
	@Override
	public void show(){
		super.show();
	}
	private class BackListener extends DefaultActorListener {
		public BackListener() {

		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);
			fp.getScreenManager().reset(fp);
			fp.setScreen(fp.getScreenManager().menuScreen);

		}

	}
}
