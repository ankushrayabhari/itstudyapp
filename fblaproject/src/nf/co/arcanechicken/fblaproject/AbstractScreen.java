package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen {
	private Table table;
	private SpriteBatch batch = null;
	private Skin skin;
	private BitmapFont font;
	private TextureAtlas atlas;
	private Box2DDebugRenderer debugRenderer;
	protected final Stage stage;
	protected final FBLAProject fp;
	protected OrthographicCamera cam;
	protected World world;
	protected Stage uiStage;
	protected Preferences prefs;
	protected static int points;

	public AbstractScreen(FBLAProject fp) {
		this.fp = fp;
		stage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		uiStage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		cam = (OrthographicCamera) stage.getCamera();
		world = new World(new Vector2(0, 0.0f), false);
		debugRenderer = new Box2DDebugRenderer();

	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public Table getTable() {
		if (table == null) {
			table = new Table(getSkin());
			table.setFillParent(true);
			stage.addActor(table);

		}
		return table;

	}

	protected Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}

	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont();
		}
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) {
			batch = new SpriteBatch();
		}
		return batch;
	}

	public TextureAtlas getAtlas() {
		if (atlas == null) {
			atlas = new TextureAtlas(Gdx.files.internal("image-atlases/pack.atlas"));
		}

		return this.atlas;
	}

	public void resize(int width, int height) {

	}

	public void render(float delta) {

		stage.act(delta);
		uiStage.act(delta);

		
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Gdx.gl.glClear(16384);
		stage.draw();
		uiStage.draw();

		cam.update();

		world.step(1 / 60f, 3, 3); // error line

//		debugRenderer.render(world, cam.combined);
//		Table.drawDebug(stage);
//		Table.drawDebug(uiStage);
		getBatch().setProjectionMatrix(cam.combined);
	}

	public void hide() {

		dispose();
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {
		
	}

}
