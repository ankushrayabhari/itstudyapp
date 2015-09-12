package nf.co.arcanechicken.fblaproject;

import java.io.File;
import java.io.FileOutputStream;

import co.nf.arcanechicken.fblaproject.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class InfoScreen extends Activity{
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.info);
		
		File file = getApplicationContext().getFileStreamPath("info.txt");
		if(file.exists()){
			Intent intent = new Intent(InfoScreen.this,
					FBLAProjectLauncher.class);
			InfoScreen.this.startActivity(intent);
		}
		System.out.println(file.exists());
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {	
				Spinner spinner = (Spinner) findViewById(R.id.spinner1);
				
				File file = getApplicationContext().getFileStreamPath("info.txt");
				String filename = "info.txt";
				String string = spinner.getSelectedItem().toString();
				FileOutputStream outputStream;
				
				if(!file.exists()){
					try {
				  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);	
				  outputStream.write(string.getBytes());
				  outputStream.close();
				} catch (Exception e) {
				  e.printStackTrace();
				}

				}
				Intent intent = new Intent(InfoScreen.this,
						FBLAProjectLauncher.class);
				InfoScreen.this.startActivity(intent);
			}
		});
		
		
	}
	
}
