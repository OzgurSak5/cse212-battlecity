package edu.yeditepe.cse212.battlecity.tank;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.Bullet;
import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class PlayerTank extends Tank{
	private int lives;
	private int starCount;
	private boolean shieldActive;
	
	public PlayerTank(Position position, Direction direction) {
		super(position, direction, 3);
		this.lives = GameConstants.INITIAL_LIVES;
		this.starCount = 0;
		this.shieldActive = false;
	}
	
	@Override
	public BufferedImage getSprite() {
	    String dir = directionToString(getDirection());
	    return ImageLoader.load("tanks/player1_gold/player1_gold_shape0_" + dir + "_0.png");
	}

	private String directionToString(Direction d) {
	    switch(d) {
	        case UP:    return "up";
	        case DOWN:  return "down";
	        case LEFT:  return "left";
	        case RIGHT: return "right";
	        default:    return "up";
	    }
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
		return GameConstants.TANK_SIZE;
	}
	@Override
	public int getHeight() {
		return GameConstants.TANK_SIZE;
	}

	@Override
	public BulletOwner getBulletOwner() {
		return BulletOwner.PLAYER;
	}
	
	@Override
	public Bullet shoot() {
	    Bullet bullet = super.shoot();
	    if(starCount >= 1) {
	        bullet.setSpeed(GameConstants.BULLET_SPEED_FAST);
	    }
	    return bullet;
	}
}
