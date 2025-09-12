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
import java.io.IOException;
import java.io.ObjectInputStream;
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
import it.unibo.progetto_oop.combat.mvc_pattern.WrapLayout;

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
    private static final String LINK = "/Screenshot 2025-09-12 192147.png";
    /**
     * Colour of charging boss.
     */
    private static final String PURPLE = "/purple.png";
    /**
     * Colour of player.
     */
    private static final String GREEN = "/green.jpg";
    /**
     * Colour of enemy Boss.
     */
    private static final String RED = "/red.jpg";
    /**
     * Colour of enemy.
     */
    private static final String GENGAR = "/Screenshot 2025-09-12 192332.png";
    /**
     * Colour of flame attack.
     */
    private static final String YELLOW = "/yellow.jpg";
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
    private transient Map<JLabel, Position> cells;
    /**
     * Colour of enemy or Boss.
     */
    private String enemyColour;
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
     * Button for displaying info in the combat view.
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
    private transient Neighbours neighbours;
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
        this.buttonHeight = buttonHeightToAssign;
        this.buttonWidth = buttonWidthToAssign;
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

    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.neighbours = new Neighbours();
        this.cells = new HashMap<>();
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
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
                cellLabel.setIcon(
                    this.getIconResource(
                        "/white.jpg", squareWidth, squareHeight));
                this.cells.put(
                    cellLabel,
                    new Position(j, i));
                gridpanel.add(
                    cellLabel);
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
                0, this.maxPlayerHealth);
        this.playerStaminaBar.setValue(
            this.maxPlayerHealth);
        this.playerStaminaBar.setStringPainted(true);
        this.playerStaminaBar.setForeground(Color.CYAN);
        this.playerStaminaBar.setPreferredSize(
            new Dimension(
                barWidth * size, barHeight));

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
            new WrapLayout(FlowLayout.CENTER));
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
    @Override
    public final void setPlayerHealthBarMax(final int max) {
        this.playerHealtBar.setMaximum(max);
    }

    /**
     * Sets the maximum value for the enemy health bar.
     *
     * @param max maximum health value for the enemy
     */
    @Override
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
    @Override
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
        final CombatController ctrl = combatController;
        this.attackButton.addActionListener(e -> ctrl.handleAttackMenu());
        this.bagButton.addActionListener(e -> ctrl.handleBagMenu());
        this.runButton.addActionListener(e -> ctrl.exitCombat());
        this.infoButton.addActionListener(e -> ctrl.handleInfo());
        this.physicalAttackButton.addActionListener(e -> ctrl.handlePlayerPhysicalAttack());
        this.longRangeButton.addActionListener(e -> ctrl.handlePlayerLongRangeAttack(false, true));
        this.poisonButton.addActionListener(e -> ctrl.handlePlayerLongRangeAttack(true, false));
        this.backButton.addActionListener(e -> ctrl.handleBackToMainMenu());
        this.backAttackButton.addActionListener(e -> ctrl.handleBackToMainMenu());
        this.attackBuffButton.addActionListener(e -> ctrl.handleAttackBuff());
        this.curePoisonButton.addActionListener(e -> ctrl.handleCurePoisonInput());
        this.healButton.addActionListener(e -> ctrl.handleHeal());
    }

    /**
     * GENGARraws the grid with the specified parameters.
     *
     * @param context the context containing necessary information for GENGARrawing
     */
    @Override
    public final void updateDisplay(final RedrawContext context) {
        if (context.isBoss()) {
            this.enemyColour = RED;
        } else {
            this.enemyColour = GENGAR;
        }
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
                        ? LINK : enemyColour,
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
                        ? LINK : enemyColour,
                        context.getSquareWidth(), context.getSquareHeight());
                }
            } else if ((context.isDrawFlame()
            || context.isDrawPoison())
                    && this.neighbours.neighbours(
                        cellPos, context.getFlame(), context.getFlameSize())) {
                icon = context.isDrawFlame()
                    ? this.getIconResource(YELLOW,
                    context.getSquareWidth(), context.getSquareHeight())
                        : getIconResource(
                            "/green.jpg",
                            context.getSquareWidth(),
                            context.getSquareHeight());
            } else if (context.isDrawBossRayAttack()
                && this.neighbours.neighbours(
                    cellPos, context.getFlame(), 1)) {
                        icon = this.getIconResource(
                            PURPLE, 
                            context.getSquareWidth(), 
                            context.getSquareHeight()
                        );
            } else if (
                context.isDrawPoisonDamage()
                && context.getWhoIsPoisoned() != null
                && entry.getValue().y() == context.getPoisonYCoord()
                && entry.getValue().x() == context.getWhoIsPoisoned().x()) {
                    icon = this.getIconResource(GREEN,
                    context.getSquareWidth(), context.getSquareHeight());
            } else if (
                context.isDrawPlayer()
                && context.getPlayer() != null
                && this.neighbours.neighbours(
                    context.getPlayer(), cellPos, context.getPlayerRange())) {
                icon = this.getIconResource(
                    LINK,
                    context.getSquareWidth(),
                    context.getSquareHeight());
            } else if (
                context.isDrawEnemy()
                && context.getEnemy() != null
                && this.neighbours.neighbours(
                    context.getEnemy(), cellPos, context.getEnemyRange())) {
                icon = getIconResource(
                    enemyColour,
                    context.getSquareWidth(),
                    context.getSquareHeight());
            } else if (
                context.isCharging()
                && this.neighbours.deathNeighbours(
                    context.getEnemy(),
                    cellPos,
                    context.getChargingCellDistance())) {
                icon = getIconResource(PURPLE,
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
    @Override
    public final void showAttackMenu() {
        this.cardLayout.show(buttonPanelContainer, "attackOptions");
    }

    /**
     * Sets the visibility of the bag buttons in the combat view.
     */
    @Override
    public final void showMainMenu() {
        this.cardLayout.show(this.buttonPanelContainer, "originalButtons");
    }

    /**
     * Sets the visibility of the bag buttons in the combat view.
     */
    @Override
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
     * @param action the action type corresponding to the button to enable
     * @param isEnabled true to enable the button, false to disable it
     */
    @Override
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
    @Override
    public final void display() {
        this.setVisible(true);
    }

    private ImageIcon getIconResource(
        final String path,
        final int width,
        final int height) {
        final URL imgURL = CombatView.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Icon not found: " + path);
            return createDefaultIcon(width, height);
        }
    }

    /**
     * Returns the attack button for the combat view.
     *
     * @return a defensive copy of the long range attack button, or null if not initialized
     */
    public final JButton getLongRangeAttackButton() {
        return copyButton(this.longRangeButton);
    }

    /**
     * Returns the poison attack button.
     *
     * @return a copy of the poison button (may be null if view not initialized)
     */
    public final JButton getPoisonAttackButton() {
        if (this.poisonButton == null) {
            return null;
        }
        final JButton copy = new JButton(this.poisonButton.getText());
        copy.setEnabled(this.poisonButton.isEnabled());
        copy.setPreferredSize(this.poisonButton.getPreferredSize());
        copy.setToolTipText(this.poisonButton.getToolTipText());
        copy.setIcon(this.poisonButton.getIcon());
        return copy;
    }

    /**
     * Returns the cure poison button for the combat view.
     *
     * @return a defensive copy of the cure poison button, or null if not initialized
     */
    public final JButton getCurePoisonButton() {
        return copyButton(this.curePoisonButton);
    }

    /**
     * Returns the attack buff button for the combat view.
     *
     * @return a defensive copy of the attack buff button, or null if not initialized
     */
    public final JButton getAttackBuffButton() {
        return copyButton(this.attackBuffButton);
    }

    /**
     * Returns the heal button for the combat view.
     *
     * @return a defensive copy of the heal button, or null if not initialized
     */
    public final JButton getHealingButton() {
        return copyButton(this.healButton);
    }

    /**
     * Returns the back button for the combat view.
     *
     * @return a defensive copy of the back attack button, or null if not initialized
     */
    public final JButton getAttackBackButton() {
        return copyButton(this.backAttackButton);
    }

    /**
     * Returns the bag button panel for the combat view.
     *
     * @return a defensive copy of the bag button panel
     */
    public final JPanel getBagButtonPanel() {
        return copyPanel(this.bagButtonPanel);
    }

    /**
     * Returns the attack button panel for the combat view.
     *
     * @return a defensive copy of the attack button panel
     */
    public final JPanel getAttackButtonPanel() {
        return copyPanel(this.attackButtonPanel);
    }

    /**
     * Returns the original button panel for the combat view.
     *
     * @return a defensive copy of the original button panel
     */
    public final JPanel getOriginalButtonPanel() {
        return copyPanel(this.originalButtonPanel);
    }

    /**
     * Returns the button panel container for the combat view.
     *
     * @return a defensive copy of the button panel container
     */
    public final JPanel getButtonPanelContainer() {
        return copyPanel(this.buttonPanelContainer);
    }

    /**
     * Returns the player health bar for the combat view.
     *
     * @return a defensive copy of the player health bar
     */
    public final JProgressBar getPlayerHealthBar() {
        return copyProgressBar(this.playerHealtBar);
    }

    private JProgressBar copyProgressBar(final JProgressBar src) {
        if (src == null) {
            return null;
        }
        final JProgressBar copy = new JProgressBar(src.getMinimum(), src.getMaximum());
        copy.setValue(src.getValue());
        copy.setStringPainted(src.isStringPainted());
        copy.setString(src.getString());
        copy.setPreferredSize(src.getPreferredSize());
        copy.setForeground(src.getForeground());
        copy.setBackground(src.getBackground());
        copy.setBorder(src.getBorder());
        copy.setOrientation(src.getOrientation());
        copy.setIndeterminate(src.isIndeterminate());
        return copy;
    }

    /**
     * Returns a defensive copy of the enemy health bar.
     *
     * @return a copy of the internal enemy health JProgressBar, or null if not initialized
     */
    public final JProgressBar getEnemyHealthBar() {
        return copyProgressBar(this.enemyHealthBar);
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

    private JButton copyButton(final JButton src) {
        if (src == null) {
            return null;
        }
        final JButton copy = new JButton(src.getText());
        copy.setEnabled(src.isEnabled());
        copy.setPreferredSize(src.getPreferredSize());
        copy.setToolTipText(src.getToolTipText());
        copy.setIcon(src.getIcon());
        return copy;
    }

    private JPanel copyPanel(final JPanel src) {
        if (src == null) {
            return null;
        }
        final JPanel copy = new JPanel(src.getLayout());
        for (final Component c : src.getComponents()) {
            if (c instanceof JButton) {
                copy.add(copyButton((JButton) c));
            } else {
                // add non-button components directly if safe (labels, struts...). Adjust if needed.
                copy.add(c);
            }
        }
        return copy;
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
                onRestart.run();
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

    /**
     * Returns the main panel of the combat view.
     */
    @Override
    public JPanel getViewPanel() {
        return this;
    }

    /**
     * Returns a defensive copy of the info label to avoid exposing internal state.
     * Prefer using showInfo/clearInfo for reading/modifying the info text.
     *
     * @return a copy of the internal info JLabel, or null if not initialized
     */
    public JLabel getInfoLabel() {
        if (this.infoLabel == null) {
            return null;
        }
        final JLabel copy = new JLabel(
            this.infoLabel.getText(),
            this.infoLabel.getIcon(),
            this.infoLabel.getHorizontalAlignment()
        );
        copy.setPreferredSize(this.infoLabel.getPreferredSize());
        copy.setToolTipText(this.infoLabel.getToolTipText());
        copy.setOpaque(this.infoLabel.isOpaque());
        copy.setHorizontalAlignment(this.infoLabel.getHorizontalAlignment());
        copy.setVerticalAlignment(this.infoLabel.getVerticalAlignment());
        return copy;
    }

}
