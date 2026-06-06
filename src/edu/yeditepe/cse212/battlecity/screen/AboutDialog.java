package edu.yeditepe.cse212.battlecity.screen;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutDialog extends JDialog{
	
	public AboutDialog(Frame parent) {
        super(parent, "About", true);   // true = modal
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Battle City", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title);
        
        panel.add(new JLabel("Name: Özgür Sak", SwingConstants.CENTER));
        panel.add(new JLabel("Student Number: 20240702066", SwingConstants.CENTER));
        panel.add(new JLabel("Email: ozgur.sak@std.yeditepe.edu.tr", SwingConstants.CENTER));
        panel.add(new JLabel("CSE212 - Spring 2026", SwingConstants.CENTER));
        
        add(panel, BorderLayout.CENTER);
    }
}
