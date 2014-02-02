package com.hariharaknarayanan.MemoryGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
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
import com.hariharaknarayanan.MemoryGame.UsefulFunctions.TimerUtilities;

public class StartActivity extends Activity implements OnItemSelectedListener {
	
	private String level;
	private int numLeaders = 5;
	/*private Set<String> easyScores; 
	private Set<String> medScores;
	private Set<String> hardScores;*/ 
	
	private void createHighScoresFile(String filePath)
	{
		File file = new File(getApplicationContext().getFilesDir().getPath().toString() +"/"+filePath);
		if(!file.exists())
		{
			try
			{
				FileOutputStream fileOut = new FileOutputStream(getApplicationContext().getFilesDir().getPath().toString() +"/"+filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.close();
				fileOut.close();
			}
			catch(IOException e)
			{
				System.out.println("Error");
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		
		
		//SharedPreferences sharedPref = getSharedPreferences("MEMORYFILE", 0);
		String levelDefaultValue = (getResources().getStringArray(R.array.level))[0];
		this.level = levelDefaultValue;
		//this.easyScores = sharedPref.getStringSet(getString(R.string.easyscorespref), null);
		//this.medScores = sharedPref.getStringSet(getString(R.string.mediumscorespref), null);
		//this.hardScores = sharedPref.getStringSet(getString(R.string.hardscorespref), null);
		
		createHighScoresFile(getResources().getString(R.string.easyhighscoresfile));
		createHighScoresFile(getResources().getString(R.string.medhighscoresfile));
		createHighScoresFile(getResources().getString(R.string.hardhighscoresfile));		
				
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.level, R.layout.spinner_textitems);
		adapter1.setDropDownViewResource(android.R.layout.select_dialog_item);
		spinner1.setAdapter(adapter1);		
		spinner1.setOnItemSelectedListener(this);
		
		//Intent intent = getIntent();
		//String score = intent.getStringExtra("Score");
		
		/*if(score != null)
		{
			Set<String> s;
			String prefString;
			if(level.compareTo((getResources().getStringArray(R.array.level))[0]) == 0)
			{
				s = this.easyScores;
				prefString = getString(R.string.easyscorespref);
			}
			else if(level.compareTo((getResources().getStringArray(R.array.level))[1]) == 0)
			{
				s = this.medScores;
				prefString = getString(R.string.mediumscorespref);
			}
			else
			{
				s = this.hardScores;
				prefString = getString(R.string.hardscorespref);
			}
			
			if(s == null)
			{
				s = new HashSet<String>();
				s.add(score);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putStringSet(prefString, s);
				editor.commit();
				return;
			}
			
			Iterator<String> sIterator = s.iterator();
			int maxTimeInt = 0;
			String maxTime = "";
			
			while(sIterator.hasNext())
			{
				String time = sIterator.next();
				int timeInt = TimerUtilities.convertMSToSeconds(time);
				
				if(maxTimeInt > timeInt)
				{
					maxTimeInt = timeInt;
					maxTime = time;
				}
			}
			
			int scoreInt = TimerUtilities.convertMSToSeconds(score);
			
			if(maxTimeInt > scoreInt)
			{
				s.remove(maxTime);
				s.add(score);
				
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putStringSet(prefString, s);
				editor.commit();
			}
		}*/				
	}
	
	public void onClickPlay(View v)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("Level", this.level);
		startActivity(intent);
	}
	
	public void goToLeaderboard(View v)
	{
		Intent intent = new Intent(this, LeaderboardActivity.class);

		/*SharedPreferences sharedPref = getSharedPreferences("MEMORYFILE", 0); 
		this.easyScores = sharedPref.getStringSet(getString(R.string.easyscorespref), null);
		this.medScores = sharedPref.getStringSet(getString(R.string.mediumscorespref), null);
		this.hardScores = sharedPref.getStringSet(getString(R.string.hardscorespref), null);
		
		Object[] easyScoresArr = null;
		Object[] medScoresArr = null;
		Object[] hardScoresArr = null;
		
		if(this.easyScores != null)
		{
			easyScoresArr = this.easyScores.toArray();
		}
		if(this.medScores != null)
		{
			medScoresArr = this.medScores.toArray();
		}
		if(this.hardScores != null)
		{
			hardScoresArr = this.hardScores.toArray();
		}
		
		intent.putExtra("Easy", easyScoresArr);
		intent.putExtra("Medium", medScoresArr);
		intent.putExtra("Hard", hardScoresArr);	*/
		
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
