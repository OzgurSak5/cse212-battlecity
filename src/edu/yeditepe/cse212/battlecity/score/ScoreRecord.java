package edu.yeditepe.cse212.battlecity.score;

public class ScoreRecord implements Comparable<ScoreRecord>{
	private String name;
	private int score;
	private String time;
	private String date;
	
	public ScoreRecord(String name, int score, String time, String date) {
		this.name = name;
		this.score = score;
		this.time = time;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public String getTime() {
		return time;
	}
	public String getDate() {
		return date;
	}

	@Override
	public int compareTo(ScoreRecord other) {
		return other.score - this.score;
	}
	
	public String toCsvLine() {
	    return name + "," + score + "," + time + "," + date;
	}
	
	public static ScoreRecord fromCsvLine(String line) {
		String[] parts = line.split(",");
		if(parts.length != 4) {
			return null;
		}
		int score;
		try {
			score = Integer.parseInt(parts[1]);
		} 
		catch (NumberFormatException e) {
			return null;
		}
		return new ScoreRecord(parts[0], score, parts[2], parts[3]);
	}
}
