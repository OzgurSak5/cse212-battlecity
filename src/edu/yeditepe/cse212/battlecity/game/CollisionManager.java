package edu.yeditepe.cse212.battlecity.game;

import java.util.ArrayList;

import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.tank.Tank;
import edu.yeditepe.cse212.battlecity.tile.BaseTile;
import edu.yeditepe.cse212.battlecity.tile.EmptyTile;
import edu.yeditepe.cse212.battlecity.tile.Tile;

public class CollisionManager {
	public static boolean canTankMove(Tank tank,GameMap gameMap) {
		Position nextPosition = tank.getNextPosition();
		int x = nextPosition.getX();
		int y = nextPosition.getY();
		int width = tank.getWidth();
		int height = tank.getHeight();
		
		if(!isInsideBoard(x,y,width,height)) {
			return false;
		}
		
		if(!areAllCornersWalkable(x,y,width,height,gameMap)) {
            return false;
        }
		return true;
	}

	private static boolean areAllCornersWalkable(int x, int y, int width, int height, GameMap gameMap) {
		if(!isPixelWalkable(x,y,gameMap)) {
			return false;
		}
		if(!isPixelWalkable(x+width-1,y,gameMap)) {
			return false;
		}
		if(!isPixelWalkable(x,y+height-1,gameMap)) {
			return false;
		}
		if(!isPixelWalkable(x+width-1,y+height-1,gameMap)) {
			return false;
		}
		return true;
	}

	private static boolean isPixelWalkable(int pixelX,int pixelY,GameMap gameMap) {
		int tileX = pixelX / GameConstants.TILE_SIZE;
		int tileY = pixelY / GameConstants.TILE_SIZE;
		Tile tile = gameMap.getTile(tileX, tileY);
		
		if(tile == null) {
			return false;
		}
		return tile.isWalkable();
	}

	private static boolean isInsideBoard(int x, int y, int width, int height) {
		if(x >= 0 && y >= 0 && x + width <= GameConstants.BOARD_WIDTH && y + height <= GameConstants.BOARD_HEIGHT) {
			return true;
		}
		return false;
	}
	
	public static void bulletTankCollisions(Bullet bullet, PlayerTank playerTank, ArrayList<EnemyTank> enemyTanks) {
		if (!bullet.isAlive()) {
		    return;
		}
		
		Position bp = bullet.getPosition();
		int bx = bp.getX();
		int by = bp.getY();
		int bw = bullet.getWidth();
		int bh = bullet.getHeight();
		
		if (bullet.getOwner() == BulletOwner.PLAYER) {
			for (EnemyTank enemy : enemyTanks) {
			    if (!enemy.isAlive()) {
			        continue;
			    }
			    
			    Position ep = enemy.getPosition();
			    int ex = ep.getX();
			    int ey = ep.getY();
			    int ew = enemy.getWidth();
			    int eh = enemy.getHeight();
			    
			    if (rectanglesCollide(bx, by, bw, bh, ex, ey, ew, eh)) {
			        enemy.kill();
			        bullet.kill();
			        break;
			    }
			}
		}
		else if (bullet.getOwner() == BulletOwner.ENEMY) {
		    if (!playerTank.isAlive()) {
		        return;
		    }
		    
		    Position pp = playerTank.getPosition();
		    int px = pp.getX();
		    int py = pp.getY();
		    int pw = playerTank.getWidth();
		    int ph = playerTank.getHeight();
		    
		    if (rectanglesCollide(bx, by, bw, bh, px, py, pw, ph)) {
		    	if(!playerTank.isShieldActive()) {
		            playerTank.loseLife();
		        }
		        bullet.kill();
		    }
		}
	}
	
	public static boolean rectanglesCollide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
	}

	public static void bulletCollisions(Bullet bullet,GameMap gameMap,int starCount) {
		Position p = bullet.getPosition();
		int x = p.getX();
		int y = p.getY();
		int w = bullet.getWidth();
		int h = bullet.getHeight();
		
		
		checkBulletCHit(x,y,bullet,gameMap,starCount);
		checkBulletCHit(x+w-1,y,bullet,gameMap,starCount);
		checkBulletCHit(x,y+h-1,bullet,gameMap,starCount);
		checkBulletCHit(x+w-1,y+h-1,bullet,gameMap,starCount);
	}
	
	private static void checkBulletCHit(int pX,int pY,Bullet bullet,GameMap gameMap,int starCount) {
		if (!bullet.isAlive()) {
	        return;
	    }
		
		int tileX = pX / GameConstants.TILE_SIZE;
		int tileY = pY / GameConstants.TILE_SIZE;
		
		Tile tile = gameMap.getTile(tileX, tileY);
		
		if(tile == null) {
			return;
		}
		
		if(tile.isBlocksBullet()) {
			tile.hit(starCount);
			bullet.kill();
			
			if (tile.isDestroyed()) {
				if(!(tile instanceof BaseTile)) {
			        gameMap.setTile(tileX, tileY, new EmptyTile(new Position(tileX, tileY)));
			    }
		    }
		}
	}
}
