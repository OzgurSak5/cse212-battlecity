package edu.yeditepe.cse212.battlecity.map;

import edu.yeditepe.cse212.battlecity.tile.BaseTile;
import edu.yeditepe.cse212.battlecity.tile.EmptyTile;
import edu.yeditepe.cse212.battlecity.tile.SteelWall;
import edu.yeditepe.cse212.battlecity.tile.Tile;

public class GameMap {
	private Tile[][] tiles;
	private int width;
	private int height;
	private BaseTile baseTile;
	
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[height][width];
		
		for(int y = 0;y < height;y++) {
			for(int x = 0;x < width;x++) {
				tiles[y][x] = new EmptyTile(new Position(x, y));
			}
		}
	}
	
	public synchronized Tile[] shieldBaseWithSteel() {
	    if(baseTile == null) {
	        return new Tile[0];
	    }
	    
	    int baseX = baseTile.getPosition().getX();
	    int baseY = baseTile.getPosition().getY();
	    
	    int[][] offsets = {
	        {-1, -1}, {0, -1}, {1, -1},
	        {-1,  0},          {1,  0},
	        {-1,  1}, {0,  1}, {1,  1}
	    };
	    
	    Tile[] snapshot = new Tile[8];
	    
	    for(int i = 0; i < offsets.length; i++) {
	        int nx = baseX + offsets[i][0];
	        int ny = baseY + offsets[i][1];
	        
	        if(!isValidPosition(nx, ny)) {
	            snapshot[i] = null;
	            continue;
	        }
	        
	        snapshot[i] = tiles[ny][nx];
	        tiles[ny][nx] = new SteelWall(new Position(nx, ny));
	    }
	    
	    return snapshot;
	}

	public synchronized void unshieldBase(Tile[] snapshot) {
	    if(baseTile == null || snapshot == null) {
	        return;
	    }
	    
	    int baseX = baseTile.getPosition().getX();
	    int baseY = baseTile.getPosition().getY();
	    
	    int[][] offsets = {
	        {-1, -1}, {0, -1}, {1, -1},
	        {-1,  0},          {1,  0},
	        {-1,  1}, {0,  1}, {1,  1}
	    };
	    
	    for(int i = 0; i < offsets.length; i++) {
	        if(snapshot[i] == null) continue;
	        
	        int nx = baseX + offsets[i][0];
	        int ny = baseY + offsets[i][1];
	        
	        tiles[ny][nx] = snapshot[i];
	    }
	}

	public Tile getTile(int x,int y) {
		if(!isValidPosition(x, y)) {
			return null;
		}
		return tiles[y][x];
	}

	public void setTile(int x, int y, Tile tile) {
	    if(!isValidPosition(x, y)) {
	        return;
	    }
	    tiles[y][x] = tile;
	    if(tile instanceof BaseTile) {
	        baseTile = (BaseTile) tile;
	    }
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isValidPosition(int x, int y) {
		if(x >= 0 && y >= 0 && x < width && y < height) {
			return true;
		}
		return false;
	}
	
	public boolean isBaseDestroyed() {
	    if(baseTile == null) {
	        return false;
	    }
	    return baseTile.isDestroyed();
	}
}
