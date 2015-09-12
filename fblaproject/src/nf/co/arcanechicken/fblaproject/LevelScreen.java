package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class LevelScreen extends AbstractScreen {
	private int level;
	public Ship ship;
	private EnemyGroup group;
	private InputController inputController;
	private Array<Body> bodies;
	private int count = 0, groupNumber, groupCount;
	private LevelData[] values;
	private Label[][] labels;
	private float leftDuration = 1.0f, leftTime, rightDuration = 1.0f, rightTime, mag;
	private Table uiTable;
	private Label shipHealth;

	public LevelScreen(FBLAProject fp) {
		super(fp);

		stage.setViewport(Constants.UNIT_WIDTH, Constants.UNIT_HEIGHT, false);

		bodies = new Array<Body>();
		labels = null;

		values = LevelData.values();

		groupNumber = values[level].getGroupNumber();

		group = new EnemyGroup(values[level].getGroupWidth(), values[level].getGroupHeight(), world);

		ship = Ship.create("playerShip.png", Constants.UNIT_WIDTH / 2, 0, world);

		world.setContactListener(new PhysicsContactListener(ship));
		stage.addActor(ship);
		stage.addActor(group);
		for (int i = 0; i < group.getEnemies().length; i++) {
			for (int j = 0; j < group.getEnemies()[0].length; j++) { // columns
				stage.addActor(group.getEnemies()[i][j]);
			}
		}

		inputController = new InputController(stage, ship, world);
		stage.addActor(inputController);

		uiTable = new Table(getSkin());
		uiStage.addActor(uiTable);
		uiTable.invalidate();
		shipHealth = new Label(String.valueOf(ship.getHealth()), getSkin());

	}

	@Override
	public void show() {
		super.show();

	}

	public void checkUI() {
		if (ship.getGpu() == GPU.AMB_1 || ship.getGpu() == GPU.AMB_3) {
			shipHealth.invalidate();
			shipHealth.setPosition(ship.getX(), ship.getY());
			uiStage.addActor(shipHealth);

		}
		if (ship.getGpu() == GPU.AMB_2 || ship.getGpu() == GPU.AMB_3) {
			labels = new Label[group.getEnemies().length][group.getEnemies()[0].length];

			for (int k = 0; k < group.getEnemies().length; k++) {
				for (int l = 0; l < group.getEnemies()[0].length; l++) {
					if (group.getEnemies()[k][l] != null) {
						Label label = new Label("HP: "
								+ String.valueOf(group.getEnemies()[k][l].getHealth()), getSkin());
						label.invalidate();
						label.setPosition(group.getEnemies()[k][l].getX() * 80,
								group.getEnemies()[k][l].getY() * 80);
						uiStage.addActor(label);

						labels[k][l] = label;
					} else {
						labels[k][l] = null;
					}
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		shipHealth.setText(String.valueOf(ship.getHealth()));
		shipHealth.setColor(Color.WHITE);

		if (rightTime <= rightDuration) {
			mag = 1.0f;
			rightTime += delta;
		} else {
			mag = -1.0f;
			leftTime += delta;
			if (leftTime >= leftDuration) {
				rightTime = 0;
				leftTime = 0;
			}
		}
		group.strafe(mag);
		if (ship.getHealth() <= 0) {
			fp.setScreen(fp.getScreenManager().gameOverScreen);
			System.out.println("over");
		}
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			fp.getScreenManager().shopScreen.setShip(ship);
			fp.setScreen(fp.getScreenManager().shopScreen);
		}
		for (int i = 0; i < ship.getBullets().size; i++) {
			if (ship.getBullets().get(i).getY() >= Constants.UNIT_HEIGHT) {
				ship.getBullets().get(i).setDead(true);

			}
			if (ship.getBullets().get(i).isDead()) {
				ship.getBullets().get(i).getBody().setUserData(null);
				world.destroyBody(ship.getBullets().get(i).getBody());
				stage.getActors().removeValue(ship.getBullets().get(i), true);
				ship.getBullets().removeIndex(i);
			}

		}

		world.getBodies(bodies);

		for (int j = 0; j < bodies.size; j++) {
			if (bodies.get(j).getUserData() instanceof Bullet
					&& ((Bullet) bodies.get(j).getUserData()).isDead()) {
				((Bullet) bodies.get(j).getUserData()).setDead(true);
			}
		}
		for (int k = 0; k < group.getEnemies().length; k++) {
			for (int l = 0; l < group.getEnemies()[0].length; l++) {
			

				if (labels != null && k > group.getEnemies().length && l > group.getEnemies()[0].length && group.getEnemies()[k][l] != null && k > labels.length && l > labels[0].length && labels[k][l] != null) {
					labels[k][l].setPosition(group.getEnemies()[k][l].getX() * 80,
							group.getEnemies()[k][l].getY() * 80);
					labels[k][l].setText(String.valueOf(group.getEnemies()[k][l].getHealth()));

				}
// if(group.getEnemies()[k][l].getBullets() != null)
				if (group.getEnemies()[k][l] != null)
					for (int i = 0; i < group.getEnemies()[k][l].getBullets().size; i++) {
						if (group.getEnemies()[k][l].getBullets().get(i).getY() <= 0) {
							group.getEnemies()[k][l].getBullets().get(i).setDead(true);

						}
						if (group.getEnemies()[k][l].getBullets().get(i).isDead()) {

							group.getEnemies()[k][l].getBullets().get(i).getBody()
									.setUserData(null);
							world.destroyBody(group.getEnemies()[k][l].getBullets().get(i)
									.getBody());
							stage.getActors().removeValue(
									group.getEnemies()[k][l].getBullets().get(i), true);
							group.getEnemies()[k][l].getBullets().removeIndex(i);
						}

					}

				if (group.getEnemies()[k][l] != null && group.getEnemies()[k][l].dead) {
					ship.addCash(ship.getCashPerE());
					prefs = Gdx.app.getPreferences("data");
					points = prefs.getInteger("points");
					points++;
					prefs.putInteger("points", points);
					prefs.flush();

					if (labels != null) {
						uiStage.getActors().removeValue(labels[k][l], true);

						labels[k][l] = null;
					}

					fp.getScreenManager().shopScreen.setShip(ship);
					stage.addActor(group.getEnemies()[k][l].getExplosion());
					group.getEnemies()[k][l].getExplosion().getPos()
							.set(group.getEnemies()[k][l].getX(), group.getEnemies()[k][l].getY());
					group.getEnemies()[k][l].getExplosion().getEffect().start();
					world.destroyBody(group.getEnemies()[k][l].getBody());
					group.getEnemies()[k][l].getBody().setUserData(null);
					stage.getActors().removeValue(group.getEnemies()[k][l], true);
					group.getEnemies()[k][l] = null;
					count++;

				}
				if (count == group.getEnemies().length * group.getEnemies()[0].length) {

					groupCount++;

					for (int i = 0; i < ship.getBullets().size; i++) {
						ship.getBullets().get(i).setDead(true);

					}
					count = 0;

					group = new EnemyGroup(values[level].getGroupWidth(),
							values[level].getGroupHeight(), world);
					prefs = Gdx.app.getPreferences("data");
					prefs.putInteger("points", points);
					prefs.flush();

					fp.setScreen(fp.getScreenManager().shopScreen);
					break;

				}
				// check

			}

		}

	}

	public void addEnemies() {

		for (int i = 0; i < group.getEnemies().length; i++) {
			for (int j = 0; j < group.getEnemies()[0].length; j++) { // columns
				if (group.getEnemies()[i][j] != null)
					stage.addActor(group.getEnemies()[i][j]);
			}
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
