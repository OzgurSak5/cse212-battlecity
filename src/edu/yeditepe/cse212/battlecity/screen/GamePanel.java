package edu.yeditepe.cse212.battlecity.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.yeditepe.cse212.battlecity.game.Bullet;
import edu.yeditepe.cse212.battlecity.game.BulletOwner;
import edu.yeditepe.cse212.battlecity.game.Direction;
import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.game.GameStatus;
import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.Position;
import edu.yeditepe.cse212.battlecity.powerup.PowerUp;
import edu.yeditepe.cse212.battlecity.powerup.PowerUpManager;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;
import edu.yeditepe.cse212.battlecity.tile.Bush;
import edu.yeditepe.cse212.battlecity.tile.Tile;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class GamePanel extends JPanel implements KeyListener{
	private GameMap gameMap;
	private PlayerTank playerTank;
	private boolean wPressed;
	private boolean aPressed;
	private boolean sPressed;
	private boolean dPressed;
	private GameLoop gameLoop;
	private GameFrame gameFrame;
	
	public GamePanel(GameMap gameMap,PlayerTank playerTank,GameFrame gameFrame) {
		this.gameMap = gameMap;
		this.playerTank = playerTank;
		setPreferredSize(new Dimension(GameConstants.BOARD_WIDTH,GameConstants.BOARD_HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		this.gameFrame = gameFrame;
	}
	
	private void drawBushOverlay(Graphics g) {
	    int tileSize = GameConstants.TILE_SIZE;
	    for(int y = 0; y < gameMap.getHeight(); y++) {
	        for(int x = 0; x < gameMap.getWidth(); x++) {
	            Tile tile = gameMap.getTile(x, y);
	            if(tile instanceof Bush) {
	                BufferedImage sprite = tile.getSprite();
	                if(sprite != null) {
	                    g.drawImage(sprite, x * tileSize, y * tileSize, tileSize, tileSize, null);
	                }
	                else {
	                    g.setColor(tile.getColor());
	                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
	                }
	            }
	        }
	    }
	}
	
	public synchronized void clearKeys() {
	    wPressed = false;
	    aPressed = false;
	    sPressed = false;
	    dPressed = false;
	}
	
	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawTiles(g);
		drawTank(g);
		drawBullets(g);
		drawEnemyTanks(g);
		drawPowerUps(g);
		drawBushOverlay(g);
		drawGameStatus(g);
	}
	
	private void drawGameStatus(Graphics g) {
		if(gameLoop == null) {
			return;
		}
		
		GameStatus gameStatus = gameLoop.getStatus();
	    
	    if(gameStatus == GameStatus.GAME_OVER) {
	        g.setColor(Color.RED);
	        g.setFont(new Font("Arial", Font.BOLD, 64));
	        String text = "GAME OVER";
	        int textWidth = g.getFontMetrics().stringWidth(text);
	        int x = (GameConstants.BOARD_WIDTH - textWidth) / 2;
	        int y = GameConstants.BOARD_HEIGHT / 2;
	        g.drawString(text, x, y);
	    }
	    else if(gameStatus == GameStatus.LEVEL_COMPLETED) {
	        g.setColor(Color.GREEN);
	        g.setFont(new Font("Arial", Font.BOLD, 48));
	        String text = "LEVEL COMPLETE!";
	        int textWidth = g.getFontMetrics().stringWidth(text);
	        int x = (GameConstants.BOARD_WIDTH - textWidth) / 2;
	        int y = GameConstants.BOARD_HEIGHT / 2;
	        g.drawString(text, x, y);
	    }
	}
	
	private void drawTank(Graphics g) {
		if(!playerTank.isAlive()) {
			return;
		}
		
		Position position = playerTank.getPosition();
		int x = position.getX();
		int y = position.getY();
		int width = playerTank.getWidth();
		int height = playerTank.getHeight();
		
		BufferedImage sprite = playerTank.getSprite();
	    if(sprite != null) {
	        g.drawImage(sprite, x, y, width, height, null);
	    }
	    else {
	        g.setColor(playerTank.getColor());
	        g.fillRect(x, y, width, height);
	    }
	}

	private void drawTiles(Graphics g) {
		int tileSize = GameConstants.TILE_SIZE;
		for(int y = 0;y < gameMap.getHeight();y++) {
			for(int x = 0;x < gameMap.getWidth();x++) {
				Tile tile = gameMap.getTile(x, y);
				BufferedImage sprite = tile.getSprite();
	            
	            if(sprite != null) {
	                g.drawImage(sprite, x * tileSize, y * tileSize, tileSize, tileSize, null);
	            }
	            else {
	                g.setColor(tile.getColor());
	                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
	            }
			}
		}
	}
	
	private void drawBullets(Graphics g) {
		if(gameLoop == null) {
			return;
		}
		
	    g.setColor(Color.LIGHT_GRAY);
	    for(Bullet bullet : gameLoop.getBullets()) {
	        Position p = bullet.getPosition();
	        int cx = p.getX() + bullet.getWidth() / 2;
	        int cy = p.getY() + bullet.getHeight() / 2;

	        int bw = (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.DOWN) ? 5 : 10;
	        int bh = (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.DOWN) ? 10 : 5;

	        g.fillRect(cx - bw / 2, cy - bh / 2, bw, bh);
	    }  
	}
	
	private void drawEnemyTanks(Graphics g) {
		if(gameLoop == null) {
			return;
		}
		
		for(EnemyTank enemy : gameLoop.getEnemyTanks()) {
			if(!enemy.isAlive()) {
				continue;
			}
			
			Position p = enemy.getPosition();
			BufferedImage sprite = enemy.getSprite();
	        if(sprite != null) {
	            g.drawImage(sprite, p.getX(), p.getY(), enemy.getWidth(), enemy.getHeight(), null);
	        }
	        else {
	            g.setColor(enemy.getColor());
	            g.fillRect(p.getX(), p.getY(), enemy.getWidth(), enemy.getHeight());
	        }
		}
	}
	
	private void drawPowerUps(Graphics g) {
	    if(gameLoop == null) {
	        return;
	    }
	    
	    if(gameLoop.getPowerUpManager() == null) {
	        return;
	    }
	    
	    for(PowerUp powerUp : gameLoop.getPowerUpManager().getActivePowerUps()) {
	        if(powerUp.isCollected()) {
	            continue;
	        }
	        
	        Position p = powerUp.getPosition();
	        int x = p.getX();
	        int y = p.getY();
	        int w = powerUp.getWidth();
	        int h = powerUp.getHeight();
	        
	        BufferedImage sprite = powerUp.getSprite();
	        if(sprite != null) {
	            g.drawImage(sprite, x, y, w, h, null);
	        }
	        else {
	            g.setColor(powerUp.getColor());
	            g.fillRect(x, y, w, h);
	            
	            g.setColor(Color.BLACK);
	            g.setFont(new Font("Arial", Font.BOLD, 18));
	            String symbol = powerUp.getSymbol();
	            int textX = x + (w - g.getFontMetrics().stringWidth(symbol)) / 2;
	            int textY = y + (h + g.getFontMetrics().getAscent()) / 2 - 2;
	            g.drawString(symbol, textX, textY);
	        }
	    }
	}
	
	public synchronized Direction getRequestedDirection() {
		if(wPressed) {
			return Direction.UP;
		}
		else if(aPressed) {
			return Direction.LEFT;
		}
		else if(sPressed) {
			return Direction.DOWN;
		}
		else if(dPressed) {
			return Direction.RIGHT;
		}
		return null;
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) {
			wPressed = true;
		}
		else if(key == KeyEvent.VK_A) {
			aPressed = true;
		}
		else if(key == KeyEvent.VK_S) {
			sPressed = true;
		}
		else if(key == KeyEvent.VK_D) {
			dPressed = true;
		}
		else if(key == KeyEvent.VK_SPACE) {
			int maxBullets = (playerTank.getStarCount() >= 2) ? 2 : 1;
		    
		    int playerBullets = 0;
		    for(Bullet b : gameLoop.getBullets()) {
		        if(b.getOwner() == BulletOwner.PLAYER && b.isAlive()) {
		            playerBullets++;
		        }
		    }
		    
		    if(playerBullets < maxBullets) {
		        Bullet bullet = playerTank.shoot();
		        gameLoop.addBullet(bullet);
		    }
		}
		else if(key == KeyEvent.VK_P) {
		    gameFrame.togglePause();
		}
		
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) {
			wPressed = false;
		}
		else if(key == KeyEvent.VK_A) {
			aPressed = false;
		}
		else if(key == KeyEvent.VK_S) {
			sPressed = false;
		}
		else if(key == KeyEvent.VK_D) {
			dPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
