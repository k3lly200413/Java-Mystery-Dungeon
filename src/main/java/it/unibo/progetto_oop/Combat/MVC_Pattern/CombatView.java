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
import java.awt.CardLayout;
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
    private JPanel panel;

    private JPanel buttonPanelContainer;
    private CardLayout cardLayout;
    private JPanel originalButtonPanel;
    private JPanel attackButtonPanel;
    private JPanel healthPanel;

    private JButton attackButton;
    private JButton bagButton;
    private JButton runButton;
    private JButton infoButton;
    private JButton physicalAttackButton;
    private JButton longRangeButton;
    private JButton poisonButton;
    private JButton backButton;

    public CombatView(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        this.setLayout(new BorderLayout());

    }
    private void initializeUI(int size){

        panel = new JPanel(new GridLayout(size, size));
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
        this.add(panel, BorderLayout.CENTER);

        this.healthPanel.setLayout(new BoxLayout(this.healthPanel, BoxLayout.Y_AXIS));

        this.playerHealtBar = new JProgressBar(0, 100);
        this.playerHealtBar.setValue(100);
        this.playerHealtBar.setStringPainted(true);
        this.playerHealtBar.setForeground(Color.GREEN);

        this.enemyHealthBar = new JProgressBar(0, 100);
        this.enemyHealthBar.setValue(100);
        this.enemyHealthBar.setStringPainted(true);
        this.enemyHealthBar.setForeground(Color.RED);
        this.enemyHealthBar.setPreferredSize(new Dimension(35 * size, 20));

        this.healthPanel.add(this.playerHealtBar);
        this.healthPanel.add(enemyHealthBar);
        this.add(healthPanel, BorderLayout.NORTH);

        this.cardLayout = new CardLayout();
        this.buttonPanelContainer = new JPanel(cardLayout);

        this.originalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackButton = new JButton("Attack");
        this.bagButton = new JButton("Bag");
        this.runButton = new JButton("Run");
        this.infoButton = new JButton("info");

        this.originalButtonPanel.add(attackButton);
        this.originalButtonPanel.add(bagButton);
        this.originalButtonPanel.add(runButton);
        this.originalButtonPanel.add(infoButton);

        this.attackButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.physicalAttackButton = new JButton("Physical Attack");
        this.longRangeButton = new JButton("Long Range");
        this.poisonButton = new JButton("Poison");
        this.backButton = new JButton("Back");
        this.attackButtonPanel.add(physicalAttackButton);
        this.attackButtonPanel.add(longRangeButton);
        this.attackButtonPanel.add(poisonButton);
        this.attackButtonPanel.add(backButton);

        this.buttonPanelContainer.add(originalButtonPanel, "originalButtons");
        this.buttonPanelContainer.add(attackButtonPanel, "attackOptions");

        this.add(buttonPanelContainer, BorderLayout.SOUTH);

        this.cardLayout.show(buttonPanelContainer, "origianlButtons");
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

    public void showAttackOptions() {
        this.cardLayout.show(buttonPanelContainer, "attackOptions");
    }

    public void showOriginalButtons(){
        this.cardLayout.show(this.buttonPanelContainer, "originalButton");
    }

    public void display() {
        this.setVisible(true);
    }

}
