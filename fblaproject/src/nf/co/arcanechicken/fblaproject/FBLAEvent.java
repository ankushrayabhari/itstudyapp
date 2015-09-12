package nf.co.arcanechicken.fblaproject;

import java.util.Calendar;
import java.util.Date;

import com.facebook.Session;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class FBLAEvent extends FBLAProject{
	@Override
	public void create() {
		setScreen(new EventCreateScreen(this));
		
		
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
