package edu.yeditepe.cse212.battlecity.exception;

public class MapLoadException extends Exception{
	public MapLoadException(String message) {
        super(message);
    }
	public MapLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
