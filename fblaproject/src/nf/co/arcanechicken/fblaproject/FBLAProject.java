package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FBLAProject extends Game {
	private ScreenManager screenManager;
	private JsonManager jsonManager;
	protected int points;
	
	@Override
	public void create() {
		jsonManager = new JsonManager();	
		screenManager = new ScreenManager(this);
		setScreen(screenManager.menuScreen);
	}

	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}
	
	public ScreenManager getScreenManager(){
		return screenManager;
	}
	public JsonManager getJsonManager(){
		return jsonManager;
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
