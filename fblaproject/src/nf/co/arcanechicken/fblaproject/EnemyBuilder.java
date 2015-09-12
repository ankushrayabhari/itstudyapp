package nf.co.arcanechicken.fblaproject;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class EnemyBuilder {
private Enemy enemy;
private World world;
	
	public EnemyBuilder(World world){
		this.world = world;
		enemy = new Enemy(world);
	
	}
	public void renew(){
		enemy = new Enemy(world);
	}
	public EnemyBuilder health(float health){
		enemy.setHealth(health);
		return getThis();
	}
	public EnemyBuilder texture(String fileName){
		enemy.setTexture(fileName);
		return getThis();
	}
	
	public Enemy build(){
		return enemy;
	}
	protected EnemyBuilder getThis() {
		return this;
	}
	
	
}
