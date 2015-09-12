package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.facebook.Session;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class PostScreen extends AbstractScreen {

	final TextField tf;
	private boolean posted = false;
	private float start = 0f, cap = 2f;
	final TextButton post;
	
	public PostScreen(FBLAPost fp) {
		super(fp);

		Table table = super.getTable();
		
		
		table.add("Post Question / Share Tip");
		table.row().expand();
		
		
		
		Image img = new Image(new Texture(Gdx.files.internal("fbicon.png")));
		table.add(img).width(Constants.WIDTH / 4).height(Constants.HEIGHT / 8);
		
		table.row().expand();
		
		tf = new TextField("", getSkin());
		tf.getStyle().font.scale(2.0f);
		tf.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		table.add(tf).expand().prefWidth(Constants.WIDTH / 1.5f).height(Constants.HEIGHT / 10);

		table.row().expand();
		post = new TextButton("Post", getSkin());
		table.add(post).expand().prefWidth(Constants.WIDTH / 3).prefHeight(Constants.HEIGHT / 10);
		
		table.row().expand();

		
		post.addListener(new DefaultActorListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if(!tf.getText().isEmpty()){
				Session session = Session.getActiveSession();

				FacebookClient fc = new DefaultFacebookClient(session.getAccessToken());

				fc.publish("me/feed", FacebookType.class, Parameter.with("message", tf.getText()));
				prefs = Gdx.app.getPreferences("data");
				points = prefs.getInteger("points");
				points += 5;
				prefs.putInteger("points", points);
				prefs.flush();
				post.setText("Message Posted");
				posted = true;
				
				}
			}
		});
	}

	public void render(float delta) {
		super.render(delta);
		
		if(posted){
			if(start > cap){
				post.setText("Post");
				start = 0f;
				tf.setText("");
			} else {
				start += delta;
			}
		}

	}

}
