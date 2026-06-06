package edu.yeditepe.cse212.battlecity.tank;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.yeditepe.cse212.battlecity.game.Bullet;
import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;

public abstract class Tank {
	private Position position;
	private Direction direction;
	private int speed;
	private boolean alive;
	
	public Tank(Position position, Direction direction, int speed) {
		this.position = position;
		this.direction = direction;
		this.speed = speed;
		alive = true;
	}
	
	public abstract Color getColor();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract BulletOwner getBulletOwner();
    public abstract BufferedImage getSprite();
    
    public Bullet shoot() {
    	int bulletX = 0;
    	int bulletY = 0;
    	int tankW = getWidth();
    	int tankH = getHeight();
    	int bulletW = GameConstants.BULLET_SIZE;
    	int bulletH = GameConstants.BULLET_SIZE;
    	
    	switch(direction) {
			case UP:
				bulletX = position.getX() + tankW/2 - bulletW/2;
	            bulletY = position.getY() - bulletH;
				break;
			case DOWN:
				bulletX = position.getX() + tankW/2 - bulletW/2;
	            bulletY = position.getY() + tankH;
				break;
			case LEFT:
				bulletX = position.getX() - bulletW;
	            bulletY = position.getY() + tankH/2 - bulletH/2;
				break;
			case RIGHT:
				bulletX = position.getX() + tankW;
				bulletY = position.getY() + tankH/2 - bulletH/2;
				break;
    	}
    	
    	Position bulletPosition = new Position(bulletX, bulletY);
    	return new Bullet(bulletPosition,direction,getBulletOwner());
    }
	
	public Position getNextPosition() {
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
		
		return new Position(newX,newY);
	}
	
	public void move() {
		Position next = getNextPosition();
		position.setX(next.getX());
		position.setY(next.getY());
	}
	
	public int getSpeed() {
		return speed;
	}

	public Position getPosition() {
		return position;
	}
	
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		this.alive = false;
	}
}
