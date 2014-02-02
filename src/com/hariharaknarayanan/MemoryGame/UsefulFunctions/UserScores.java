package com.hariharaknarayanan.MemoryGame.UsefulFunctions;

public class UserScores implements Comparable {
	
	private String userScore;
	
	public UserScores(String userScore)
	{
		this.userScore = userScore;
	}
	
	public String getUserScore()
	{
		return this.userScore;
	}

	@Override
	public int compareTo(Object arg0) {
		
		UserScores uScores = (UserScores)arg0;
		return this.userScore.compareTo(uScores.userScore);				
	}	

}
