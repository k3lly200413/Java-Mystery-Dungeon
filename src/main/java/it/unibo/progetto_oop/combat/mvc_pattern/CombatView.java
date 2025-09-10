package it.unibo.progetto_oop.combat.mvc_pattern;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.helper.Neighbours;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * View class in Model View Controller Pattern.
 *
 * @author kelly.applebee@studio.unibo.it
 */
public class CombatView extends JPanel implements CombatViewInterface {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Default height of the health bars.
     */
    private static final int DEFAULT_BAR_HEIGHT = 20;
    /**
     * Default width of the health bars.
     */
    private static final int DEFAULT_BAR_WIDTH = 35;
    /**
     * Default space buffer between elements.
     */
    private static final int DEFAULT_SPACE_BUFFER = 5;
    /**
     * Default width of the squares in the grid.
     */
    private static final int DEFAULT_SQUARE_WIDTH = 20;
    /**
     * Default height of the squares in the grid.
     */
    private static final int DEFAULT_SQUARE_HEIGHT = 20;
    /**
     * Length mutator for the Info.
     */
    private static final int INFO_LENGTH_MUTATOR = 30;
    /**
     * Length mutator for the info in the combat view.
     */
    private static final int DEFAULT_INFO_HEIGHT = 70;
    /**
     * String to separate numbers in health or stamina bars.
     */
    private static final String SEPARATOR_BAR = "/";
    /**
     * Colour of charging boss.
     */
    private static final String PURPLE = "/Screenshot 2025-03-25 164621.png";
    /**
     * Colour of enemy.
     */
    private static final String RED = "/red.jpg";
    /**
     * Colour of player.
     */
    private static final String GREEN = "/green.jpg";
    /**
     * Size to use for the combat view.
     */
    private final int sizeToUse;
    /**
     * Height and width of the buttons in the combat view.
     */
    private final int buttonHeight;
    /**
     * Height modifier for the combat view.
     */
    private final int heightModifier;
    /**
     * Width modifier for the combat view.
     */
    private final int widthModifier;
    /**
     * Width of the buttons in the combat view.
     */
    private final int buttonWidth;
    /**
     * Map to hold JLabel components and their corresponding Position.
     */
    private final Map<JLabel, Position> cells;
    /**
     * CombatController instance.
     */
    private CombatController controller;
    /**
     * Height and width of the player's health bar.
     */
    private JProgressBar playerHealtBar;
    /**
     * Height and width of the player's health bar.
     */
    private JProgressBar enemyHealthBar;
    /**
     * Height and width of the enemy's health bar.
     */
    private JProgressBar playerStaminaBar;
    /**
     * Container for the button panels, allowing for card layout switching.
     */
    private JPanel buttonPanelContainer;
    /**
     * Container for the button panels, allowing for card layout switching.
     */
    private CardLayout cardLayout;
    /**
     * Panel containing the original buttons.
     */
    private JPanel originalButtonPanel;
    /**
     * Panel containing the attack buttons.
     */
    private JPanel attackButtonPanel;
    /**
     * Panel containing the bag buttons.
     */
    private JPanel bagButtonPanel;
    /**
     * Button for initiating an attack in the combat view.
     */
    private JButton attackButton;
    /**
     * Button for opening the bag in the combat view.
     */
    private JButton bagButton;
    /**
     * Button for running away in the combat view.
     */
    private JButton runButton;
    /**
     * Button for displaying information in the combat view.
     */
     private JButton infoButton;
    /**
     * Button for performing a physical attack in the combat view.
     */
    private JButton physicalAttackButton;
    /**
     *  Button for performing a long-range attack in the combat view.
     */
    private JButton longRangeButton;
    /**
     * Button for performing a poison attack in the combat view.
     */
    private JButton poisonButton;
    /**
     * Button for going back to the previous menu in the combat view.
     */
    private JButton backButton;
    /**
     * Button for going back to the previous menu in the combat view.
     */
    private JButton backAttackButton;
    /**
     * Button for curing poison in the combat view.
     */
    private JButton curePoisonButton;
    /**
     * Button for applying an attack buff in the combat view.
     */
    private JButton attackBuffButton;
    /**
     * Button for healing in the combat view.
     */
    private JButton healButton;
    /**
     * Label for displaying information in the combat view.
     */
    private JLabel infoLabel;
    /**
     * Neighbours helper class for position calculations.
     */
    private final Neighbours neighbours;
    /**
     * Maximum health of the player.
     */
    private final int maxPlayerHealth;
    /**
     * Maximum health of the enemy.
     */
    private final int maxEnemyHealth;

