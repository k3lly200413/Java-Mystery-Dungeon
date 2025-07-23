package it.unibo.progetto_oop.Combat.MVC_Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import it.unibo.progetto_oop.Combat.Position.Position;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;

public class CombatView extends JFrame{

    private final Map<JLabel, Position> cells = new HashMap<>();
    
    private JProgressBar playerHealtBar;
    private JProgressBar enemyHealthBar;

    private JPanel healthPanel;
    private JPanel panel;
    private JPanel buttonPanel;

    private JButton attackButton;
    private JButton bagButton;
    private JButton runButton;

    public CombatView(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);

        panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel, BorderLayout.CENTER);

        this.healthPanel = new JPanel();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                final JLabel cellLabel = new JLabel();
                cellLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                cellLabel.setOpaque(true);
                // Set an initial icon for the background
                cellLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/white.jpg")));
                this.cells.put(cellLabel, new Position(j, i)); // Store the label and its position
                panel.add(cellLabel); // Add the label to the grid panel
            }
        }
        this.getContentPane().add(panel, BorderLayout.CENTER);

        this.healthPanel.setLayout(new BoxLayout(this.healthPanel, BoxLayout.Y_AXIS));

        this.playerHealtBar = new JProgressBar(0, 100);
        this.playerHealtBar.setValue(100);
        this.playerHealtBar.setStringPainted(true);
        this.playerHealtBar.setForeground(Color.GREEN);
        this.playerHealtBar.setPreferredSize(new Dimension(35 * size, 20));

        this.enemyHealthBar = new JProgressBar(0, 100);
        this.enemyHealthBar.setValue(100);
        this.enemyHealthBar.setStringPainted(true);
        this.enemyHealthBar.setForeground(Color.RED);
        this.enemyHealthBar.setPreferredSize(new Dimension(35 * size, 20));

        this.healthPanel.add(this.playerHealtBar);
        this.healthPanel.add(Box.createVerticalStrut(5));
        this.healthPanel.add(enemyHealthBar);
        this.getContentPane().add(healthPanel, BorderLayout.NORTH);

        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackButton = new JButton("Attack");
        this.bagButton = new JButton("Bag");
        this.runButton = new JButton("Run");

        this.buttonPanel.add(attackButton);
        this.buttonPanel.add(bagButton);
        this.buttonPanel.add(runButton);

        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void redrawGrid(Map<JLabel, Position> cells, Position player, Position enemy) {
        for (Map.Entry<JLabel, Position> entry : cells.entrySet()) {
            JLabel cellLabel = entry.getKey();
            Position cellPos = entry.getValue();

            if (cellPos.equals(player)){
                cellLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/green.jpg")));
            } else if (cellPos.equals(enemy)) {
                cellLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/red.jpg")));
            } else {
                cellLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/white.jpg")));
            }
        }
    }

    public void display() {
        this.setVisible(true);
    }

}
