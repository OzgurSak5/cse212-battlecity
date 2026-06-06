package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class ClockPowerUp extends PowerUp{

	public ClockPowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		gameLoop.setEnemiesFrozen(true);
		
		final GameLoop loop = gameLoop;
	    
	    Runnable expireAction = new Runnable() {
	        @Override
	        public void run() {
	            loop.setEnemiesFrozen(false);
	        }
	    };
	    
	    PowerUpEffectThread effect = new PowerUpEffectThread(10000, expireAction);
	    Thread t = new Thread(effect);
	    t.start();
	}

	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	@Override
	public String getSymbol() {
		return "C";
	}
	
	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("items/powerup_timer.png");
	}

}
