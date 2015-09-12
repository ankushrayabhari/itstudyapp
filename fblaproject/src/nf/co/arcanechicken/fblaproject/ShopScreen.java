package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class ShopScreen extends AbstractScreen {
	private Table shopTable;
	private FBLAProject fp;
	private SelectBox selectBox;
	private Ship ship;
	private Label description, cashLabel;
	private CPU[] values;
	private TextButton buy;
	private Array<TextButton> buttons;
	private Array<Image> images;
	private Array<Label> labels;
	private String enumType;
	private String[] parts = { "Central Processing Unit (CPU)", "Graphics Processing Unit (GPU)",
			"Random Access Memory (RAM)", "Antivirus" };
	private String[] descriptions = {
			"The CPU is the main processing power, and carries out most calculations. Here, CPUs allow for movement speed upgrades.",
			"The GPU manages the graphics on a computer screen and is used extensively in graphically demanding programs like games. Here, GPUs provide heads-up display upgrades.",
			"RAM stores temporary data used by running programs. Here, RAM allows for a greater fire rate (more projectiles stored in memory).",
			"Antivirus programs help protect you from viruses and other malicious programs. Here, antivirus programs give you more health." };

	public ShopScreen(FBLAProject fp, Ship ship) {
		super(fp);

		this.fp = fp;

		buttons = new Array<TextButton>();
		images = new Array<Image>();
		labels = new Array<Label>();

		shopTable = new Table(getSkin());
//		shopTable.debug();
		shopTable.setTransform(true);
		shopTable.setFillParent(true);
		shopTable.setSize(Constants.WIDTH, Constants.HEIGHT);

		shopTable.add("Shop").colspan(6);
		selectBox = new SelectBox(parts, getSkin());
		selectBox.getStyle().font.setScale(1.25f);
		selectBox.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		selectBox.addListener(new ItemSelectionListener());

		shopTable.row().expandX().fill();
		shopTable.add(selectBox).colspan(6).center();

		shopTable.row().expand().fillX();

		description = new Label(descriptions[0], getSkin());
		description.setWrap(true);

		shopTable.add(description).fillY().colspan(6);

		shopTable.row().expand();
		cashLabel = new Label("Cash: $" + ship.getCash(), getSkin());
		shopTable.add(cashLabel).colspan(6);
		values = CPU.values();

		for (int i = 0; i < values.length; i++) {
			shopTable.row().expand();
			images.add(values[i].getImage());
			shopTable.add(images.get(i)).left();
			TextButton button = new TextButton("Buy", getSkin());
			button.addListener(new BuyListener(i));
			buttons.add(button);
			Label label = new Label(String.valueOf(values[i].getMag()) + " speed multiplier, $" + values[i].getCost() * ship.getCashMultiplier(),
					getSkin());
			
			labels.add(label);
			label.setWrap(true);
			
			shopTable.add(label).prefWidth(Constants.WIDTH / 2);
			shopTable.add(button).colspan(3).left().fillX();

		}
		enumType = "cpu";
		TextButton button = new TextButton("Back to Game", getSkin());
		button.addListener(new BackListener());

		shopTable.row().expand();
		shopTable.add(button).colspan(6);
		stage.addActor(shopTable);

	}

	public void setShip(Ship ship) {
		this.ship = ship;
		for(int i = 0; i < values.length; i++){
			labels.get(i).setText(
					String.valueOf(CPU.values()[i].getMag()) + " speed multiplier $" + (values[i].getCost() * ship.getCashMultiplier()));
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		cashLabel.setText("Cash: $" + ship.getCash());
	}

	private class BuyListener extends ClickListener {
		private int i;

		public BuyListener(int i) {
			this.i = i;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);

			if (enumType == "cpu") {
				if (((TextButton) event.getListenerActor()).getText().toString().equals("Equip")) {
					ship.setCPU(CPU.values()[i]);
					((TextButton) event.getListenerActor()).setText("Equipped");
				}
				if (ship.getCash() >= CPU.values()[i].getCost() * ship.getCashMultiplier()
						&& !ship.getCpus().contains(CPU.values()[i], false)) {
					ship.addCPU(CPU.values()[i]);
					ship.addCash(-CPU.values()[i].getCost());
					((TextButton) event.getListenerActor()).setText("Equip");

				}
			}
			if (enumType == "gpu") {
				if (((TextButton) event.getListenerActor()).getText().toString().equals("Equip")) {
					ship.setGPU(GPU.values()[i]);
					((TextButton) event.getListenerActor()).setText("Equipped");
				}
				if (ship.getCash() >= GPU.values()[i].getCost() * ship.getCashMultiplier()
						&& !ship.getGpus().contains(GPU.values()[i], false)) {

					ship.addGPU(GPU.values()[i]);
					ship.addCash(-GPU.values()[i].getCost());
					((TextButton) event.getListenerActor()).setText("Equip");

				}

			}
			if (enumType == "ram") {
				if (((TextButton) event.getListenerActor()).getText().toString().equals("Equip")) {
					ship.setRAM(RAM.values()[i]);
					((TextButton) event.getListenerActor()).setText("Equipped");
				}
				if (ship.getCash() >= RAM.values()[i].getCost() * ship.getCashMultiplier()
						&& !ship.getRams().contains(RAM.values()[i], false)) {
					ship.addRAM(RAM.values()[i]);
					ship.addCash(-RAM.values()[i].getCost());
					((TextButton) event.getListenerActor()).setText("Equip");

				}
			}
			if (enumType == "av") {
				if (((TextButton) event.getListenerActor()).getText().toString().equals("Equip")) {
					ship.setAV(AV.values()[i]);
					((TextButton) event.getListenerActor()).setText("Equipped");
				}
				if (ship.getCash() >= AV.values()[i].getCost() * ship.getCashMultiplier()
						&& !ship.getAvs().contains(AV.values()[i], false)) {
					ship.addAV(AV.values()[i]);
					ship.addCash(-AV.values()[i].getCost());
					((TextButton) event.getListenerActor()).setText("Equip");

				}
			}

		}
	}

	private class ItemSelectionListener extends ChangeListener {
		private ItemSelectionListener() {
		}

		public void changed(ChangeListener.ChangeEvent event, Actor actor) {
			int selectedIndex = ((SelectBox) actor).getSelectionIndex();
			description.setText(descriptions[selectedIndex]);

			// don't try this at home kids

			if (parts[selectedIndex].contains("Central Processing Unit")) {
				for (int i = 0; i < CPU.values().length; i++) {
					images.get(i).setDrawable(
							new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files
									.internal("CPU_Y_" + (i + 1) + ".png")))));

					labels.get(i).setText(
							String.valueOf(CPU.values()[i].getMag()) + " speed multiplier $" + (values[i].getCost() * ship.getCashMultiplier()));
					if (!ship.getCpus().contains(CPU.values()[i], false)) {
						buttons.get(i).setText("Buy");
					} else {
						buttons.get(i).setText("Equip");
					}
				}

				enumType = "cpu";
			}
			if (parts[selectedIndex].contains("Graphics Processing Unit")) {
				for (int i = 0; i < GPU.values().length; i++) {
					images.get(i).setDrawable(
							new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files
									.internal("GPU_AMB_" + (i + 1) + ".png")))));
					labels.get(i).setText(GPU.values()[i].getDescription() + ", $" +  + GPU.values()[i].getCost() * ship.getCashMultiplier());
					if (!ship.getGpus().contains(GPU.values()[i], false)) {
						buttons.get(i).setText("Buy");
					} else {
						buttons.get(i).setText("Equip");
					}
				}
				enumType = "gpu";

			}
			if (parts[selectedIndex].contains("Random Access Memory")) {
				for (int i = 0; i < RAM.values().length; i++) {
					images.get(i).setDrawable(
							new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files
									.internal("RAM_" + (i + 1) + ".png")))));
					labels.get(i).setText("Allows ship to fire every 1/" + (int)RAM.values()[i].getMag() + " second, $" + RAM.values()[i].getCost() * ship.getCashMultiplier());
					if (!ship.getRams().contains(RAM.values()[i], false)) {
						buttons.get(i).setText("Buy");
					} else {
						buttons.get(i).setText("Equip");
					}
				}
				enumType = "ram";
			}
			if (parts[selectedIndex].contains("Antivirus")) {
				for (int i = 0; i < AV.values().length; i++) {
					images.get(i).setDrawable(
							new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files
									.internal("AV_" + (i + 1) + ".png")))));
					labels.get(i).setText("Gives " + AV.values()[i].getMag() + " total health, $" +  + AV.values()[i].getCost() * ship.getCashMultiplier());
					if (!ship.getAvs().contains(AV.values()[i], false)) {
						buttons.get(i).setText("Buy");
					} else {
						buttons.get(i).setText("Equip");
					}
				}
				enumType = "av";
			}

		}
	}

	private class BackListener extends DefaultActorListener {
		public BackListener() {

		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);
			fp.getScreenManager().levelScreen.addEnemies();
			fp.getScreenManager().levelScreen.checkUI();
			fp.setScreen(fp.getScreenManager().levelScreen);

		}

	}

}
