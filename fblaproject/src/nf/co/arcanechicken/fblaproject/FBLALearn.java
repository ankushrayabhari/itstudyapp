package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FBLALearn extends FBLAProject{
	
	
	@Override
	public void create() {
		setScreen(new LearnScreen(this));
		
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
