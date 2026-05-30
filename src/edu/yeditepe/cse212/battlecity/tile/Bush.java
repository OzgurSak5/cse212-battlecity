package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.map.Position;

public class Bush extends Tile{

	public Bush(Position position) {
		super(position, true, false, false);
	}

	@Override
	public void hit(int starCount) {}

	@Override
	public Color getColor() {
		return new Color(34,139,34);
	}
	
}
