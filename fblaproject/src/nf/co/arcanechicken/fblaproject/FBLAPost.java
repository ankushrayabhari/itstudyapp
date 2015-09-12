package nf.co.arcanechicken.fblaproject;

public class FBLAPost extends FBLAProject{
	@Override
	public void create() {
		setScreen(new PostScreen(this));
		
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
