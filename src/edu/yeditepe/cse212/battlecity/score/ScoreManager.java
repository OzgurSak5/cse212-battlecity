package edu.yeditepe.cse212.battlecity.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.yeditepe.cse212.battlecity.exception.ScoreFileException;

public class ScoreManager {
	private static final String DEFAULT_FILE_PATH = "scores.csv";
	private static final String CSV_HEADER = "NAME,SCORE,TIME,DATE";
	
	public static void saveScore(ScoreRecord record) throws ScoreFileException {
		File file = new File(DEFAULT_FILE_PATH);
		boolean fileExists = file.exists();
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
	        
	        if(!fileExists) {
	            writer.write(CSV_HEADER);
	            writer.newLine();
	        }
	        
	        writer.write(record.toCsvLine());
	        writer.newLine();
	    }
	    catch (IOException e) {
	        throw new ScoreFileException("Could not save score: " + e.getMessage(), e);
	    }
	}

	public static ArrayList<ScoreRecord> loadScores() throws ScoreFileException {
		ArrayList<ScoreRecord> scores = new ArrayList<>();
	    File file = new File(DEFAULT_FILE_PATH);
	    	    
	    if(!file.exists()) {
	    	return scores;
	    }
	    
	    try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        boolean isFirst = true;
	        
	        while((line = reader.readLine()) != null) {
	            if(isFirst) {
	                isFirst = false;
	                continue;
	            }
	            
	            ScoreRecord record = ScoreRecord.fromCsvLine(line);
	            if(record != null) {
	                scores.add(record);
	            }
	        }
	    }
	    catch (IOException e) {
	        throw new ScoreFileException("Could not read scores: " + e.getMessage(), e);
	    }
	    
	    return scores;
	}

	public static ArrayList<ScoreRecord> loadTopScores(int howMany) throws ScoreFileException {
		ArrayList<ScoreRecord> all = loadScores();
	    Collections.sort(all);
	    ArrayList<ScoreRecord> top = new ArrayList<>();
	    
	    int limit = Math.min(howMany, all.size());
	    
	    for(int i = 0; i < limit; i++) {
	        top.add(all.get(i));
	    }
	    
	    return top;
	}
}
