package it.unibo.progetto_oop.Combat.MVC_Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

// TODO: Implement methods necessary to Controller

public class CombatView extends JFrame{

    private final int buttonHeight;
    private final int buttonWidth;

    private final Map<JLabel, Position> cells = new HashMap<>();
    
    private JProgressBar playerHealtBar;
    private JProgressBar enemyHealthBar;
    private JProgressBar playerStaminaBar;

    private JPanel gridpanel;

    private JPanel buttonPanelContainer;
    private CardLayout cardLayout;
    private JPanel originalButtonPanel;
    private JPanel attackButtonPanel;
    private JPanel healthPanel;
    private JPanel bagButtonPanel;

    private JButton attackButton;
    private JButton bagButton;
    private JButton runButton;
    private JButton infoButton;
    private JButton physicalAttackButton;
    private JButton longRangeButton;
    private JButton poisonButton;
    private JButton backButton;
    private JButton backAttackButton;
    private JButton curePoisonButton;
    private JButton attackBuffButton;
    private JButton healButton;

    private JLabel infoLabel;

    private final MeleeButton redrawHelper = new MeleeButton();

    private java.net.URL imgURL;

    public CombatView(int size) {
        this.buttonHeight = (20 * size) / 3;
        this.buttonWidth = (50 * size) / 3;
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

        // TODO: get values from model
        this.playerStaminaBar = new JProgressBar(0, 100); // Set max from model later
        this.playerStaminaBar.setValue(100);            // Set value from model later
        this.playerStaminaBar.setStringPainted(true);
        this.playerStaminaBar.setForeground(Color.CYAN); // Light Blue
        this.playerStaminaBar.setPreferredSize(new Dimension(35 * size, 20)); // Match others

        this.enemyHealthBar = new JProgressBar(0, 100);
        this.enemyHealthBar.setValue(100);
        this.enemyHealthBar.setStringPainted(true);
        this.enemyHealthBar.setForeground(Color.RED);
        this.enemyHealthBar.setPreferredSize(new Dimension(35 * size, 20));

        this.healthPanel.add(new JLabel("Player Health"));
        this.healthPanel.add(this.playerHealtBar);
        this.healthPanel.add(Box.createVerticalStrut(5));
        healthPanel.add(new JLabel("Player Stamina: "));
        healthPanel.add(playerStaminaBar);       
        this.healthPanel.add(new JLabel("Enemy Health"));
        this.healthPanel.add(enemyHealthBar);
        
        this.add(healthPanel, BorderLayout.NORTH);

        this.cardLayout = new CardLayout();
        this.buttonPanelContainer = new JPanel(cardLayout);

        this.originalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackButton = this.createButton("Attack", this.buttonHeight, this.buttonWidth);
        this.bagButton = this.createButton("Bag", this.buttonHeight, this.buttonWidth);
        this.runButton = this.createButton("Run", this.buttonHeight, this.buttonWidth);
        this.infoButton = this.createButton("Info", this.buttonHeight, this.buttonWidth);

        this.originalButtonPanel.add(attackButton);
        this.originalButtonPanel.add(bagButton);
        this.originalButtonPanel.add(runButton);
        this.originalButtonPanel.add(infoButton);

        this.attackButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.physicalAttackButton = this.createButton("Physical Attack", this.buttonHeight, this.buttonWidth);
        this.longRangeButton = this.createButton("Long Range", this.buttonHeight, this.buttonWidth);new JButton("Long Range");
        this.poisonButton = this.createButton("Poison", this.buttonHeight, this.buttonWidth);
        this.backAttackButton = this.createButton("Back", this.buttonHeight, this.buttonWidth);
        this.attackButtonPanel.add(physicalAttackButton);
        this.attackButtonPanel.add(longRangeButton);
        this.attackButtonPanel.add(poisonButton);
        this.attackButtonPanel.add(backAttackButton);

        this.bagButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackBuffButton = createButton("Attack Buff", this.buttonHeight, this.buttonWidth);
        this.curePoisonButton = createButton("Cure poison", this.buttonHeight, this.buttonWidth);
        this.healButton = createButton("Heal", this.buttonHeight, this.buttonWidth);
        this.backButton = createButton("Back", this.buttonHeight, this.buttonWidth);

        this.bagButtonPanel.add(attackBuffButton);
        this.bagButtonPanel.add(curePoisonButton);
        this.bagButtonPanel.add(healButton);
        this.bagButtonPanel.add(backButton);

        this.buttonPanelContainer.add(originalButtonPanel, "originalButtons");
        this.buttonPanelContainer.add(attackButtonPanel, "attackOptions");
        this.buttonPanelContainer.add(bagButtonPanel, "bagButtons");
        

        this.infoLabel = new JLabel("Combat Started!", SwingConstants.CENTER);
        this.infoLabel.setPreferredSize(new Dimension(70 * size, 30));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(buttonPanelContainer);
        southPanel.add(infoLabel);
        this.add(southPanel, BorderLayout.SOUTH);


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

    public void updatePlayerStamina(int value) {
        this.playerStaminaBar.setValue(value);
        this.playerStaminaBar.setString("Stamina: " + value + "/" + playerStaminaBar.getMaximum());
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
                            int flameSize, boolean drawPlayer, boolean drawEnemy, 
                            boolean drawflame, boolean drawPoison, int playerRange, 
                            int enemyRange, boolean isGameOver, Position whoDied,
                            boolean drawBossRayAttack, ArrayList<Position> deathRayPath,
                            boolean drawPoisonDamage, int poisonYCoord;
                            boolean isCharging, int chargingCellDistance) {
        
            for (var entry : cells.entrySet()) {
            JLabel cellLabel = entry.getKey();
            Position cellPos = entry.getValue();
            Icon icon = null;

            if (isGameOver) {
                if (this.redrawHelper.deathNeighbours(whoDied, cellPos, enemyRange)){
                    icon = this.getIconResource(whoDied.equals(player) ? "/Screenshot 2025-03-25 164621.png" : "/red.jpg");
                } else if (drawPlayer && redrawHelper.deathNeighbours(whoDied, cellPos, enemyRange)) {
                    icon = getIconResource(whoDied.equals(player) ? "/Screenshot 2025-03-25 164621.png" : "/red.jpg");
                }
            }

            else if ((drawflame || drawPoison || drawBossRayAttack) && this.redrawHelper.neighbours(cellPos, flame, flameSize)){
                icon = drawflame ? this.getIconResource("/yellow.jpg") : drawPoison ? this.getIconResource("/green.jpg") : getIconResource("/purple.png");
            
            else if (drawPoisonDamage && entry.getValue().y() == poisonYCorrd) {
                icon = this.getIconResource("/green.jpg");
            }
            else if ((drawflame || drawPoison) && this.redrawHelper.neighbours(cellPos, flame, 0)){
                icon = drawflame ? this.getIconResource("/yellow.jpg") : this.getIconResource("/green.jpg");
            } else if (drawPlayer && this.redrawHelper.neighbours(player, cellPos, playerRange)){
                icon = this.getIconResource("/Screenshot 2025-03-25 164621.png");
            } else if (drawEnemy && this.redrawHelper.neighbours(enemy, cellPos, enemyRange)) {
                icon = getIconResource("/red.jpg");
            } else if (isCharging && this.redrawHelper.deathNeighbours(enemy, cellPos, chargingCellDistance)) {
                icon = getIconResource("/purple.png");
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
        this.cardLayout.show(this.buttonPanelContainer, "originalButtons");
    }

    public void showBagButtons() {
        this.cardLayout.show(this.buttonPanelContainer, "bagButtons");
    }

    public void setAllButtonsEnabled(){
        this.setPanelEnabled(this.originalButtonPanel, true);
        this.setPanelEnabled(this.attackButtonPanel, true);
    }

    public void setAllButtonsDisabled() {
        this.setPanelEnabled(this.originalButtonPanel, false);
        this.setPanelEnabled(this.attackButtonPanel, false);
    }

    public void setCustomButtonEnabled(JButton buttonToEnable){
        buttonToEnable.setEnabled(true);
    }

    public void setCustomButtonDisabled(JButton buttonToDisable) {
        buttonToDisable.setEnabled(false);
    }

    private void setPanelEnabled(JPanel panel, boolean enablePanel){
        panel.setEnabled(enablePanel);
        for (Component comp : panel.getComponents()){
            if (comp instanceof JButton) {
                comp.setEnabled(enablePanel);
            }
        }
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

    public JButton getLongRangeAttackButton() {
        return this.longRangeButton;
    }

    public JButton getPoisonAttackButton() {
        return this.poisonButton;
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 20, 20);
        g.dispose();
        return new ImageIcon(image);
    }

    private JButton createButton(String name, int height, int length) {
        JButton tempButton = new JButton(name);
        tempButton.setPreferredSize(new Dimension(length, height));
        return tempButton;
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
        //TODO: Put in two different methods 
        this.backAttackButton.addActionListener(e);
    }
    public void addCurePoisonButtonListener(ActionListener e) {
        this.curePoisonButton.addActionListener(e);
    }
}
