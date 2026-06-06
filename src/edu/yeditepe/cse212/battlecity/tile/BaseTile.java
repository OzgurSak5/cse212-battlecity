package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class BaseTile extends Tile{

	public BaseTile(Position position) {
		super(position, false, true, true);
	}

	@Override
	public void hit(int starCount) {
		destroy();
		// if the BaseTile destroys the game ends.
	}

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	@Override
	public BufferedImage getSprite() {
		if(isDestroyed()) {
	        return ImageLoader.load("tiles/base_destroyed.png");
	    }
	    return ImageLoader.load("tiles/base_eagle.png");
	}
	
}
