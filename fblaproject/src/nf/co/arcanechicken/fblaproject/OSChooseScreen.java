package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class OSChooseScreen extends AbstractScreen {
	private FBLAProject fp;
	private SelectBox oses;
	private OS os;
	private OS[] values;
	private Label desc;
	private String[] names = { "Windows", "Mac", "Linux" };
	private String[] descriptions = {
			"Windows ships are the most vulnerable to viruses, and have the lowest health, but are compatible with the most programs. Windows ships can scavenge more out of dead enemies, and have moderately pricy upgrades.",
			"Mac ships are inbetween Windows and Linux in terms of vulnerability to viruses, so they have moderate health. Macs scavenge an average amount out of dead enemies, but cost the most.",
			"Linux ships are the least vulnerable to viruses, and have the highest health. Linux is free, so the ship upgrades are a lot cheaper, but also the least compatible, so there's less scavenging from dead enemies." };

	public OSChooseScreen(FBLAProject fp) {
		super(fp);

		this.fp = fp;
		
		values = OS.values();
		os = OS.WINDOWS;

		Table table = super.getTable();
		table.setTransform(true);
		table.setFillParent(true);
		table.setSize(Constants.WIDTH, Constants.HEIGHT);
		Label label = new Label("Choose Ship Operating System", getSkin());
		
		table.add(label);
		
		
		
		table.row().expandX().fill().height(Constants.HEIGHT / 10);
		
		oses = new SelectBox(names, getSkin());
		oses.addListener(new SelectBoxListener());
		table.add(oses);
		
		table.row().expand().fill();
		
		
		desc = new Label(descriptions[oses.getSelectionIndex()], getSkin());
		desc.getStyle().font.setScale(1.75f);
		desc.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		desc.setWrap(true);
		table.add(desc);
		
		table.row().expandX().fill().height(Constants.HEIGHT / 7);
		

		TextButton button = new TextButton("Continue", getSkin());
		table.add(button).prefHeight(Constants.HEIGHT / 12);

		button.addListener(new BackListener());

	}

	private class BackListener extends DefaultActorListener {
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);
			fp.getScreenManager().levelScreen.addEnemies();
			fp.getScreenManager().levelScreen.checkUI();
			fp.getScreenManager().levelScreen.ship.setOS(os);
			fp.setScreen(fp.getScreenManager().levelScreen);

		}
	}

	private class SelectBoxListener extends ChangeListener {
		private SelectBoxListener() {
		}

		public void changed(ChangeListener.ChangeEvent event, Actor actor) {
			int selectedIndex = ((SelectBox) actor).getSelectionIndex();
			os = values[selectedIndex];
			desc.setText(descriptions[selectedIndex]);
		}
	}
}
