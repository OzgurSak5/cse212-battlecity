package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.map.Position;

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
	
}
