package com.hariharaknarayanan.MemoryGame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

import com.hariharaknarayanan.MemoryGame.UsefulFunctions.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class LeaderboardActivity extends Activity implements OnItemSelectedListener {

	private String level;
	
	private void updateScoreView()
	{
		TextView tView = (TextView) findViewById(R.id.textView1);
		tView.setText("");
		
		String filePath = getApplicationContext().getFilesDir().getPath().toString() +"/";
		
		if(level.compareTo("Easy") == 0)
		{
			filePath = filePath + getResources().getString(R.string.easyhighscoresfile);
		}
		else if(level.compareTo("Medium") == 0)
		{
			filePath = filePath + getResources().getString(R.string.medhighscoresfile);
		}
		else
		{
			filePath = filePath + getResources().getString(R.string.hardhighscoresfile);
		}
		
		TreeSet<String> e = null;
		
		try
		{
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (TreeSet<String>) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		} catch (ClassNotFoundException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		
		
		if(e == null || e.size() == 0)
		{
			tView.setText("No scores yet.");
		}
		else
		{
			tView.setText("");
			Iterator<String> sIterator = e.iterator();
			int index = 1;
			
			while(sIterator.hasNext())
			{
				String score = sIterator.next();
				tView.append(String.valueOf(index)+") "+score);
				tView.append("\n\n");
				index++;
			}
		}
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.level, R.layout.spinner_textitems);
		adapter1.setDropDownViewResource(android.R.layout.select_dialog_item);
		spinner1.setAdapter(adapter1);		
		spinner1.setOnItemSelectedListener(this);
		
		this.level = "Easy";
		updateScoreView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.leaderboard, menu);
		return true;
	}
	
	public void goToMain(View v)
	{
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String level = (String) arg0.getItemAtPosition(arg2);
		this.level = level;
		updateScoreView();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
