package com.hariharaknarayanan.MemoryGame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.ObjectInputStream;
import java.util.Comparator;
import java.util.TreeSet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.hariharaknarayanan.MemoryGame.UsefulFunctions.UserScores;

public class ResultsActivity extends Activity {
	
	private String score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		TextView tView = (TextView)findViewById(R.id.textView1);
		Intent intent = getIntent();
		
		boolean finished = intent.getBooleanExtra("Finished", false);
		
		if (!finished)
		{
			TextView tView2 = (TextView)findViewById(R.id.textView2);
			tView.setText("Sorry. Time's up!");
			tView2.setText("");
		}
		else
		{		
			score = intent.getStringExtra("Time");
			tView.setText("Your time - "+score);
			//tView.setTextAlignment(tView.TEXT_ALIGNMENT_CENTER);
			
			String level = intent.getStringExtra("Level");
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
			
			if(e == null)
			{
				e = new TreeSet<String>();
			}
			
			boolean writeScore = false;
			
			if(e.size() < getResources().getInteger(R.integer.HighScoresLimit))
			{
				e.add(score);
				writeScore = true;
			}
			else
			{
				if(score.compareTo(e.descendingIterator().next()) < 0)
				{
					e.remove(e.last());
					e.add(score);
					writeScore = true;
				}				
			}
			
			if(writeScore)
			{
				TextView tView2 = (TextView) findViewById(R.id.textView2);
				tView2.setText("Congratulations! High Score!");
				try
				{
					FileOutputStream fileOut = new FileOutputStream(filePath);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(e);
					out.close();
					fileOut.close();
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}
	
	public void goToMain(View v)
	{
		Intent intent = new Intent(this, StartActivity.class);
		intent.putExtra("Score", score);
		startActivity(intent);
	}

}
