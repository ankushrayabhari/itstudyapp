package nf.co.arcanechicken.fblaproject;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;

public class LearnScreen extends AbstractScreen {
	private TextField input;
	private FileHandle termsFile;
	private TermManager tm;
	private int index = 0;
	private TextButton prev, next;

	public LearnScreen(FBLALearn fp) {
		super(fp);

		Json json = new Json();

		termsFile = Gdx.files.internal("terms.json");

		tm = ((TermManager) json.fromJson(TermManager.class, termsFile.readString()));

		
		
		Table table = super.getTable();
		final Label learn = new Label("Learn", getSkin());
		learn.getStyle().font.scale(1.5f);
		learn.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		learn.setColor(new Color(0.5f, 0.5f, 1.0f, 1f));
		table.add(learn).colspan(3).fillY();
// table.debug();

		table.row().fillX();
		final Label current = new Label("", getSkin());
		prev = new TextButton("<", getSkin());
		prev.setVisible(false);
		prev.addListener(new DefaultActorListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if(index != 0){
				index--;
				current.setText(tm.getWords().get(index).getTerm());
				learn.setText("Term");
				}
			}
		});

		next = new TextButton(">", getSkin());
		next.addListener(new DefaultActorListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if(index + 1 < tm.getWords().size){
				index++;
				current.setText(tm.getWords().get(index).getTerm());
				learn.setText("Term");
				}
				
			}
		});

		table.add(prev).prefWidth(Constants.WIDTH / 6);

		current.addListener(new DefaultActorListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if (((Label) event.getListenerActor()).getText().toString().equals(
						tm.getWords().get(index).getTerm())) {
					((Label) event.getListenerActor()).setText(tm.getWords().get(index).getDef());
					prefs = Gdx.app.getPreferences("data");
					points = prefs.getInteger("points");
					points++;
					prefs.putInteger("points", points);
					prefs.flush();
					learn.setText("Definition");
				} else {
					((Label) event.getListenerActor()).setText(tm.getWords().get(index).getTerm());
					learn.setText("Term");
				}
				
			}
		});
		current.setWrap(true);
		current.setAlignment(0, 1);
		table.add(current).expandX().fillX().center();
		table.add(next).prefWidth(Constants.WIDTH / 6);
		
		current.setText(tm.getWords().get(index).getTerm());
		

	}
	public void render(float delta){
		super.render(delta);
		
		if(index == 0){
			prev.setVisible(false);
		} else {
			prev.setVisible(true);
		}
		if(index == tm.getWords().size - 1){
			next.setVisible(false);
		} else {
			next.setVisible(true);
		}
	}

}
