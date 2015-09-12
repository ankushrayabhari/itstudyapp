package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class InputController extends Actor {
	private Vector3 touchPos;
	private Stage stage;
	private Ship ship;
	private float mag, magA;
	private World world;

	public InputController(Stage stage, Ship ship, World world) {
		this.stage = stage;
		this.ship = ship;
		this.world = world;
		touchPos = new Vector3();
	}

	@Override
	public void act(float delta) {
		mag = -Gdx.input.getAccelerometerX() * ship.getSens() * delta;
		ship.moveLateral(mag);

		if (Gdx.input.isKeyPressed(Keys.A)) {
			
			mag = -300 * delta;
			ship.moveLateral(mag);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			mag = 300 * delta;
			ship.moveLateral(mag);
		}
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && ship.lastShotTime >= ship.shotCapO / ship.shotCap) {
			Bullet b = new Bullet(world, ship.getX() + ship.getWidth() / 2 - Textures.bullet.getWidth() / 160, ship.getY() + ship.getHeight());
			b.setTexture(false);
			b.accelerate(true, delta, 4);
			ship.getBullets().add(b);
			stage.addActor(b);
			ship.lastShotTime = 0;
		} else {
			ship.lastShotTime += delta;
		}

	}

}
