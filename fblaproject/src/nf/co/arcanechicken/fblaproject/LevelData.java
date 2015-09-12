package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public enum LevelData {
	INFINITE(2, 3, 5, 6, 1, 3);
	
	private int groupMin, groupMax, widthMin, widthMax, heightMin, heightMax;
	private int groupNumber, width, height;
	private LevelData(int groupMin, int groupMax, int widthMin, int widthMax, int heightMin, int heightMax){

		this.groupMin = groupMin;
		this.groupMax = groupMax;
		groupNumber = MathUtils.random(groupMin, groupMax);
		this.widthMin = widthMin;
		this.widthMax = widthMax;
		width = MathUtils.random(widthMin, widthMax);
		this.heightMin = heightMin;
		this.heightMax = heightMax;
		height = MathUtils.random(heightMin, heightMax);
		
	}
	public int getGroupNumber(){
		return MathUtils.random(groupMin, groupMax);
	}
	public int getGroupWidth(){
		return MathUtils.random(widthMin, widthMax);
	}
	public int getGroupHeight(){
		return MathUtils.random(heightMin, heightMax);
	}
	
}
