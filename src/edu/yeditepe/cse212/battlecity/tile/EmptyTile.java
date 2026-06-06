package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;

public class EmptyTile extends Tile{

	public EmptyTile(Position position) {
		super(position,true,false,false);
	}

	@Override
	public void hit(int starCount) {		
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}

	@Override
	public BufferedImage getSprite() {
		return null;
	}
	
}
