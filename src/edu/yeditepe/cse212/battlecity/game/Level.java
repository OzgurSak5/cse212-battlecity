package edu.yeditepe.cse212.battlecity.game;

public class Level {
	private int levelNumber;
	private String mapFilePath;
	private Difficulty difficulty;
	
	public Level(int levelNumber, String mapFilePath, Difficulty difficulty) {
		this.levelNumber = levelNumber;
		this.mapFilePath = mapFilePath;
		this.difficulty = difficulty;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public String getMapFilePath() {
		return mapFilePath;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	
}
