package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Word implements Json.Serializable{

	private String term, def;
	
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		term = json.readValue("name", String.class, jsonData);
		def = json.readValue("def", String.class, jsonData);
		
		
	}

}
