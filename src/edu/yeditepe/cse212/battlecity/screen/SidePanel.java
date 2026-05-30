package edu.yeditepe.cse212.battlecity.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.yeditepe.cse212.battlecity.game.GameConstants;
import edu.yeditepe.cse212.battlecity.game.GameLoop;
import edu.yeditepe.cse212.battlecity.tank.EnemyTank;
import edu.yeditepe.cse212.battlecity.tank.PlayerTank;

public class SidePanel extends JPanel{
	private PlayerTank playerTank;
	private GameLoop gameLoop;
	public SidePanel(PlayerTank playerTank) {
		this.playerTank = playerTank;
		setPreferredSize(new Dimension(GameConstants.SIDE_PANEL_WIDTH,GameConstants.BOARD_HEIGHT));
		setBackground(Color.DARK_GRAY);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,14));
		
		g.drawString("LIVES", 20, 30);
	    g.drawString(String.valueOf(playerTank.getLives()), 30, 55);
	    g.drawString("STARS", 20, 100);
	    g.drawString(String.valueOf(playerTank.getStarCount()), 30, 125);
	    g.drawString("ENEMIES", 15, 170);
	    
	    int aliveCount = 0;
	    if (gameLoop != null) {
	        for (EnemyTank enemy : gameLoop.getEnemyTanks()) {
	            if (enemy.isAlive()) {
	                aliveCount++;
	            }
	        }
	    }
	    g.drawString(String.valueOf(aliveCount), 30, 195);
	}
	
	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}
	
	
	
}
