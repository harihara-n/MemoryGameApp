package com.hariharaknarayanan.MemoryGame.UsefulFunctions;

import java.util.HashMap;

public class ShuffleHelper {
	
	public static HashMap<Integer, Integer> assignToHashMap(int numCards, Integer[] flagInt)
	{
		Integer[] cardsArr = new Integer[numCards];
		for(int i=1; i<=numCards; i++)
		{
			cardsArr[i-1] = i;
		}
		
		cardsArr = Shuffle(cardsArr);
		flagInt = Shuffle(flagInt);
		
		HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		
		for(int i=0, j=0; i<cardsArr.length; i++)
		{
			returnMap.put(cardsArr[i], flagInt[j]);
			
			if(i%2 == 1)
			{
				j++;
			}
		}
		
		return returnMap;
	}
	
	private static Integer[] Shuffle(Integer[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			int randomNumber =(int) (Math.random()*(arr.length - i));
			
			int tempValue = arr[randomNumber];
			arr[randomNumber] = arr[arr.length - i -1];
			arr[arr.length - i - 1] = tempValue;
		}
		
		return arr;
	}

}
