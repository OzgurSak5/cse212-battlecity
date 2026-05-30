package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public class ExtraLifePowerUp extends PowerUp{

	public ExtraLifePowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		playerTank.addLife();
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public String getSymbol() {
		return "L";
	}

}
