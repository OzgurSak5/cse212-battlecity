package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class BrickWall extends Tile{

	public BrickWall(Position position) {
		super(position, false, true, true);
	}

	@Override
	public void hit(int starCount) {
		destroy();
	}

	@Override
	public Color getColor() {
		return new Color(178,34,34);
	}

	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("tiles/brick.png");
	}
	
}
