package edu.yeditepe.cse212.battlecity.powerup;

import java.util.ArrayList;

import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tile.EmptyTile;
import edu.yeditepe.cse212.battlecity.tile.Tile;

public class PowerUpManager {
	private GameMap gameMap;
	private ArrayList<PowerUp> activePowerUps;
	private int killsSinceLastSpawn;
	
	private static final int KILLS_PER_SPAWN_CHECK = 3;
	private static final double SPAWN_PROBABILITY = 0.9;
	
	public PowerUpManager(GameMap gameMap) {
		this.gameMap = gameMap;
		activePowerUps = new ArrayList<>();
		killsSinceLastSpawn = 0;
	}
	
	public synchronized void onEnemyKilled() {
		killsSinceLastSpawn++;
		if(killsSinceLastSpawn >= KILLS_PER_SPAWN_CHECK) {
			killsSinceLastSpawn = 0;
			
			if(Math.random() < SPAWN_PROBABILITY) {
				trySpawnPowerUp();
			}
		}
	}
	
	public synchronized void removeCollected() {
		ArrayList<PowerUp> toRemove = new ArrayList<>();
		for (PowerUp powerUp : activePowerUps) {
			if(powerUp.isCollected()) {
				toRemove.add(powerUp);
			}
		}
		activePowerUps.removeAll(toRemove);
	}
	
	private void trySpawnPowerUp() {
		Position position = findFreeSpawnPosition();
		if(position == null) {
			return;
		}
		
		PowerUp powerUp = createRandomPowerUp(position);
		activePowerUps.add(powerUp);
	}
	
	private Position findFreeSpawnPosition() {
		for(int attempt = 0; attempt < 10; attempt++) {
			int tileX = (int) (Math.random() * GameConstants.MAP_WIDTH);
			int tileY = (int) (Math.random() * GameConstants.MAP_HEIGHT);

			Tile tile = gameMap.getTile(tileX, tileY);
			if(tile instanceof EmptyTile) {
				int x = tileX * GameConstants.TILE_SIZE;
				int y = tileY * GameConstants.TILE_SIZE;
				return new Position(x, y);
			}
		}
		return null;
	}
	
	private PowerUp createRandomPowerUp(Position pos) {
		int type = (int)(Math.random() * 6);
		switch (type) {
		case 0:
			return new ExtraLifePowerUp(pos);
		case 1:
			return new StarPowerUp(pos);
		case 2:
			return new BombPowerUp(pos);
		case 3:
			return new ClockPowerUp(pos);
		case 4:
			return new ShovelPowerUp(pos);
		case 5:
			return new ShieldPowerUp(pos);
		default:
			return new ExtraLifePowerUp(pos);
		}
	}
	
	public synchronized ArrayList<PowerUp> getActivePowerUps() {
		return new ArrayList<>(activePowerUps);
	}
}
