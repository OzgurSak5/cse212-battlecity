package edu.yeditepe.cse212.battlecity.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import edu.yeditepe.cse212.battlecity.editor.MapEditorFrame;
import edu.yeditepe.cse212.battlecity.screen.AboutDialog;
import edu.yeditepe.cse212.battlecity.screen.GameFrame;
import edu.yeditepe.cse212.battlecity.screen.HelpDialog;
import edu.yeditepe.cse212.battlecity.screen.HighScoreFrame;
import edu.yeditepe.cse212.battlecity.screen.OptionsDialog;

public class GameMenuBar extends JMenuBar{
	private GameFrame gameFrame;
	private JMenuItem pauseItem;
	
	
	public GameMenuBar(GameFrame gameFrame) {
		this.gameFrame = gameFrame;

		buildGameMenu();
		buildToolsMenu();
		buildScoreMenu();
		buildHelpMenu();
	}
	
	private void attachMenuListener(JMenu menu) {
	    menu.addMenuListener(new MenuListener() {
	        @Override
	        public void menuSelected(MenuEvent e) {
	            gameFrame.onMenuOpened();
	        }
	        @Override
	        public void menuDeselected(MenuEvent e) {}
	        @Override
	        public void menuCanceled(MenuEvent e) {}
	    });
	}
	
	private void buildGameMenu() {
		JMenu gameMenu = new JMenu("Game");
		attachMenuListener(gameMenu);
		
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.startNewGame();
			}
		});
		
		pauseItem = new JMenuItem("Pause");
	    pauseItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            gameFrame.togglePause();
	        }
	    });
	    
	    JMenuItem optionsItem = new JMenuItem("Options");
	    optionsItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            OptionsDialog dialog = new OptionsDialog(gameFrame);
	            dialog.setVisible(true);
	        }
	    });
	    
	    JMenuItem mainMenuItem = new JMenuItem("Main Menu");
	    mainMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            gameFrame.returnToTitle();
	        }
	    });
	    
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		gameMenu.add(newGameItem);
		gameMenu.add(pauseItem);
		gameMenu.add(optionsItem);
		gameMenu.add(mainMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitItem);
		add(gameMenu);
	}
	
	private void buildToolsMenu() {
		JMenu toolsMenu = new JMenu("Tools");
		attachMenuListener(toolsMenu);
		JMenuItem mapEditorItem = new JMenuItem("Map Editor");
		
		mapEditorItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MapEditorFrame frame = new MapEditorFrame();
				frame.setVisible(true);
			}
		});
		
		toolsMenu.add(mapEditorItem);
		add(toolsMenu);
	}
	
	private void buildScoreMenu() {
		JMenu scoresMenu = new JMenu("Scores");
		attachMenuListener(scoresMenu);
		JMenuItem highScoresItem = new JMenuItem("High Scores");
		
		highScoresItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HighScoreFrame frame = new HighScoreFrame();
		        frame.setVisible(true);
			}
		});
		
		scoresMenu.add(highScoresItem);
		add(scoresMenu);
		
	}
	
	private void buildHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		attachMenuListener(helpMenu);
		JMenuItem helpItem = new JMenuItem("How to Play");
		
		helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog dialog = new HelpDialog(gameFrame);
		        dialog.setVisible(true);
			}
		});
		
		JMenuItem aboutItem = new JMenuItem("About");
		
		aboutItem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        AboutDialog dialog = new AboutDialog(gameFrame);
		        dialog.setVisible(true);
		    }
		});
		
		helpMenu.add(helpItem);
	    helpMenu.add(aboutItem);
	    add(helpMenu);
	}
	
	public void setPauseLabel(String label) {
		pauseItem.setText(label);
	}
	
}
