package it.unibo.progetto_oop.Combat.MVC_Pattern;

import javax.swing.BorderFactory;
// import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import it.unibo.progetto_oop.Combat.CommandPattern.MeleeButton;
import it.unibo.progetto_oop.Combat.Position.Position;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

public class CombatView extends JFrame{

    private final Map<JLabel, Position> cells = new HashMap<>();
    
    private JProgressBar playerHealtBar;
    private JProgressBar enemyHealthBar;
    private JPanel gridpanel;

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

    private JLabel infoLabel;

    private final MeleeButton redrawHelper = new MeleeButton();

    private java.net.URL imgURL;

    public CombatView(int size) {
        this.setTitle("Combat Screen");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 75 * size);
        this.setLayout(new BorderLayout());
        this.initializeUI(size);
    }

    private void initializeUI(int size){

        this.gridpanel = new JPanel(new GridLayout(size, size));
        this.healthPanel = new JPanel();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                final JLabel cellLabel = new JLabel();
                cellLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                cellLabel.setOpaque(true);
                // Set an initial icon for the background
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
                cellLabel.setIcon(this.getIconResource("/white.jpg"));
                this.cells.put(cellLabel, new Position(j, i)); // Store the label and its position
                this.gridpanel.add(cellLabel); // Add the label to the grid panel
            }
        }
        this.add(this.gridpanel, BorderLayout.CENTER);

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

        this.healthPanel.add(new JLabel("Player Health"));
        this.healthPanel.add(this.playerHealtBar);
        this.healthPanel.add(new JLabel("Enemy Health"));
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

        this.showOriginalButtons();
    }

    public void setHealthBarMax(int max) {
        this.playerHealtBar.setMaximum(max);
        this.enemyHealthBar.setMaximum(max);
    }

    public void updatePlayerHealth(int value) {
        this.playerHealtBar.setValue(value);
        this.playerHealtBar.setString("Player: " + value + "/" + this.playerHealtBar.getMaximum());
    }

    public void updateEnemyHealth(int value) {
        enemyHealthBar.setValue(value);
        enemyHealthBar.setString("Enemy: " + value + "/" + this.enemyHealthBar.getMaximum());
    }
    
    // NEW: Methods to control the info label
    public void showInfo(String text) {
        // Use HTML to allow for multi-line messages
        infoLabel.setText("<html>" + text.replace("\n", "<br>") + "</html>");
    }

    public void clearInfo() {
        infoLabel.setText("");
    }

    public void redrawGrid( Position player, Position enemy, Position flame, 
                            boolean drawPlayer, boolean drawEnemy, 
                            boolean drawflame, boolean drawPoison, 
                            int playerRange, int enemyRange) {
        for (var entry : cells.entrySet()) {
            JLabel cellLabel = entry.getKey();
            Position cellPos = entry.getValue();
            Icon icon = null;
            if ((drawflame || drawPoison) && this.redrawHelper.neighbours(player, flame, enemyRange)){
                icon = drawflame ? this.getIconResource("/yellow.jpg") : this.getIconResource("/green.jpg");
            } else if (drawPlayer && this.redrawHelper.neighbours(player, cellPos, playerRange)){
                icon = this.getIconResource("/Screenshot 2025-03-25 164621.png");
            } else if (drawEnemy && this.redrawHelper.neighbours(enemy, cellPos, enemyRange)) {
                icon = getIconResource("/red.jpg");
            } else {
                icon = getIconResource("/white.jpg");
            }
            cellLabel.setIcon(icon);
        }
        this.revalidate();
        this.repaint();
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

    public void close() {
        this.dispose();
    }

    private ImageIcon getIconResource(String path) {
        this.imgURL = getClass().getResource(path);
        if (this.imgURL != null){
            return new ImageIcon(this.imgURL);
        }
        else{
            System.err.println("Was not able to find file: " + path);
            return this.createDefaultIcon();
        }
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 20, 20);
        g.dispose();
        return new ImageIcon(image);
    } 

    // Listener methods (Used By Controller)

    public void addAttackButtonListener(ActionListener e) {
        this.attackButton.addActionListener(e);
    }
    public void addBagButtonListener(ActionListener e) {
        this.bagButton.addActionListener(e);
    }
    public void addRunButtonListener(ActionListener e){
        this.runButton.addActionListener(e);
    }
    public void addInfoButtonListener(ActionListener e){
        this.infoButton.addActionListener(e);
    }
    public void addPhysicalButtonListener(ActionListener e){
        this.physicalAttackButton.addActionListener(e);
    }
    public void addLongRangeButtonListener(ActionListener e){
        this.longRangeButton.addActionListener(e);
    }
    public void addPoisonButtonListener(ActionListener e){
        this.poisonButton.addActionListener(e);
    }
    public void addBackButtonListener(ActionListener e){
        this.backButton.addActionListener(e);
    }

}
