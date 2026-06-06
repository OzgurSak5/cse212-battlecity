package edu.yeditepe.cse212.battlecity.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.yeditepe.cse212.battlecity.editor.MapEditorFrame;
import edu.yeditepe.cse212.battlecity.util.ImageLoader;

public class TitleScreenFrame extends JFrame {

    public TitleScreenFrame() {
        setTitle("Battle City");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MenuPanel panel = new MenuPanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
        panel.requestFocusInWindow();
    }

    private class MenuPanel extends JPanel implements KeyListener {
        private final String[] items = {"PLAY", "PLAY CUSTOM MAP", "MAP EDITOR", "HIGH SCORES", "EXIT"};
        private int selected = 0;
        private final int[] itemY = new int[items.length];
        private BufferedImage logo;
        private BufferedImage cursor;

        public MenuPanel() {
            setPreferredSize(new Dimension(560, 540));
            setBackground(Color.BLACK);
            setFocusable(true);
            addKeyListener(this);

            logo = ImageLoader.load("title_logo.png");
            cursor = ImageLoader.load("tanks/player1_gold/player1_gold_shape0_right_0.png");

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    requestFocusInWindow();
                    for(int i = 0; i < items.length; i++) {
                        if(Math.abs(e.getY() - itemY[i]) < 18) {
                            selected = i;
                            repaint();
                            activate();
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.drawString("I-   00        HI- 20000", 20, 28);

            if(logo != null) {
                int lw = 460;
                int lh = logo.getHeight() * lw / logo.getWidth();
                g.drawImage(logo, (w - lw) / 2, 50, lw, lh, null);
            }
            else {
                g.setColor(new Color(210, 90, 40));
                g.setFont(new Font("Arial", Font.BOLD, 46));
                String t = "BATTLE CITY";
                g.drawString(t, (w - g.getFontMetrics().stringWidth(t)) / 2, 130);
            }

            g.setFont(new Font("Monospaced", Font.BOLD, 22));
            int startY = 290;
            int gap = 42;
            for(int i = 0; i < items.length; i++) {
                int y = startY + i * gap;
                itemY[i] = y - 7;
                int tw = g.getFontMetrics().stringWidth(items[i]);
                int x = (w - tw) / 2;

                g.setColor(i == selected ? Color.YELLOW : Color.WHITE);
                g.drawString(items[i], x, y);

                if(i == selected && cursor != null) {
                    g.drawImage(cursor, x - 44, y - 24, 30, 30, null);
                }
            }

            g.setColor(Color.GRAY);
            g.setFont(new Font("Monospaced", Font.PLAIN, 12));
            String f1 = "CSE212 - YEDITEPE UNIVERSITY - 2026";
            g.drawString(f1, (w - g.getFontMetrics().stringWidth(f1)) / 2, getHeight() - 24);
        }

        private void activate() {
            switch(selected) {
                case 0:
                    GameFrame game = new GameFrame();
                    game.setVisible(true);
                    dispose();
                    break;
                case 1:
                    JFileChooser chooser = new JFileChooser(new File("levels"));
                    chooser.setDialogTitle("Choose a map to play");
                    int result = chooser.showOpenDialog(MenuPanel.this);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        GameFrame custom = new GameFrame(path);
                        custom.setVisible(true);
                        dispose();
                    }
                    break;
                case 2:
                    MapEditorFrame editor = new MapEditorFrame();
                    editor.setVisible(true);
                    break;
                case 3:
                    HighScoreFrame scores = new HighScoreFrame();
                    scores.setVisible(true);
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                selected = (selected - 1 + items.length) % items.length;
                repaint();
            }
            else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                selected = (selected + 1) % items.length;
                repaint();
            }
            else if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                activate();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        @Override
        public void keyTyped(KeyEvent e) {}
    }
}