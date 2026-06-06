package edu.yeditepe.cse212.battlecity.tank;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class EnemyTank extends Tank{

	public EnemyTank(Position position, Direction direction, int speed) {
		super(position, direction, speed);
	}
	
	
	@Override
	public BufferedImage getSprite() {
	    String dir = directionToString(getDirection());
	    return ImageLoader.load("tanks/enemy_white/enemy_white_shape0_" + dir + "_0.png");
	}

	private String directionToString(Direction d) {
	    switch(d) {
	        case UP:    return "up";
	        case DOWN:  return "down";
	        case LEFT:  return "left";
	        case RIGHT: return "right";
	        default:    return "down";   // enemy default DOWN
	    }
	}

	@Override
	public Color getColor() {
		return Color.PINK;
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
		return BulletOwner.ENEMY;
	}
	
}
