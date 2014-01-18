package com.hariharaknarayanan.MemoryGame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	private String level, sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		
		
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.sound, R.layout.spinner_textitems);
		adapter2.setDropDownViewResource(android.R.layout.select_dialog_item);
		spinner2.setAdapter(adapter2);
		
		
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        sound = (String) parentView.getItemAtPosition(position);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		

		});

		
	}
	
	public void goBack(View v)
	{
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
	}
	
	public void saveSettings(View v)
	{
		SharedPreferences sharedPref = getSharedPreferences("MEMORYFILE", 2); 
		//SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.levelpref), level);
		editor.putString(getString(R.string.soundpref), sound);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
