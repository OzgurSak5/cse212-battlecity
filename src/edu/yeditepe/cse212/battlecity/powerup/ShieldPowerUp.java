package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class ShieldPowerUp extends PowerUp{

	public ShieldPowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		playerTank.activateShield();
		
		final PlayerTank tank = playerTank;

		Runnable expireAction = new Runnable() {
			@Override
			public void run() {
				tank.deactivateShield();
			}
		};

		PowerUpEffectThread effect = new PowerUpEffectThread(10000, expireAction);
		Thread t = new Thread(effect);
		t.start();
	}

	@Override
	public Color getColor() {
		return Color.CYAN;
	}

	@Override
	public String getSymbol() {
		return "S";
	}

	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("items/powerup_helmet.png");
	}
}
