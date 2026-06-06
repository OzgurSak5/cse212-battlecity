package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class SteelWall extends Tile{

	public SteelWall(Position position) {
		super(position, false, true, true);
	}

	@Override
	public void hit(int starCount) {
		if(starCount >= 3) {
			destroy();
		}
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}
	
	@Override
	public BufferedImage getSprite() {
	    return ImageLoader.load("tiles/steel.png");
	}
	
}
