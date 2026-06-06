package edu.yeditepe.cse212.battlecity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.map.Position;

public abstract class Tile {
	private Position position;
	private boolean walkable;
	private boolean destructible;
	private boolean	blocksBullet;
	protected boolean destroyed = false;

	public Tile(Position position, boolean walkable, boolean destructible, boolean blocksBullet) {
		this.position = position;
		this.walkable = walkable;
		this.destructible = destructible;
		this.blocksBullet = blocksBullet;
	}
	
	public abstract void hit(int starCount);
	public abstract Color getColor();
	public abstract BufferedImage getSprite();
	
	protected void destroy() {
		this.destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Position getPosition() {
		return position;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public boolean isBlocksBullet() {
		return blocksBullet;
	}	
	
}
