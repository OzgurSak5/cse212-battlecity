package edu.yeditepe.cse212.battlecity.game;

import java.awt.Color;

import edu.yeditepe.cse212.battlecity.map.Position;

public class Bullet {
	private Position position;
	private Direction direction;
	private int speed;
	private BulletOwner owner;
	private boolean alive;
	
	public Bullet(Position position, Direction direction, BulletOwner owner) {
		this.position = position;
		this.direction = direction;
		this.owner = owner;
		speed = GameConstants.BULLET_SPEED;
		alive = true;
	}
	
	public void move() {
		int newX = position.getX();
		int newY = position.getY();
		
		switch(direction) {
			case UP:
				newY = newY - speed;
				break;
			case DOWN:
				newY = newY + speed;
				break;
			case LEFT:
				newX = newX - speed;
				break;
			case RIGHT:
				newX = newX + speed;
				break;
		}
		
		position.setX(newX);
	    position.setY(newY);
	}
	
	public void kill() {
		alive = false;
	}
	
	public Color getColor() {
		return Color.WHITE;
	}
	
	public Position getPosition() {
		return position;
	}
	public Direction getDirection() {
		return direction;
	}
	public int getSpeed() {
		return speed;
	}
	public BulletOwner getOwner() {
		return owner;
	}
	public boolean isAlive() {
		return alive;
	}
	
	public int getWidth() {
		return GameConstants.BULLET_SIZE;
	}
	
	public int getHeight() {
		return GameConstants.BULLET_SIZE;
	}
}