    /**
     * Constructor for CombatView.
     *
     * @param size the size of the combat view, used to scale components
     * @param buttonHeightToAssign the height of the buttons
     * @param buttonWidthToAssign the width of the buttons
     * @param heightModifierToAssign the height modifier for scaling
     * @param widthModifierToAssign the width modifier for scaling
     * @param maxPlayerHealthToAssign the maximum health of the player
     * @param maxEnemyHealthToAssign the maximum health of the enemy
     */
    public CombatView(final int size,
    final int buttonHeightToAssign,
    final int buttonWidthToAssign,
    final int heightModifierToAssign,
    final int widthModifierToAssign,
    final int maxPlayerHealthToAssign,
    final int maxEnemyHealthToAssign) {
        this.sizeToUse = size;
        this.heightModifier = heightModifierToAssign;
        this.widthModifier = widthModifierToAssign;
        this.cells = new HashMap<>();
        // this.buttonHeight = (20 * size) / 3;
        this.buttonHeight = buttonHeightToAssign;
        // this.buttonWidth = (50 * size) / 3;
        this.buttonWidth = buttonWidthToAssign;
        // this.setSize(70 * size, 75 * size);
        this.maxPlayerHealth = maxPlayerHealthToAssign;
        this.maxEnemyHealth = maxEnemyHealthToAssign;
        this.neighbours = new Neighbours();
    }

