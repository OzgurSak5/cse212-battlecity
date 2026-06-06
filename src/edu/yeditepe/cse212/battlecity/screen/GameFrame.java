package edu.yeditepe.cse212.battlecity.screen;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.yeditepe.cse212.battlecity.exception.MapLoadException;
import edu.yeditepe.cse212.battlecity.exception.ScoreFileException;
import edu.yeditepe.cse212.battlecity.game.Difficulty;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.EnemyManager;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.game.GameStatus;
import edu.yeditepe.cse212.battlecity.game.Level;
import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.MapManager;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.menu.GameMenuBar;
import edu.yeditepe.cse212.battlecity.powerup.PowerUpManager;
import edu.yeditepe.cse212.battlecity.score.ScoreManager;
import edu.yeditepe.cse212.battlecity.score.ScoreRecord;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.tile.BaseTile;
import edu.yeditepe.cse212.battlecity.tile.BrickWall;
import edu.yeditepe.cse212.battlecity.tile.Bush;
import edu.yeditepe.cse212.battlecity.tile.SteelWall;
import edu.yeditepe.cse212.battlecity.tile.Water;

public class GameFrame extends JFrame{
	private GamePanel gamePanel;
	private GameLoop gameLoop;
	private Thread gameThread;
	private GameMenuBar menuBar;
	private SidePanel sidePanel;
	private EnemyManager enemyManager;
	private Thread enemyManagerThread;
	private Level[] levels;
	private int currentLevelIndex;
	private Level currentLevel;
	
	
	public GameFrame() {
		setTitle("Battle City");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		levels = new Level[3];
		levels[0] = new Level(1, "levels/level1.txt", Difficulty.EASY);
		levels[1] = new Level(2, "levels/level2.txt", Difficulty.MEDIUM);
		levels[2] = new Level(3, "levels/level3.txt", Difficulty.HARD);
		currentLevelIndex = 0;
		menuBar = new GameMenuBar(this);
        setJMenuBar(menuBar);
        initializeGame();
	}
	
	public GameFrame(String customMapPath) {
	    setTitle("Battle City - Custom Map");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    levels = new Level[1];
	    levels[0] = new Level(1, customMapPath, Difficulty.MEDIUM);
	    currentLevelIndex = 0;
	    menuBar = new GameMenuBar(this);
	    setJMenuBar(menuBar);
	    initializeGame();
	}
	
	private void initializeGame() {
		GameMap gameMap;
		try {
			currentLevel = levels[currentLevelIndex];
			gameMap = MapManager.loadMap(currentLevel.getMapFilePath());
		}
		catch(MapLoadException e) {
		    JOptionPane.showMessageDialog(this, 
		        "Failed to load map: " + e.getMessage(), 
		        "Map Load Error", 
		        JOptionPane.ERROR_MESSAGE);
		    gameMap = new GameMap(GameConstants.MAP_WIDTH, GameConstants.MAP_HEIGHT);
		}
		int startX = 4 *GameConstants.TILE_SIZE;
		int startY = 12 *GameConstants.TILE_SIZE;
		Position startposition = new Position(startX, startY);
		PlayerTank playerTank = new PlayerTank(startposition, Direction.UP);
		
		if (gamePanel != null) {
            remove(gamePanel);
        }
		
        if (sidePanel != null) {
            remove(sidePanel);
        }
		
		gamePanel = new GamePanel(gameMap,playerTank,this);
		sidePanel = new SidePanel(playerTank);
		
		add(gamePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);
		
		pack();//pencereyi otomatik istenilen büyüklüğe ayarlayan oto metot
		setLocationRelativeTo(null);
		gamePanel.requestFocusInWindow();
		
		gameLoop = new GameLoop(gamePanel, playerTank, gameMap,sidePanel);
		gamePanel.setGameLoop(gameLoop);
		sidePanel.setGameLoop(gameLoop);
		
		enemyManager = new EnemyManager(gameLoop, gameMap, currentLevel.getDifficulty());
		gameLoop.setEnemyManager(enemyManager);
		gameLoop.setGameFrame(this);
		enemyManagerThread = new Thread(enemyManager);
		enemyManagerThread.start();
		PowerUpManager powerUpManager = new PowerUpManager(gameMap);
		gameLoop.setPowerUpManager(powerUpManager);
		
		gameThread = new Thread(gameLoop);
		gameThread.start();
		
		if (menuBar != null) {
            menuBar.setPauseLabel("Pause");
        }
		
        revalidate();
        repaint();
	}
	
