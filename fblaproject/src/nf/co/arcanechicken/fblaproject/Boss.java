package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Boss extends EnemyGroup {
	
	public Boss() {
		super(1, 1, new World(new Vector2(0, 0), false));
		
	}
	
}
