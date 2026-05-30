package edu.yeditepe.cse212.battlecity.exception;

public class ScoreFileException extends Exception{
	public ScoreFileException(String message) {
        super(message);
    }
    public ScoreFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
