package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class EnemyGroup extends Actor implements Json.Serializable {
	private Enemy[][] enemies;
	private EnemyBuilder builder;
	protected World world;
	private float duration;
	private int[] healthVals = {100, 200, 300, 400};

	public EnemyGroup(int width, int height, World world) {
		enemies = new Enemy[width][height];
		System.out.println(width + " " + height);

		this.world = world;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i != 0 && j != 0) {
					builder.renew();
				} else {
					builder = new EnemyBuilder(world);
				}
				enemies[i][j] = builder.texture("enemy" + MathUtils.random(3) + ".png").health(healthVals[MathUtils.random(0, healthVals.length - 1)]).build();
				if(((float)width + enemies[i][j].getWidth() < Constants.WIDTH)){
					enemies[i][j].setPos(i + enemies[i][j].getWidth(), Constants.UNIT_HEIGHT
						- enemies[i][j].getHeight() - j);
				} else {
					enemies[i][j].setPos(i / 4 + enemies[i][j].getWidth(), Constants.UNIT_HEIGHT
							- enemies[i][j].getHeight() - j);
				}
				

			}
		}

	}

	public void strafe(float mag) {
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[0].length; j++) {
				if(enemies[i][j] != null){
				enemies[i][j].moveLateral(mag);
				}
			}
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	public void act(float delta) {
		
	}

	public Enemy[][] getEnemies() {
		return enemies;
	}

	@Override
	public void write(Json json) {

	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub

	}

}
