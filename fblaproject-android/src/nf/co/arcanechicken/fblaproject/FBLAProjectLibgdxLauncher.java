package nf.co.arcanechicken.fblaproject;

import nf.co.arcanechicken.fblaproject.FBLAProject;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class FBLAProjectLibgdxLauncher extends AndroidApplication{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        initialize(new FBLAProject(), cfg);
    }
}
