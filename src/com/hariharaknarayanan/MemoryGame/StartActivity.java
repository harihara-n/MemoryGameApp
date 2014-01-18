package com.hariharaknarayanan.MemoryGame;

import java.util.Set;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class StartActivity extends Activity implements OnItemSelectedListener {
	
	private String level;
	private int numLeaders = 5;
	private Set<String> easyScores; 
	private Set<String> medScores;
	private Set<String> hardScores; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		SharedPreferences sharedPref = getSharedPreferences("MEMORYFILE", 0); 
		this.level = sharedPref.getString(getString(R.string.levelpref), null);
		this.easyScores = sharedPref.getStringSet(getString(R.string.easyscorespref), null);
		this.medScores = sharedPref.getStringSet(getString(R.string.mediumscorespref), null);
		this.hardScores = sharedPref.getStringSet(getString(R.string.hardscorespref), null);
		
		if(this.level == null)
		{
			String levelDefaultValue = (getResources().getStringArray(R.array.level))[0];
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(getString(R.string.levelpref), levelDefaultValue);
			editor.commit();
		}
		
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.level, R.layout.spinner_textitems);
		adapter1.setDropDownViewResource(android.R.layout.select_dialog_item);
		spinner1.setAdapter(adapter1);		
		spinner1.setOnItemSelectedListener(this);	
							
	}
	
	public void onClickPlay(View v)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("Level", this.level);
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		this.level = (String) arg0.getItemAtPosition(arg2);		
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
