package nf.co.arcanechicken.fblaproject;

public class ScreenManager {
	public MenuScreen menuScreen;
	public LevelScreen levelScreen;
	public ShopScreen shopScreen;
	public GameOverScreen gameOverScreen;
	public OSChooseScreen osChooseScreen;
	
	public ScreenManager(FBLAProject fp){
		menuScreen = new MenuScreen(fp);
		levelScreen = new LevelScreen(fp);
		shopScreen = new ShopScreen(fp, levelScreen.ship);
		gameOverScreen = new GameOverScreen(fp);
		osChooseScreen = new OSChooseScreen(fp);
	}
	public void reset(FBLAProject fp){
		menuScreen = new MenuScreen(fp);
		levelScreen = new LevelScreen(fp);
		shopScreen = new ShopScreen(fp, levelScreen.ship);
		gameOverScreen = new GameOverScreen(fp);
		osChooseScreen = new OSChooseScreen(fp);
	}
	
	
	
	
}
