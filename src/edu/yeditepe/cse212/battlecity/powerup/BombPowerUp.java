package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public class BombPowerUp extends PowerUp{

	public BombPowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		for (EnemyTank enemy : gameLoop.getEnemyTanks()) {
			enemy.kill();
		}
	}

	@Override
	public Color getColor() {
		return Color.ORANGE;
	}

	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return "B";
	}

}
