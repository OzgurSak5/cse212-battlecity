package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public class StarPowerUp extends PowerUp{

	public StarPowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		playerTank.addStar();
	}

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	@Override
	public String getSymbol() {
		return "*";
	}

}
