package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.map.Position;

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
	
}
