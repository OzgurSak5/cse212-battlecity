package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

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

	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("items/powerup_tank_1up.png");
	}
}
