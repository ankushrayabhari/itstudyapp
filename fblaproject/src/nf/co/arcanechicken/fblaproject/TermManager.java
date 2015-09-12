package nf.co.arcanechicken.fblaproject;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TermManager implements Json.Serializable{
	private Array<Word> terms;
	
	public TermManager(){
		terms = new Array<Word>();
	}
	
	public Array<Word> getWords(){
		return terms;
	}
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		terms = json.readValue("terms", Array.class, Word.class, jsonData);
		
	}

}
