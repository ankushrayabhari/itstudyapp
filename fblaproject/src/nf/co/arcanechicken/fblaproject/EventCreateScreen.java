package nf.co.arcanechicken.fblaproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class EventCreateScreen extends AbstractScreen {
	String[] ampm = { "AM", "PM" };
	public Date d, e;
	public boolean publish;
	private boolean created;
	private final TextButton make;
	private float start = 0f, cap = 2f;

	public EventCreateScreen(FBLAEvent fp) {
		super(fp);

		Session session = Session.getActiveSession();

		Table table = super.getTable();
// table.debug();
		table.setTransform(true);
		table.setFillParent(true);
		table.setSize(Constants.WIDTH, Constants.HEIGHT);

		Label tf = new Label("Create Study Event", getSkin());
		tf.getStyle().font.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tf.getStyle().font.scale(1.4f);
		table.add(tf);

		Calendar c = Calendar.getInstance();

		final TextField day = new TextField(String.valueOf(c.get(GregorianCalendar.DAY_OF_MONTH)),
				getSkin());

		final SelectBox month = new SelectBox(new String[] { "January", "Feburary", "March",
				"April", "May", "June", "July", "August", "September", "October", "November",
				"December" }, getSkin());
		month.setSelection(c.get(GregorianCalendar.MONTH));
// final TextField month = new
// TextField(String.valueOf(c.get(GregorianCalendar.MONTH) + 1),
// getSkin());

		final TextField year = new TextField(String.valueOf(c.get(GregorianCalendar.YEAR)),
				getSkin());
		final TextField hour = new TextField(String.valueOf(c.get(GregorianCalendar.HOUR)),
				getSkin());
		final TextField minute = new TextField(String.valueOf(c.get(GregorianCalendar.MINUTE)),
				getSkin());
		final SelectBox m = new SelectBox(ampm, getSkin());
		m.setSelection(c.get(GregorianCalendar.AM_PM));

		final TextField length = new TextField("60", getSkin());

		Image img = new Image(new Texture(Gdx.files.internal("fbicon.png")));
		table.add(img).width(Constants.WIDTH / 4).height(Constants.HEIGHT / 8);

		table.row().fill();
		Label monthLabel = new Label("Month: ", getSkin());
		table.add(monthLabel).fill().prefWidth(Constants.WIDTH / 2)
				.prefHeight(Constants.WIDTH / 10);
		table.add(month).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label dayLabel = new Label("Day: ", getSkin());
		table.add(dayLabel).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.add(day).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label yearLabel = new Label("Year: ", getSkin());
		table.add(yearLabel).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.add(year).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label hourLabel = new Label("Hour: ", getSkin());
		table.add(hourLabel).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.add(hour).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label minuteLabel = new Label("Minute: ", getSkin());
		table.add(minuteLabel).fill().prefWidth(Constants.WIDTH / 2)
				.prefHeight(Constants.WIDTH / 10);
		table.add(minute).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label ml = new Label("AM/PM: ", getSkin());
		table.add(ml).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.add(m).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();
		Label lengthLabel = new Label("Length (minutes): ", getSkin());
		table.add(lengthLabel).fill().prefWidth(Constants.WIDTH / 2)
				.prefHeight(Constants.WIDTH / 10);
		table.add(length).fill().prefWidth(Constants.WIDTH / 2).prefHeight(Constants.WIDTH / 10);
		table.row().fill();

		make = new TextButton("Create Event", getSkin());
		table.add(make).colspan(2).prefHeight(Constants.WIDTH / 9);
		make.addListener(new DefaultActorListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				Session session = Session.getActiveSession();

				FacebookClient fc = new DefaultFacebookClient(session.getAccessToken());

				Calendar cal = Calendar.getInstance();

				cal.set(Integer.valueOf(year.getText()), month.getSelectionIndex(),
						Integer.valueOf(day.getText()), Integer.valueOf(hour.getText()),
						Integer.valueOf(minute.getText()));

				d = cal.getTime();

				cal.set(Integer.valueOf(year.getText()), month.getSelectionIndex(),
						Integer.valueOf(day.getText()), Integer.valueOf(hour.getText()),
						Integer.valueOf(minute.getText()) + Integer.valueOf(length.getText()));

				e = cal.getTime();

				User user = fc.fetchObject("me", User.class);

				fc.publish("me/events", FacebookType.class,
						Parameter.with("name", "FBLA Study Event"),
						Parameter.with("start_time", d), Parameter.with("end_time", e));

				prefs = Gdx.app.getPreferences("data");
				points = prefs.getInteger("points");
				points++;
				prefs.putInteger("points", points);
				prefs.flush();
				
				make.setText("Event Created");
				created = true;

			}
		});

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (created) {
			if (start > cap) {
				make.setText("Create Event");
				start = 0f;
			} else {
				start += delta;
			}
		}

	}

}