    /**
     * Initialize the combat view.
     * Created new method because of PMD
     */
    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        this.initializeUI(
            sizeToUse,
            DEFAULT_BAR_HEIGHT,
            DEFAULT_BAR_WIDTH,
            DEFAULT_SPACE_BUFFER,
            DEFAULT_SQUARE_WIDTH,
            DEFAULT_SQUARE_HEIGHT
        );
    }

    private void initializeUI(final int size,
    final int barHeight,
    final int barWidth,
    final int spaceBuffer,
    final int squareWidth,
    final int squareHeight) {
        this.setSize(heightModifier * size, widthModifier * size);

        final JPanel gridpanel = new JPanel(new GridLayout(size, size));
        final JPanel healthPanel = new JPanel();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JLabel cellLabel = new JLabel();
                cellLabel.setBorder(
                    BorderFactory.createBevelBorder(
                        BevelBorder.RAISED));
                cellLabel.setOpaque(true);
                // Set an initial icon for the background
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
                cellLabel.setIcon(
                    this.getIconResource(
                        "/white.jpg", squareWidth, squareHeight));
                this.cells.put(
                    cellLabel,
                    new Position(j, i)); // Store the label and its position
                gridpanel.add(
                    cellLabel); // Add the label to the grid panel
            }
        }
        this.add(gridpanel, BorderLayout.CENTER);

        healthPanel.setLayout(
            new BoxLayout(
                healthPanel, BoxLayout.Y_AXIS));

        this.playerHealtBar = new JProgressBar(0, this.maxPlayerHealth);
        this.playerHealtBar.setValue(this.maxPlayerHealth);
        this.playerHealtBar.setStringPainted(true);
        this.playerHealtBar.setForeground(Color.GREEN);

        this.playerStaminaBar =
            new JProgressBar(
                0, this.maxPlayerHealth); // Set max from model later
        this.playerStaminaBar.setValue(
            this.maxPlayerHealth); // Set value from model later
        this.playerStaminaBar.setStringPainted(true);
        this.playerStaminaBar.setForeground(Color.CYAN); // Light Blue
        this.playerStaminaBar.setPreferredSize(
            new Dimension(
                barWidth * size, barHeight)); // Match others

        this.enemyHealthBar = new JProgressBar(0, this.maxEnemyHealth);
        this.enemyHealthBar.setValue(this.maxEnemyHealth);
        this.enemyHealthBar.setStringPainted(true);
        this.enemyHealthBar.setForeground(Color.RED);
        this.enemyHealthBar.setPreferredSize(
            new Dimension(barWidth * size, barHeight));

        healthPanel.add(new JLabel("Player Health"));
        healthPanel.add(this.playerHealtBar);
        healthPanel.add(Box.createVerticalStrut(spaceBuffer));
        healthPanel.add(new JLabel("Player Stamina: "));
        healthPanel.add(playerStaminaBar);
        healthPanel.add(new JLabel("Enemy Health"));
        healthPanel.add(enemyHealthBar);

        this.add(healthPanel, BorderLayout.NORTH);

        this.cardLayout = new CardLayout();
        this.buttonPanelContainer = new JPanel(cardLayout);

        this.originalButtonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER));
        this.attackButton = this.createButton(
            "Attack", this.buttonHeight, this.buttonWidth);
        this.bagButton = this.createButton(
            "Bag", this.buttonHeight, this.buttonWidth);
        this.runButton = this.createButton(
            "Run", this.buttonHeight, this.buttonWidth);
        this.infoButton = this.createButton(
            "Info", this.buttonHeight, this.buttonWidth);

        this.originalButtonPanel.add(attackButton);
        this.originalButtonPanel.add(bagButton);
        this.originalButtonPanel.add(runButton);
        this.originalButtonPanel.add(infoButton);

        this.attackButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.physicalAttackButton = this.createButton(
            "Physical Attack", this.buttonHeight, this.buttonWidth);
        this.longRangeButton = this.createButton(
            "Long Range", this.buttonHeight, this.buttonWidth);
        new JButton("Long Range");
        this.poisonButton = this.createButton(
            "Poison", this.buttonHeight, this.buttonWidth);
        this.backAttackButton = this.createButton(
            "Back", this.buttonHeight, this.buttonWidth);
        this.attackButtonPanel.add(physicalAttackButton);
        this.attackButtonPanel.add(longRangeButton);
        this.attackButtonPanel.add(poisonButton);
        this.attackButtonPanel.add(backAttackButton);

        this.bagButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackBuffButton = createButton(
            "Attack Buff", this.buttonHeight, this.buttonWidth);
        this.curePoisonButton = createButton(
            "Cure poison", this.buttonHeight, this.buttonWidth);
        this.healButton = createButton(
            "Heal", this.buttonHeight, this.buttonWidth);
        this.backButton = createButton(
            "Back", this.buttonHeight, this.buttonWidth);

        this.bagButtonPanel.add(attackBuffButton);
        this.bagButtonPanel.add(curePoisonButton);
        this.bagButtonPanel.add(healButton);
        this.bagButtonPanel.add(backButton);

        this.buttonPanelContainer.add(originalButtonPanel, "originalButtons");
        this.buttonPanelContainer.add(attackButtonPanel, "attackOptions");
        this.buttonPanelContainer.add(bagButtonPanel, "bagButtons");

        this.infoLabel = new JLabel("Combat Started!", SwingConstants.CENTER);
        this.infoLabel.setPreferredSize(
            new Dimension(
                INFO_LENGTH_MUTATOR * size,
                DEFAULT_INFO_HEIGHT
            )
        );

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(buttonPanelContainer);
        southPanel.add(infoLabel);
        this.add(southPanel, BorderLayout.SOUTH);

        this.showMainMenu();
    }

    /**
     * Sets the maximum value for the health bars.
     *
     * @param max the maximum health value for both player and enemy
     */
    public final void setPlayerHealthBarMax(final int max) {
        this.playerHealtBar.setMaximum(max);
    }

    /**
     * Sets the maximum value for the enemy health bar.
     *
     * @param max maximum health value for the enemy
     */
    public final void setEnemyHealthBarMax(final int max) {
        this.enemyHealthBar.setMaximum(max);
    }

    /**
     * Updates the player's health bar with the current value.
     *
     * @param value the current health value of the player
     */
    @Override
    public final void updatePlayerHealth(final int value) {
        this.playerHealtBar.setValue(value);
        this.playerHealtBar.setString(
            "Player: " + value + SEPARATOR_BAR + this.playerHealtBar.getMaximum());
    }

    /**
     * Updates the player's stamina bar with the current value.
     *
     * @param value the current stamina value of the player
     */
    @Override
    public final void updatePlayerStamina(final int value) {
        this.playerStaminaBar.setValue(value);
        this.playerStaminaBar.setString(
            "Stamina: " + value + SEPARATOR_BAR + playerStaminaBar.getMaximum());
    }

    /**
     * Sets the maximum value for the player's stamina bar.
     *
     * @param max maximum stamina value for the player
     */
    public final void setPlayerMaxStaminaBar(final int max) {
        this.playerStaminaBar.setMaximum(max);
    }

    /**
     * Updates the enemy's health bar with the current value.
     *
     * @param value the current health value of the enemy
     */
    @Override
    public final void updateEnemyHealth(final int value) {
        enemyHealthBar.setValue(value);
        enemyHealthBar.setString(
            "Enemy: " + value + SEPARATOR_BAR + this.enemyHealthBar.getMaximum());
    }

    /**
     * Displays a message in the info label.
     *
     * @param text the message to display
     */
    @Override
    public final void showInfo(final String text) {
        // Use HTML to allow for multi-line messages
        infoLabel.setText("<html>" + text.replace("\n", "<br>") + "</html>");
    }

    /**
     * Clears the info label, removing any displayed messages.
     */
    @Override
    public final void clearInfo() {
        infoLabel.setText("");
    }

    /**
     * Method used to set the controller to assign ActionListeners to buttons.
     */
    @Override
    public final void setController(final CombatController combatController) {
        this.controller = combatController;
        this.attackButton.addActionListener(e -> this.controller.handleAttackMenu());
        this.bagButton.addActionListener(e -> this.controller.handleBagMenu());
        this.runButton.addActionListener(e -> this.controller.exitCombat());
        this.infoButton.addActionListener(e -> this.controller.handleInfo());
        this.physicalAttackButton.addActionListener(e -> this.controller.handlePlayerPhysicalAttack());
        this.longRangeButton.addActionListener(e -> this.controller.handlePlayerLongRangeAttack(false, true));
        this.poisonButton.addActionListener(e -> this.controller.handlePlayerLongRangeAttack(true, false));
        this.backButton.addActionListener(e -> this.controller.handleBackToMainMenu());
        this.backAttackButton.addActionListener(e -> this.controller.handleBackToMainMenu());
        this.attackBuffButton.addActionListener(e -> this.controller.handleAttackBuff());
        this.curePoisonButton.addActionListener(e -> this.controller.handleCurePoisonInput());
        this.healButton.addActionListener(e -> this.controller.handleHeal());
    }

    /**
     * Redraws the grid with the specified parameters.
     *
     * @param context the context containing necessary information for redrawing
     */
    public final void redrawGrid(final RedrawContext context) {

        for (final var entry : cells.entrySet()) {
            final JLabel cellLabel = entry.getKey();
            final Position cellPos = entry.getValue();
            Icon icon = null;

            if (context.isGameOver()) {
                if (context.getWhoDied() != null
                    && this.neighbours.deathNeighbours(
                    context.getWhoDied(), cellPos, context.getEnemyRange())) {
                    icon =
                    this.getIconResource(
                        context.getWhoDied().equals(context.getPlayer())
                        ? PURPLE : RED,
                        context.getSquareHeight(), context.getSquareWidth());
                } else if (
                    context.isDrawPlayer()
                    && neighbours.deathNeighbours(
                        context.getWhoDied(),
                        cellPos,
                        context.getEnemyRange())) {
                    icon =
                    getIconResource(
                        context.getWhoDied().equals(context.getPlayer())
                        ? PURPLE : RED,
                        context.getSquareWidth(), context.getSquareHeight());
                }
            } else if ((context.isDrawFlame()
            || context.isDrawPoison()
            || context.isDrawBossRayAttack())
                    && this.neighbours.neighbours(
                        cellPos, context.getFlame(), context.getFlameSize())) {
                icon = context.isDrawFlame()
                    ? this.getIconResource("/yellow.jpg",
                    context.getSquareWidth(), context.getSquareHeight())
                        : context.isDrawPoison()
                        ? this.getIconResource(
                            GREEN,
                            context.getSquareWidth(),
                            context.getSquareHeight())
                            : getIconResource("/purple.png",
                            context.getSquareWidth(),
                            context.getSquareHeight());
            } else if (
                context.isDrawPoisonDamage()
                && context.getWhoIsPoisoned() != null
                && entry.getValue().y() == context.getPoisonYCoord()
                && entry.getValue().x() == context.getWhoIsPoisoned().x()) {
                icon = this.getIconResource(GREEN,
                context.getSquareWidth(), context.getSquareHeight());
            } else if (
                (context.isDrawFlame() || context.isDrawPoison())
                && this.neighbours.neighbours(
                    cellPos, context.getFlame(), 0)) {
                icon = context.isDrawFlame()
                ? this.getIconResource(
                    "/yellow.jpg",
                    context.getSquareWidth(),
                    context.getSquareHeight())
                : this.getIconResource(
                    GREEN,
                    context.getSquareWidth(),
                    context.getSquareHeight());
            } else if (
                context.isDrawPlayer()
                && context.getPlayer() != null
                && this.neighbours.neighbours(
                    context.getPlayer(), cellPos, context.getPlayerRange())) {
                icon = this.getIconResource(
                    PURPLE,
                    context.getSquareWidth(),
                    context.getSquareHeight());
            } else if (
                context.isDrawEnemy()
                && context.getEnemy() != null
                && this.neighbours.neighbours(
                    context.getEnemy(), cellPos, context.getEnemyRange())) {
                icon = getIconResource(
                    RED,
                    context.getSquareWidth(),
                    context.getSquareHeight());
            } else if (
                context.isCharging()
                && this.neighbours.deathNeighbours(
                    context.getEnemy(),
                    cellPos,
                    context.getChargingCellDistance())) {
                icon = getIconResource("/purple.png",
                context.getSquareWidth(), context.getSquareHeight());
            } else {
                icon = getIconResource(
                    "/white.jpg",
                    context.getSquareWidth(),
                    context.getSquareHeight());
            }
            cellLabel.setIcon(icon);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Sets the visibility of the combat view.
     */
    public final void showAttackMenu() {
        this.cardLayout.show(buttonPanelContainer, "attackOptions");
    }

    /**
     * Sets the visibility of the bag buttons in the combat view.
     */
    public final void showMainMenu() {
        this.cardLayout.show(this.buttonPanelContainer, "originalButtons");
    }

    /**
     * Sets the visibility of the bag buttons in the combat view.
     */
    public final void showBagMenu() {
        this.cardLayout.show(this.buttonPanelContainer, "bagButtons");
    }

    /**
     * Enables all buttons in the combat view, allowing user interaction.
     */
    @Override
    public final void setAllMenusDisabled() {
        this.setPanelEnabled(this.originalButtonPanel, false);
        this.setPanelEnabled(this.attackButtonPanel, false);
    }

    /**
     * Enables all buttons in the bag button panel.
     */
    public final void setBagButtonsEnabled() {
        this.setPanelEnabled(this.bagButtonPanel, true);
    }

    /**
     * Disables all buttons in the combat view, preventing user interaction.
     */
    @Override
    public final void setAllMenusEnabled() {
        this.setPanelEnabled(this.originalButtonPanel, true);
        this.setPanelEnabled(this.attackButtonPanel, true);
    }

    /**
     * Enables a specific button in the combat view, allowing user interaction.
     *
     * @param buttonToEnable the button to enable
     */
    public final void setActionEnabled(final ActionType action, final boolean isEnabled) {
        switch (action) {
            case PHYSICAL:
                this.physicalAttackButton.setEnabled(isEnabled);
                break;
            case LONG_RANGE:
                this.longRangeButton.setEnabled(isEnabled);
                break;
            case POISON:
                this.poisonButton.setEnabled(isEnabled);
                break;
            case ATTACK_BUFF:
                this.attackBuffButton.setEnabled(isEnabled);
                break;
            case CURE_POISON:
                this.curePoisonButton.setEnabled(isEnabled);
                break;
            case HEAL:
                this.healButton.setEnabled(isEnabled);
                break;
            case RUN:
                this.runButton.setEnabled(isEnabled);
                break;
            case BACK:
                this.backAttackButton.setEnabled(isEnabled);
                this.backButton.setEnabled(isEnabled);
            default:
                break;
        }
    }

    /**
     * Disables a specific button in the combat view.
     *
     * @param buttonToDisable the button to disable
     */
    public final void setCustomButtonDisabled(final JButton buttonToDisable) {
        buttonToDisable.setEnabled(false);
    }

    private void setPanelEnabled(
        final JPanel panel,
        final boolean enablePanel) {
        panel.setEnabled(enablePanel);
        for (final Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(enablePanel);
            }
        }
    }

    /**
     * Sets the visibility of the combat view.
     */
    public final void display() {
        this.setVisible(true);
    }

    private ImageIcon getIconResource(
        final String path,
        final int width,
        final int height) {
        final URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return createDefaultIcon(width, height);
        }
    }

    /**
     * Returns the attack button for the combat view.
     *
     * @return the attack button
     */
    public final JButton getLongRangeAttackButton() {
        return this.longRangeButton;
    }

    /**
     * Returns the physical attack button for the combat view.
     *
     * @return the physical attack button
     */
    public final JButton getPoisonAttackButton() {
        return this.poisonButton;
    }

    /**
     * Returns the cure poison button for the combat view.
     *
     * @return the cure poison button
     */
    public final JButton getCurePoisonButton() {
        return this.curePoisonButton;
    }

    /**
     * Returns the attack buff button for the combat view.
     *
     * @return the attack buff button
     */
    public final JButton getAttackBuffButton() {
        return this.attackBuffButton;
    }

    /**
     * Returns the heal button for the combat view.
     *
     * @return the heal button
     */
    public final JButton getHealingButton() {
        return this.healButton;
    }

    /**
     * Returns the back button for the combat view.
     *
     * @return the back button
     */
    public final JButton getAttackBackButton() {
        return this.backAttackButton;
    }

    /**
     * Returns the bag button panel for the combat view.
     *
     * @return the bag button panel
     */
    public final JPanel getBagButtonPanel() {
        return this.bagButtonPanel;
    }

    /**
     * Returns the attack button panel for the combat view.
     *
     * @return the attack button panel
     */
    public final JPanel getAttackButtonPanel() {
        return this.attackButtonPanel;
    }

    /**
     * Returns the original button panel for the combat view.
     *
     * @return the original button panel
     */
    public final JPanel getOriginalButtonPanel() {
        return this.originalButtonPanel;
    }

    /**
     * Returns the button panel container for the combat view.
     *
     * @return the button panel container
     */
    public final JPanel getButtonPanelContainer() {
        return this.buttonPanelContainer;
    }

    /**
     * Returns the player health bar for the combat view.
     *
     * @return the player health bar
     */
    public final JProgressBar getPlayerHealthBar() {
        return this.playerHealtBar;
    }

    /**
     * Returns the enemy health bar for the combat view.
     *
     * @return the enemy health bar
     */
    public final JProgressBar getEnemyHealthBar() {
        return this.enemyHealthBar;
    }

    private ImageIcon createDefaultIcon(final int width, final int height) {
        final BufferedImage image =
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = image.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return new ImageIcon(image);
    }

    private JButton createButton(
        final String name,
        final int height,
        final int length) {
        final JButton tempButton = new JButton(name);
        tempButton.setPreferredSize(new Dimension(length, height));
        return tempButton;
    }

    /**
     * Adds an action listener to the attack button.
     */
    public final void addAttackButtonListener() {
        this.attackButton.addActionListener(e -> this.controller.handleAttackMenu());
    }

    /**
     * Adds an action listener to the bag button.
     */
    public final void addBagButtonListener() {
        this.bagButton.addActionListener(e -> this.controller.handleBagMenu());
    }

    /**
     * Adds an action listener to the run button.
     */
    public final void addRunButtonListener() {
        this.runButton.addActionListener(e -> this.controller.exitCombat());
    }

    /**
     * Adds an action listener to the info button.
     */
    public final void addInfoButtonListener() {
        this.infoButton.addActionListener(e -> this.controller.handleInfo());
    }

    /**
     * Adds an action listener to the physical attack button.
     */
    public final void addPhysicalButtonListener() {
        this.physicalAttackButton.addActionListener(e -> this.controller.handlePlayerPhysicalAttack());
    }

    /**
     * Adds an action listener to the long-range attack button.
     */
    public final void addLongRangeButtonListener() {
        this.longRangeButton.addActionListener(e -> this.controller.handlePlayerLongRangeAttack(false, true));
    }

    /**
     * Adds an action listener to the poison button.
     */
    public final void addPoisonButtonListener() {
        this.poisonButton.addActionListener(e -> this.controller.handlePlayerLongRangeAttack(true, false));
    }

    /**
     * Adds an action listener to the back button.
     */
    public final void addBackButtonListener() {
        this.backButton.addActionListener(e -> this.controller.handleBackToMainMenu());
        this.backAttackButton.addActionListener(e -> this.controller.handleBackToMainMenu());
    }

    /**
     * Adds an action listener to the attack buff button.
     */
    public final void addAttackBuffButtonListener() {
        this.attackBuffButton.addActionListener(e -> this.controller.handleAttackBuff());
    }

    /**
     * Adds an action listener to the cure button.
     */
    public final void addCurePoisonButtonListener() {
        this.curePoisonButton.addActionListener(e -> this.controller.handleCurePoisonInput());
    }

    /**
     * Adds an action listener to the heal button.
     */
    public final void addHealButtonListener() {
        this.healButton.addActionListener(e -> this.controller.handleHeal());
    }

    /**
     * Displays the game over panel with a restart option.
     *
     * @param onRestart the runnable when the restart is clicked
     */
    @Override
    public void showGameOver(final Runnable onRestart) {
        final JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame == null) {
            return;
        }
        final GameOverPanel panel = new GameOverPanel(() -> {
            if (onRestart != null) {
                onRestart.run();   // <-- esegue davvero il restart
            }
        });
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Closes the view.
     */
    public void close() {
        final java.awt.Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) {
            w.dispose();
        }
    }

    @Override
    public CombatView getViewPanel() {
        return this;
    }

}
