package com.edu.tufts.gameframework;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.app.Activity;
//import android.util.Log;

/**
 * Main menu of the game.
 * 
 * @author www.gametutorial.net
 */

public class MainMenu extends Activity {
	public Boolean isClassic;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

   
	// Start game on click
	public void onClickStartGame(View v) {
		RadioButton rb;
		GamePanel panel = new GamePanel(this);

		rb = (RadioButton) findViewById(R.id.radio0);

		if (rb.isChecked()) {
			panel.isClassic = true;
		} else {
			panel.isClassic = false;
		}

		setContentView(panel);
	}

}
