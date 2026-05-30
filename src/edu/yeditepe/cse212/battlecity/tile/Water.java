package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.map.Position;

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

}
