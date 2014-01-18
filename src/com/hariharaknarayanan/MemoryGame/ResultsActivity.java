package com.hariharaknarayanan.MemoryGame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		TextView tView = (TextView)findViewById(R.id.textView1);
		Intent intent = getIntent();
		tView.setText("Your time - "+intent.getStringExtra("Time"));
		//tView.setTextAlignment(tView.TEXT_ALIGNMENT_CENTER);
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
		startActivity(intent);
	}

}
