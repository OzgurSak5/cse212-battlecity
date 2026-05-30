package edu.yeditepe.cse212.battlecity.screen;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.yeditepe.cse212.battlecity.exception.ScoreFileException;
import edu.yeditepe.cse212.battlecity.score.ScoreManager;
import edu.yeditepe.cse212.battlecity.score.ScoreRecord;

public class HighScoreFrame extends JFrame{

	public HighScoreFrame() {
		setTitle("High Scores - Top 10");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ArrayList<ScoreRecord> topScores;
        try{
        	topScores = ScoreManager.loadTopScores(10);
        }
        catch (ScoreFileException e) {
        	JOptionPane.showMessageDialog(this,"Failed to load scores: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        	topScores = new ArrayList<>();
		}
        
        String[] columnNames = {"Rank", "Name", "Score", "Time", "Date"};
        Object[][] data = new Object[topScores.size()][5];
        
        for(int i = 0; i < topScores.size(); i++) {
			ScoreRecord record = topScores.get(i);
			data[i][0] = (i+1);
			data[i][1] = record.getName();
            data[i][2] = record.getScore();
            data[i][3] = record.getTime();
            data[i][4] = record.getDate();
		}
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
	}
	
}
