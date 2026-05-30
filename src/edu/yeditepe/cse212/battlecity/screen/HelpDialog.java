package edu.yeditepe.cse212.battlecity.screen;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog{
	public HelpDialog(Frame parent) {
        super(parent, "How to Play", true);
        setSize(450, 400);
        setLocationRelativeTo(parent);
        
        String helpText = 
            "BATTLE CITY - HOW TO PLAY\n" +
            "==========================\n\n" +
            "CONTROLS:\n" +
            "  W / A / S / D  - Move tank\n" +
            "  SPACE          - Shoot\n" +
            "  P              - Pause / Resume\n\n" +
            "OBJECTIVE:\n" +
            "  - Destroy all 20 enemy tanks per level\n" +
            "  - Protect your base (the yellow tile)\n" +
            "  - Survive 3 levels to win\n\n" +
            "TILES:\n" +
            "  Red    - Brick wall (destructible)\n" +
            "  Gray   - Steel wall (needs 3 stars)\n" +
            "  Blue   - Water (impassable)\n" +
            "  Green  - Bush (hide here)\n" +
            "  Yellow - Your base (DO NOT let it die)\n\n" +
            "POWER-UPS:\n" +
            "  Green  - Extra Life\n" +
            "  Yellow - Star (3 stars destroy steel)\n" +
            "  Orange - Bomb (kill all visible enemies)\n" +
            "  Cyan   - Shield (10s invincible)\n" +
            "  White  - Clock (10s freeze enemies)\n" +
            "  Gray   - Shovel (20s steel around base)\n\n" +
            "TIPS:\n" +
            "  - Hide in bushes to ambush enemies\n" +
            "  - Stars upgrade your tank progressively\n" +
            "  - Keep moving - stationary tanks die fast";
        
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(getBackground());
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
