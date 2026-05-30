package edu.yeditepe.cse212.battlecity.tank;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;

public class EnemyTank extends Tank{

	public EnemyTank(Position position, Direction direction, int speed) {
		super(position, direction, speed);
	}

	@Override
	public Color getColor() {
		return Color.PINK;
	}

	@Override
	public int getWidth() {
		return GameConstants.TILE_SIZE - 8;
	}

	@Override
	public int getHeight() {
		return GameConstants.TILE_SIZE - 8;
	}

	@Override
	public BulletOwner getBulletOwner() {
		return BulletOwner.ENEMY;
	}
	
}
