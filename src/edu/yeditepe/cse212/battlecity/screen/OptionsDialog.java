package edu.yeditepe.cse212.battlecity.screen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.yeditepe.cse212.battlecity.game.Difficulty;

public class OptionsDialog extends JDialog{
	private GameFrame gameFrame;
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    
    public OptionsDialog(GameFrame parent) {
        super(parent, "Options", true);
        this.gameFrame = parent;
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        mainPanel.add(new JLabel("Select Difficulty:"));
        
        easyButton = new JRadioButton("Easy", true);
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");
        
        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);
        
        mainPanel.add(easyButton);
        mainPanel.add(mediumButton);
        mainPanel.add(hardButton);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton applyButton = new JButton("Apply (Restart Game)");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Difficulty selected = Difficulty.EASY;
                if(mediumButton.isSelected()) {
                    selected = Difficulty.MEDIUM;
                }
                else if(hardButton.isSelected()) {
                    selected = Difficulty.HARD;
                }
                
                int confirm = JOptionPane.showConfirmDialog(OptionsDialog.this,
                    "Changing difficulty will restart the game. Continue?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
                
                if(confirm == JOptionPane.YES_OPTION) {
                    gameFrame.setStartingDifficulty(selected);
                    dispose();
                }
            }
        });
        buttonPanel.add(applyButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