	public void startNewGame() {
		currentLevelIndex = 0;
		if(gameLoop != null) {
			gameLoop.stop();
		}
		
		if(enemyManager != null) {
			enemyManager.stop();
		}

		try {
			Thread.sleep(50);
		} 
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		initializeGame();
	}
	
	public void advanceToNextLevel() {
		currentLevelIndex++;
		
		if(currentLevelIndex >= levels.length) {
			JOptionPane.showMessageDialog(this, "Congratulations! You completed all levels!", "Victory", 
		            JOptionPane.INFORMATION_MESSAGE);
			currentLevelIndex = 0;
		}
		
		if(gameLoop != null) {
			gameLoop.stop();
		}
		
		if(enemyManager != null) {
			enemyManager.stop();
		}
		
		try {
	        Thread.sleep(50);
	    }
	    catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	    
	    initializeGame();
	}
	
	public void returnToTitle() {
	    if(gameLoop != null) {
	        gameLoop.stop();
	    }
	    if(enemyManager != null) {
	        enemyManager.stop();
	    }
	    try {
	        Thread.sleep(50);
	    }
	    catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	    dispose();
	    TitleScreenFrame title = new TitleScreenFrame();
	    title.setVisible(true);
	}
	
	public void togglePause() {
		if (gameLoop == null) {
			return;
		}

		GameStatus current = gameLoop.getStatus();

		if(current == GameStatus.RUNNING) {
			gameLoop.setStatus(GameStatus.PAUSED);
			menuBar.setPauseLabel("Resume");
		}
		else if(current == GameStatus.PAUSED) {
			gameLoop.setStatus(GameStatus.RUNNING);
			menuBar.setPauseLabel("Pause");
		}
		
		gamePanel.clearKeys();
	    gamePanel.requestFocusInWindow();
	}
	
	public void onMenuOpened() {
	    if (gamePanel != null) {
	        gamePanel.clearKeys();
	    }
	}
	
	public void onGameOver() {
	    String name = JOptionPane.showInputDialog(this, 
	        "Game Over!\nYour score: " + gameLoop.getPlayerScore() + 
	        "\n\nEnter your name:",
	        "Save Score",
	        JOptionPane.PLAIN_MESSAGE);
	    
	    if(name == null || name.trim().isEmpty()) {
	        return;
	    }
	    
	    name = name.trim().replace(",", "_");
	    
	    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
	    String time = gameLoop.getElapsedTimeString();
	    int score = gameLoop.getPlayerScore();
	    
	    ScoreRecord record = new ScoreRecord(name, score, time, date);
	    
	    try {
	        ScoreManager.saveScore(record);
	        JOptionPane.showMessageDialog(this, 
	            "Score saved successfully!", 
	            "Saved", 
	            JOptionPane.INFORMATION_MESSAGE);
	    }
	    catch(ScoreFileException e) {
	        JOptionPane.showMessageDialog(this,
	            "Failed to save score: " + e.getMessage(),
	            "Save Error",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void setStartingDifficulty(Difficulty selected) {
		for(int i = 0; i < levels.length; i++) {
	        Level original = levels[i];
	        levels[i] = new Level(original.getLevelNumber(), original.getMapFilePath(), selected);
	    }
	    startNewGame();
	}
}
