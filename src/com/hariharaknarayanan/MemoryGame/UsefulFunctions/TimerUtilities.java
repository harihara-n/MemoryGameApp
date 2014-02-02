package com.hariharaknarayanan.MemoryGame.UsefulFunctions;

public class TimerUtilities {
	
	public static int convertMSToSeconds(String s)
	{
		String[] strArray = s.split(":");
		String mins = strArray[0];
		String secs = strArray[1];
		
		int minValue = (Integer.parseInt(mins))*60;
		int secsValue = Integer.parseInt(secs);
		
		return minValue + secsValue;
	}
	
	public static String convertSecondsToMS(int seconds)
	{
		int secs = seconds%60;
		int mins = seconds/60;
		
		String secsStr = String.valueOf(secs);
		String minsStr = String.valueOf(mins);
		
		return (minsStr.concat(":")).concat(secsStr);
		
	}
	
	public static String incrementOne (String s)
	{
		String[] strArray = s.split(":");
		String mins = strArray[0];
		String secs = strArray[1];
		
		int minValue = (Integer.parseInt(mins));
		int secsValue = Integer.parseInt(secs);
		
		secsValue++;
		
		if(secsValue == 60)
		{
			secsValue = 0;
			minValue++;
		}
		
		secs = String.valueOf(secsValue);
		mins = String.valueOf(minValue);
		
		if(secsValue < 10)
		{
			secs = "0".concat(secs);
		}
		
		return mins.concat(":".concat(secs));
	}

}
