package com.hariharaknarayanan.MemoryGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.hariharaknarayanan.MemoryGame.UsefulFunctions.*;

public class GameActivity extends Activity implements OnLayoutChangeListener, Runnable, OnClickListener {

	private String level;
	private int numCols;
	private int numRows;
	private Handler handler;
	boolean isOneCardOpened = false;
	boolean isGameOver = false;
	
	private int numFlags = 30;
	private Integer[] flagInt = new Integer[numFlags];
	
	HashMap<Integer, Integer> cardToImage = new HashMap<Integer, Integer>();
	Integer openCard;
	HashSet<Integer> finishedCards = new HashSet<Integer>();
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Intent intent = getIntent();
		this.level = intent.getStringExtra("Level");		
		View v = (View) findViewById(R.id.tableLayout1);
		v.addOnLayoutChangeListener(this);
		
		handler = new Handler();
		handler.postDelayed(this, 1000);	
					
	}
	
	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
			
			if(left == 0 && right == 0 && top == 0 && bottom == 0)
			{
				return;
			}
			
			for(int i=0; i<numFlags; i++)
			{
				flagInt[i] = getResources().getIdentifier("f"+Integer.toString(i+1), "drawable", "com.hariharaknarayanan.MemoryGame");
			}
			
			if(level == null || level.compareTo((getResources().getStringArray(R.array.level))[0]) == 0)
			{
				numCols = 3;
				numRows = 4;
			}
			else if(level.compareTo((getResources().getStringArray(R.array.level))[1]) == 0)
			{
				numCols = 4;
				numRows = 5;
			}
			else
			{
				numCols = 5;
				numRows = 6;
			}
			
			AssignFlagsToCards();	
			
			TableLayout tLayout = (TableLayout)findViewById(R.id.tableLayout1);
					
			int tRowHeight = tLayout.getHeight()/numRows;
			int tColWidth = tLayout.getWidth()/numCols;
			
			LayoutParams lP = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					
			for(int i=0; i<numRows; i++)
			{
				TableRow tR = new TableRow(this);
				tR.setLayoutParams(lP);			
				for(int j=0; j<numCols; j++)
				{
					ImageView iView = new ImageView(this);
					iView.setLayoutParams(new TableRow.LayoutParams(tRowHeight, tColWidth));
					iView.setImageResource(R.drawable.defaultflag);
					iView.setClickable(true);
					iView.setOnClickListener(this);
					iView.setId(i*numCols+j+1);
					tR.addView(iView);
				}
				tLayout.addView(tR);
			}
			
			View v1 = (View) findViewById(R.id.tableLayout1);
			v1.removeOnLayoutChangeListener(this);
			
								
	}
	
	public void AssignFlagsToCards()
	{
		cardToImage = ShuffleHelper.assignToHashMap(numRows*numCols, flagInt);
	}
	
	
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		ImageView iView = (ImageView) arg0;
		int cardId = arg0.getId();
		
		if(finishedCards.contains(cardId))
		{
			return; //Card already finished.
		}
		
		iView.setImageResource(cardToImage.get(iView.getId()));
		
		if(openCard == null)
		{
			openCard = cardId;
			return;
		}
		
		int alreadyOpenedCard = openCard;
		int imageAlreadyOpenedCard = cardToImage.get(alreadyOpenedCard);
		int imageCurrentOpenedCard = cardToImage.get(cardId);
		
		if(cardId == alreadyOpenedCard) //Clicking again on open card - just return
		{
			return;
		}
		
		ImageView viewAlreadyOpened = (ImageView)findViewById(alreadyOpenedCard);
				
		if(imageAlreadyOpenedCard == imageCurrentOpenedCard)
		{
			viewAlreadyOpened.setImageResource(R.drawable.tick);
			iView.setImageResource(R.drawable.tick);
			finishedCards.add(alreadyOpenedCard);
			finishedCards.add(cardId);
			checkIfGameOver();
		}
		else
		{
			Handler newHandler = new Handler();
			newHandler.post(new RunnableClass(viewAlreadyOpened, iView));
		}
		openCard = null; 
	}
	
	public void checkIfGameOver()
	{
		if (finishedCards.size() == numCols*numRows)
		{
			Intent intent = new Intent(this, ResultsActivity.class);
			intent.putExtra("Time", ((TextView)findViewById(R.id.textView1)).getText());
			intent.putExtra("Level", level);
			intent.putExtra("Finished", true);
			startActivity(intent);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		TextView timeView = (TextView)findViewById(R.id.textView1);
		String time = (String) timeView.getText();
		//String time = "0:01";				
		
		if (TimerUtilities.convertMSToSeconds(time) < R.integer.TimeLimit) 
		{
			time = TimerUtilities.incrementOne(time);
			timeView.setText(time);
			timeView.postInvalidate();
			handler.postDelayed(this, 1000);
        }
		else
		{
			time = TimerUtilities.incrementOne(time);
			timeView.setText(time);      
			Intent intent = new Intent(this, ResultsActivity.class);
			intent.putExtra("Finished", true);
			startActivity(intent);
        }
		
		
	}
	
	public void goBackToStart(View v)
	{
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public class RunnableClass implements Runnable
	{

		public ImageView a;
		public ImageView b;
		
		@Override
		public void run()
		{
			try
			{
				Thread.sleep(600);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			a.setImageResource(R.drawable.defaultflag);
			b.setImageResource(R.drawable.defaultflag);			
		}
		
		public RunnableClass(ImageView a, ImageView b)
		{
			this.a = a;
			this.b = b;
		}	
		
	}
		
}
