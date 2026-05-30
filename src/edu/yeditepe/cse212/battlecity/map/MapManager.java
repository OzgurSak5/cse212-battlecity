package edu.yeditepe.cse212.battlecity.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.yeditepe.cse212.battlecity.exception.MapLoadException;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.tile.BaseTile;
import edu.yeditepe.cse212.battlecity.tile.BrickWall;
import edu.yeditepe.cse212.battlecity.tile.Bush;
import edu.yeditepe.cse212.battlecity.tile.SteelWall;
import edu.yeditepe.cse212.battlecity.tile.Tile;
import edu.yeditepe.cse212.battlecity.tile.Water;

public class MapManager {
    
    public static GameMap loadMap(String filePath) throws MapLoadException {
        ArrayList<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch(IOException e) {
            throw new MapLoadException("Could not read map file: " + filePath, e);
        }
        
        if(lines.size() != GameConstants.MAP_HEIGHT) {
            throw new MapLoadException("Map must have " + GameConstants.MAP_HEIGHT 
                + " rows but has " + lines.size());
        }
        
        GameMap gameMap = new GameMap(GameConstants.MAP_WIDTH, GameConstants.MAP_HEIGHT);
        
        for(int y = 0; y < GameConstants.MAP_HEIGHT; y++) {
            String row = lines.get(y);
            
            if(row.length() < GameConstants.MAP_WIDTH) {
                throw new MapLoadException("Row " + y + " is too short: " + row);
            }
            
            for(int x = 0; x < GameConstants.MAP_WIDTH; x++) {
                char c = row.charAt(x);
                Position pos = new Position(x, y);
                
                switch(c) {
                    case '.':
                        break;
                    case 'B':
                        gameMap.setTile(x, y, new BrickWall(pos));
                        break;
                    case 'S':
                        gameMap.setTile(x, y, new SteelWall(pos));
                        break;
                    case 'W':
                        gameMap.setTile(x, y, new Water(pos));
                        break;
                    case 'H':
                        gameMap.setTile(x, y, new Bush(pos));
                        break;
                    case 'X':
                        gameMap.setTile(x, y, new BaseTile(pos));
                        break;
                    default:
                        throw new MapLoadException("Unknown tile char '" + c + "' at (" + x + "," + y + ")");
                }
            }
        }
        
        return gameMap;
    }
    
    public static void saveMap(String filePath, Tile[][] tiles) throws MapLoadException {
        if(tiles == null) {
            throw new MapLoadException("Cannot save null tiles");
        }
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(int y = 0; y < GameConstants.MAP_HEIGHT; y++) {
                StringBuilder row = new StringBuilder();
                
                for(int x = 0; x < GameConstants.MAP_WIDTH; x++) {
                    Tile tile = tiles[y][x];
                    row.append(tileToChar(tile));
                }
                
                writer.write(row.toString());
                writer.newLine();
            }
        }
        catch(IOException e) {
            throw new MapLoadException("Could not save map: " + e.getMessage(), e);
        }
    }

    private static char tileToChar(Tile tile) {
        if(tile instanceof BrickWall) return 'B';
        if(tile instanceof SteelWall) return 'S';
        if(tile instanceof Water)     return 'W';
        if(tile instanceof Bush)      return 'H';
        if(tile instanceof BaseTile)  return 'X';
        return '.';
    }
}
