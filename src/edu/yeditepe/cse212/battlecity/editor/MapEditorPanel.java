package edu.yeditepe.cse212.battlecity.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.tile.BaseTile;
import edu.yeditepe.cse212.battlecity.tile.BrickWall;
import edu.yeditepe.cse212.battlecity.tile.Bush;
import edu.yeditepe.cse212.battlecity.tile.EmptyTile;
import edu.yeditepe.cse212.battlecity.tile.SteelWall;
import edu.yeditepe.cse212.battlecity.tile.Tile;
import edu.yeditepe.cse212.battlecity.tile.Water;

public class MapEditorPanel extends JPanel{
	private Tile[][] tiles;
	private TileType selectedType;
	
	public MapEditorPanel() {
		int boardWidth = GameConstants.BOARD_WIDTH;
		int boardHeight = GameConstants.BOARD_HEIGHT;
		
		setPreferredSize(new Dimension(boardWidth,boardHeight));
		setBackground(Color.BLACK);
        
        tiles = new Tile[GameConstants.MAP_HEIGHT][GameConstants.MAP_WIDTH];
        clearAll();
        
        selectedType = TileType.BRICK;
        
        addMouseListener(new MouseAdapter() {
        	@Override
            public void mousePressed(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
		});
        
        addMouseMotionListener(new MouseAdapter() {
        	@Override
            public void mouseDragged(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
		});
	}
	
	public void clearAll() {
        for(int y = 0; y < GameConstants.MAP_HEIGHT; y++) {
            for(int x = 0; x < GameConstants.MAP_WIDTH; x++) {
                tiles[y][x] = new EmptyTile(new Position(x, y));
            }
        }
        repaint();
    }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tileSize = GameConstants.TILE_SIZE;
        
        for(int y = 0; y < GameConstants.MAP_HEIGHT; y++) {
            for(int x = 0; x < GameConstants.MAP_WIDTH; x++) {
                Tile tile = tiles[y][x];
                g.setColor(tile.getColor());
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }
	
	private void handleClick(int pixelX, int pixelY) {
        int tileX = pixelX / GameConstants.TILE_SIZE;
        int tileY = pixelY / GameConstants.TILE_SIZE;
        
        if(tileX < 0 || tileX >= GameConstants.MAP_WIDTH 
        || tileY < 0 || tileY >= GameConstants.MAP_HEIGHT) {
            return;
        }
        
        Position pos = new Position(tileX, tileY);
        tiles[tileY][tileX] = createTile(selectedType, pos);
        repaint();
    }
	
	private Tile createTile(TileType type, Position pos) {
        switch(type) {
            case EMPTY:
            	return new EmptyTile(pos);
            case BRICK:
            	return new BrickWall(pos);
            case STEEL:
            	return new SteelWall(pos);
            case WATER:
            	return new Water(pos);
            case BUSH:
            	return new Bush(pos);
            case BASE:
            	return new BaseTile(pos);
            default:
            	return new EmptyTile(pos);
        }
    }
	
	public void setSelectedType(TileType type) {
        this.selectedType = type;
    }
	
	public Tile[][] getTiles() {
        return tiles;
    }
    
    public void setTiles(Tile[][] newTiles) {
        this.tiles = newTiles;
        repaint();
    }
}
