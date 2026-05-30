package edu.yeditepe.cse212.battlecity.game;

import edu.yeditepe.cse212.battlecity.tank.EnemyTank;

public class EnemyAIThread implements Runnable{
	private EnemyTank enemyTank;
	private GameLoop gameLoop;
	private boolean running;
	private static final long DECISION_INTERVAL_MS = 1000;
	
	
	
	public EnemyAIThread(EnemyTank enemyTank, GameLoop gameLoop) {
		this.enemyTank = enemyTank;
		this.gameLoop = gameLoop;
		running = true;
	}



	@Override
	public void run() {
		while(running && enemyTank.isAlive()) {
			GameStatus status = gameLoop.getStatus();

			if(status == GameStatus.GAME_OVER || status == GameStatus.LEVEL_COMPLETED) {
			    running = false;
			    break;
			}

			if(status != GameStatus.RUNNING) {
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
				continue;
			}
			
			if(gameLoop.isEnemiesFrozen()) {
		    	try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
				continue;
		    }
			
			Direction[] all = Direction.values();
		    int randomIndex = (int)(Math.random() * all.length);
		    Direction randomDir = all[randomIndex];
		    enemyTank.setDirection(randomDir);
			
			if (Math.random() < 0.3) {
			    gameLoop.addBullet(enemyTank.shoot());
			}
			
			try {
				Thread.sleep(DECISION_INTERVAL_MS);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			    running = false;
			}
		}
	}
	
	public void stop() {
		running = false;
	}
	
}
