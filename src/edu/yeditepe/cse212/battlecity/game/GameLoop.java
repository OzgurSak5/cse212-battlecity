package edu.yeditepe.cse212.battlecity.game;


import java.util.ArrayList;

import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.powerup.PowerUp;
import edu.yeditepe.cse212.battlecity.powerup.PowerUpManager;
import edu.yeditepe.cse212.battlecity.screen.GameFrame;
import edu.yeditepe.cse212.battlecity.screen.GamePanel;
import edu.yeditepe.cse212.battlecity.screen.SidePanel;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public class GameLoop implements Runnable{
	private GamePanel gamePanel;
	private PlayerTank playerTank;
	private GameMap gameMap;
	private boolean running;
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyTank> enemyTanks;
	private GameStatus gameStatus;
	private SidePanel sidePanel;
	private EnemyManager enemyManager;
	private GameFrame gameFrame;
	private int playerScore;
	private long startTimeMs;
	private PowerUpManager powerUpManager;
	private boolean enemiesFrozen;
	
	private static final int TARGET_FPS = 60;
	private static final int FRAME_TIME_MS = 1000 / TARGET_FPS;
	public GameLoop(GamePanel gamePanel, PlayerTank playerTank, GameMap gameMap,SidePanel sidePanel) {
		this.gamePanel = gamePanel;
		this.playerTank = playerTank;
		this.gameMap = gameMap;
		this.sidePanel = sidePanel;
		running = true;
		bullets = new ArrayList<>();
		enemyTanks = new ArrayList<>();
		gameStatus = GameStatus.RUNNING;
		playerScore = 0;
		startTimeMs = System.currentTimeMillis();
		enemiesFrozen = false;
		powerUpManager = null;
	}
	
	@Override
	public void run() {
		while(running) {
			update();
			gamePanel.repaint();
			sidePanel.repaint();
			
			if(getStatus() == GameStatus.GAME_OVER && gameFrame != null) {
			    running = false;
			    final GameFrame f = gameFrame;
			    f.onGameOver();
			    break;
			}
			
			if(getStatus() == GameStatus.LEVEL_COMPLETED && gameFrame != null) {
				playerScore = playerScore + 500;
			    try {
			        Thread.sleep(3000);
			    }
			    catch (InterruptedException e) {
			        Thread.currentThread().interrupt();
			        running = false;
			        break;
			    }
			    running = false;
			    gameFrame.advanceToNextLevel();
			    break;
			}
			
			
			try {
				int sleepMs;
	            if (getStatus() == GameStatus.PAUSED) {
	                sleepMs = 100;    // saniyede 10 frame yeter
	            } else {
	                sleepMs = FRAME_TIME_MS;   // normal 60 FPS
	            }
	            Thread.sleep(sleepMs);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			    running = false;
			}
		}
	}
	
	public String getElapsedTimeString() {
	    long elapsedMs = System.currentTimeMillis() - startTimeMs;
	    
	    long totalSeconds = elapsedMs / 1000;
	    long minutes = totalSeconds / 60;
	    long seconds = totalSeconds % 60;
	    
	    
	    return String.format("%02d:%02d", minutes, seconds);
	}
	
	private void checkGameStatus() {
		if(!playerTank.isAlive()) {
			gameStatus = GameStatus.GAME_OVER;
			return;
		}
		
		if(gameMap.isBaseDestroyed()) {
	        gameStatus = GameStatus.GAME_OVER;
	        return;
	    }
		
		if(enemyManager != null && enemyManager.isLevelCleared()) {
			gameStatus = GameStatus.LEVEL_COMPLETED;
		}
	}

	private void update() {
		if (gameStatus != GameStatus.RUNNING) {
	        return;
	    }
		
		Direction requestedDir = gamePanel.getRequestedDirection();
		
		if(requestedDir != null) {
			playerTank.setDirection(requestedDir);
			if(CollisionManager.canTankMove(playerTank,gameMap)) {
				playerTank.move();
			}
		}
		
		synchronized (this) {
			for (EnemyTank enemy : enemyTanks) {
			    if (enemy.isAlive() && !enemiesFrozen) {
			        if (CollisionManager.canTankMove(enemy, gameMap)) {
			            enemy.move();
			        }
			    }
			}
			
			for(Bullet bullet : bullets) {
				bullet.move();
			}
			
			for (Bullet bullet : bullets) {
				if (bullet.isAlive()) {
					int starCount = (bullet.getOwner() == BulletOwner.PLAYER) ? playerTank.getStarCount() : 0;
					CollisionManager.bulletCollisions(bullet, gameMap, starCount);
				}
			}
			
			for (Bullet bullet : bullets) {
			    if (bullet.isAlive()) {
			        CollisionManager.bulletTankCollisions(bullet, playerTank, enemyTanks);
			    }
			}
			
			for (Bullet bullet : bullets) {
		        Position p = bullet.getPosition();
		        if(p.getX()<0 || p.getX()>=GameConstants.BOARD_WIDTH || p.getY()<0 || p.getY()>=GameConstants.BOARD_HEIGHT) {
		            bullet.kill();
		        }
		    }
			
			ArrayList<Bullet> toRemove = new ArrayList<>();
			for(Bullet bullet : bullets) {
				if(!bullet.isAlive()) {
		            toRemove.add(bullet);
		        }
			}
			bullets.removeAll(toRemove);
			
			ArrayList<EnemyTank> deadEnemies = new ArrayList<>();
			for(EnemyTank enemy : enemyTanks) {
				if(!enemy.isAlive()) {
					deadEnemies.add(enemy);
				}
			}
			for (EnemyTank enemyTank : deadEnemies) {
				if(enemyManager != null) {
			        enemyManager.onEnemyKilled();
			    }
				if(powerUpManager != null) {
			        powerUpManager.onEnemyKilled();
			    }
				playerScore = playerScore + 100;
			}
			enemyTanks.removeAll(deadEnemies);
			
			
			if (powerUpManager != null) {
				for(PowerUp powerUp : powerUpManager.getActivePowerUps()) {
					if(powerUp.isCollected()) {
						continue;
					}

					if(CollisionManager.rectanglesCollide(playerTank.getPosition().getX(),
							playerTank.getPosition().getY(), playerTank.getWidth(), playerTank.getHeight(),
							powerUp.getPosition().getX(), powerUp.getPosition().getY(), powerUp.getWidth(),
							powerUp.getHeight())) {

						powerUp.apply(playerTank, this);
						powerUp.collect();
					}
				}

				powerUpManager.removeCollected();
			}
		}
		
		checkGameStatus();
	}
	
	public void stop() {
	    running = false;
	}
	
	public synchronized void addBullet(Bullet bullet) {
	    bullets.add(bullet);
	}
	
	public synchronized ArrayList<Bullet> getBullets() {
	    return new ArrayList<>(bullets);
	}
	
	
	public synchronized void addEnemyTank(EnemyTank enemyTank) {
	    enemyTanks.add(enemyTank);
	}
	
	public synchronized ArrayList<EnemyTank> getEnemyTanks(){
		return new ArrayList<>(enemyTanks);
	}
	
	public synchronized GameStatus getStatus() {
	    return gameStatus;
	}
	
	public synchronized void setStatus(GameStatus newStatus) {
		this.gameStatus = newStatus;
	}
	
	public synchronized int getPlayerScore() {
	    return playerScore;
	}

	public void setEnemyManager(EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
	}
	
	public void setPowerUpManager(PowerUpManager powerUpManager) {
	    this.powerUpManager = powerUpManager;
	}

	public synchronized boolean isEnemiesFrozen() {
	    return enemiesFrozen;
	}

	public synchronized void setEnemiesFrozen(boolean frozen) {
	    this.enemiesFrozen = frozen;
	}

	public GameMap getGameMap() {
	    return gameMap;
	}

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public PowerUpManager getPowerUpManager() {
		return powerUpManager;
	}
	
	
}
