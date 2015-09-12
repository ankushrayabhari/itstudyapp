package nf.co.arcanechicken.fblaproject;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class DefaultActorListener extends InputListener {
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		return true;
	}
}

