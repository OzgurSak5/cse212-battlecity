package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public abstract class PowerUp {
	protected Position position;
	protected boolean collected;
	protected int width;
	protected int height;
	public PowerUp(Position position) {
		super();
		this.position = position;
		collected = false;
		width = GameConstants.TILE_SIZE;
		height = GameConstants.TILE_SIZE;
	}
	
	public abstract void apply(PlayerTank playerTank, GameLoop gameLoop);
	public abstract Color getColor();
	
	public void collect() {
	    collected = true;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isCollected() {
		return collected;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public abstract String getSymbol();
	
	
}
