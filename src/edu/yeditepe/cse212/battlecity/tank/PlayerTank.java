package edu.yeditepe.cse212.battlecity.tank;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;

public class PlayerTank extends Tank{
	private int lives;
	private int starCount;
	private boolean shieldActive;
	
	public PlayerTank(Position position, Direction direction) {
		super(position, direction, 2);
		this.lives = GameConstants.INITIAL_LIVES;
		this.starCount = 0;
		this.shieldActive = false;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void loseLife() {
		if (lives > 0) {
			lives--;
		}
		
		if(lives == 0) {
			kill();
		}
	}
	
	public void addLife() {
		lives++;
	}
	
	public int getStarCount() {
		return starCount;
	}
	
	public void addStar() {
		if(starCount < 3) {
			starCount++;
		}
	}

	public boolean isShieldActive() {
		return shieldActive;
	}

	public void activateShield() {
		this.shieldActive = true;
	}

	public void deactivateShield() {
		this.shieldActive = false;
	}

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}
	@Override
	public int getWidth() {
		return GameConstants.TILE_SIZE;
	}
	@Override
	public int getHeight() {
		return GameConstants.TILE_SIZE;
	}

	@Override
	public BulletOwner getBulletOwner() {
		return BulletOwner.PLAYER;
	}
	
}
