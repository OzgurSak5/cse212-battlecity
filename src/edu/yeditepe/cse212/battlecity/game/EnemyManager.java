package edu.yeditepe.cse212.battlecity.game;

import java.util.ArrayList;

import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;

public class EnemyManager implements Runnable{
	private GameLoop gameLoop;
	private GameMap gameMap;
	private Difficulty difficulty;
	private int enemiesRemaining;
	private int enemiesKilled;
	private boolean running;
	private ArrayList<Thread> aiThreads;
	private ArrayList<EnemyAIThread> aiTasks;
	private Position[] spawnPoints;
	
	private static final long SPAWN_INTERVAL_MS = 1500;
	private static final int TOTAL_ENEMIES = GameConstants.ENEMIES_PER_LEVEL;
	private static final int MAX_ON_SCREEN = GameConstants.MAX_ENEMIES_ON_SCREEN;
	
	public EnemyManager(GameLoop gameLoop, GameMap gameMap, Difficulty difficulty) {
		this.gameLoop = gameLoop;
		this.gameMap = gameMap;
		this.difficulty = difficulty;
		enemiesRemaining = TOTAL_ENEMIES;
		enemiesKilled = 0;
		running = true;
		aiThreads = new ArrayList<>();
		aiTasks = new ArrayList<>();
		spawnPoints = new Position[3];
		spawnPoints[0] = new Position(0, 0);
		spawnPoints[1] = new Position((GameConstants.MAP_WIDTH/2) * GameConstants.TILE_SIZE, 0);
		spawnPoints[2] = new Position((GameConstants.MAP_WIDTH-1) * GameConstants.TILE_SIZE, 0);
	}

	
	@Override
	public void run() {
		while(running) {
			try {
				GameStatus status = gameLoop.getStatus();
				
				if(status == GameStatus.GAME_OVER || status == GameStatus.LEVEL_COMPLETED) {
					running = false;
					break;
				}
				
				if(status != GameStatus.RUNNING) {
					Thread.sleep(100);
					continue;
				}
				
				if(enemiesRemaining == 0) {
					Thread.sleep(SPAWN_INTERVAL_MS);
					continue;
				}
				
				if(countActiveEnemies() < MAX_ON_SCREEN) {
				    Position spawnPos = pickFreeSpawnPoint();
				    
				    if(spawnPos != null) {
				        EnemyTank enemy = new EnemyTank(spawnPos, Direction.DOWN, getEnemySpeedForDifficulty());
				        gameLoop.addEnemyTank(enemy);
				        
				        EnemyAIThread ai = new EnemyAIThread(enemy, gameLoop);
				        Thread t = new Thread(ai);
				        aiTasks.add(ai);
				        aiThreads.add(t);
				        t.start();
				        
				        enemiesRemaining--;
				    }
				}
				Thread.sleep(SPAWN_INTERVAL_MS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				running = false;
			}
			
			
		}
	}
	
	public void stop() {
		running = false;
		
		for(EnemyAIThread ai : aiTasks) {
	        ai.stop();
	    }
	}
	
	private int countActiveEnemies() {
		int count = 0;
		for (EnemyTank enemy : gameLoop.getEnemyTanks()) {
			if(enemy.isAlive()) {
				count++;
			}
		}
		return count;
	}
	
	private boolean isSpawnPointFree(Position spawnPoint) {
		int spX = spawnPoint.getX();
		int spY = spawnPoint.getY();
		int size = GameConstants.TILE_SIZE;
		
		
		for (EnemyTank enemy : gameLoop.getEnemyTanks()) {
			if(!enemy.isAlive()) {
				continue;
			}
			int ex = enemy.getPosition().getX();
	        int ey = enemy.getPosition().getY();
	        int ew = enemy.getWidth();
	        int eh = enemy.getHeight();
	        
	        if (CollisionManager.rectanglesCollide(spX, spY, size, size, ex, ey, ew, eh)) {
	            return false;
	        }
		}
		return true;
	}
	
	private Position pickFreeSpawnPoint() {
		ArrayList<Position> free = new ArrayList<>();
		
		for (Position spawn : spawnPoints) {
	        if (isSpawnPointFree(spawn)) {
	            free.add(spawn);
	        }
	    }
	    
	    if (free.isEmpty()) {
	        return null;
	    }
	    
	    int idx = (int)(Math.random() * free.size());
	    return free.get(idx);
	}
	
	private int getEnemySpeedForDifficulty() {
		switch (difficulty) {
		case EASY:
			return 1;
		case MEDIUM:
			return 2;
		case HARD:
			return 3;
		default:
			return 1;
		}
	}
	
	public synchronized int getEnemiesRemaining() {
		return enemiesRemaining;
	}
	
	public synchronized int getEnemiesKilled() {
		return enemiesKilled;
	}
	
	public int getTotalEnemiesPerLevel() {
		return TOTAL_ENEMIES;
	}
	
	public synchronized boolean isLevelCleared() {
		return enemiesKilled >= TOTAL_ENEMIES;
	}
	
	public synchronized void onEnemyKilled() {
		enemiesKilled++;
	}
	
	
}
