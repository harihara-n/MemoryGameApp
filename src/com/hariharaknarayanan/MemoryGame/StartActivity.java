package com.hariharaknarayanan.MemoryGame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class StartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		//SharedPreferences sharedPref = getSharedPreferences("HomeSetting", 0);
		String levelValue = sharedPref.getString(getString(R.string.levelpref), null);
		String soundValue = sharedPref.getString(getString(R.string.soundpref), null);
		
		if(levelValue == null)
		{
			String levelDefaultValue = (getResources().getStringArray(R.array.level))[0];
			String soundDefaultValue = (getResources().getStringArray(R.array.sound))[0];
			
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(getString(R.string.levelpref), levelDefaultValue);
			editor.putString(getString(R.string.soundpref), soundDefaultValue);
			editor.commit();
		}    
							
	}
	
	public void onClickPlay(View v)
	{
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	public void onSettingsClick(View v)
	{
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
