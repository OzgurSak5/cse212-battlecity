package edu.yeditepe.cse212.battlecity.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.yeditepe.cse212.battlecity.exception.MapLoadException;
import edu.yeditepe.cse212.battlecity.map.GameMap;
import edu.yeditepe.cse212.battlecity.map.MapManager;

public class MapEditorFrame extends JFrame{
	private MapEditorPanel editorPanel;
	
	public MapEditorFrame() {
        setTitle("Map Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        editorPanel = new MapEditorPanel();
        add(editorPanel, BorderLayout.CENTER);
        
        add(buildPalettePanel(), BorderLayout.EAST);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
	
	private JPanel buildPalettePanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Tile Palette"));
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton brick = new JRadioButton("Brick", true);
        brick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.BRICK);
            }
        });
        
        JRadioButton steel = new JRadioButton("Steel");
        steel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.STEEL);
            }
        });
        
        JRadioButton water = new JRadioButton("Water");
        water.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.WATER);
            }
        });
        
        JRadioButton bush = new JRadioButton("Bush");
        bush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.BUSH);
            }
        });
        
        JRadioButton base = new JRadioButton("Base");
        base.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.BASE);
            }
        });
        
        JRadioButton empty = new JRadioButton("Empty (Eraser)");
        empty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.setSelectedType(TileType.EMPTY);
            }
        });
        
        group.add(brick);
        group.add(steel);
        group.add(water);
        group.add(bush);
        group.add(base);
        group.add(empty);
        
        panel.add(brick);
        panel.add(steel);
        panel.add(water);
        panel.add(bush);
        panel.add(base);
        panel.add(empty);
        
        return panel;
    }
	
	private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap();
            }
        });
        
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMap();
            }
        });
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MapEditorFrame.this,
                    "Clear the entire map?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    editorPanel.clearAll();
                }
            }
        });
        
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void saveMap() {
        JFileChooser chooser = new JFileChooser(new File("levels"));
        chooser.setDialogTitle("Save Map");
        
        int result = chooser.showSaveDialog(this);
        if(result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        if(!path.endsWith(".txt")) {
            path = path + ".txt";
        }
        
        try {
            MapManager.saveMap(path, editorPanel.getTiles());
            JOptionPane.showMessageDialog(this, 
                "Map saved to: " + path,
                "Saved",
                JOptionPane.INFORMATION_MESSAGE);
        }
        catch(MapLoadException e) {
            JOptionPane.showMessageDialog(this,
                "Failed to save: " + e.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadMap() {
        JFileChooser chooser = new JFileChooser(new File("levels"));
        chooser.setDialogTitle("Load Map");
        
        int result = chooser.showOpenDialog(this);
        if(result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File file = chooser.getSelectedFile();
        
        try {
            GameMap loaded = MapManager.loadMap(file.getAbsolutePath());
            int w = loaded.getWidth();
            int h = loaded.getHeight();
            edu.yeditepe.cse212.battlecity.tile.Tile[][] tiles = 
                new edu.yeditepe.cse212.battlecity.tile.Tile[h][w];
            for(int y = 0; y < h; y++) {
                for(int x = 0; x < w; x++) {
                    tiles[y][x] = loaded.getTile(x, y);
                }
            }
            editorPanel.setTiles(tiles);
        }
        catch(MapLoadException e) {
            JOptionPane.showMessageDialog(this,
                "Failed to load: " + e.getMessage(),
                "Load Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
