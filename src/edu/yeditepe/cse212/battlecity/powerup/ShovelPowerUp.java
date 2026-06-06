package edu.yeditepe.cse212.battlecity.powerup;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.tile.Tile;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class ShovelPowerUp extends PowerUp{

	public ShovelPowerUp(Position position) {
		super(position);
	}

	@Override
	public void apply(PlayerTank playerTank, GameLoop gameLoop) {
		final GameMap gameMap = gameLoop.getGameMap();
	    final Tile[] snapshot = gameMap.shieldBaseWithSteel();
	    
	    Runnable expireAction = new Runnable() {
	        @Override
	        public void run() {
	            gameMap.unshieldBase(snapshot);
	        }
	    };
	    
	    PowerUpEffectThread effect = new PowerUpEffectThread(20000, expireAction);
	    Thread t = new Thread(effect);
	    t.start();
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}

	@Override
	public String getSymbol() {
		return "V";
	}
	
	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("items/powerup_shovel.png");
	}
	
}
