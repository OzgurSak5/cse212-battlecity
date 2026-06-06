package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class Water extends Tile{

	public Water(Position position) {
		super(position, false, false, false);
	}

	@Override
	public void hit(int starCount) {}

	@Override
	public Color getColor() {
		return new Color(0,100,200);
	}

	@Override
	public BufferedImage getSprite() {
		return ImageLoader.load("tiles/water_0.png");
	}

}
