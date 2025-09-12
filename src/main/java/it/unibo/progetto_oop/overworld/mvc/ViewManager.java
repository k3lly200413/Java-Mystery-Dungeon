package it.unibo.progetto_oop.overworld.mvc;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.InventoryView;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatControllerApi;
import it.unibo.progetto_oop.combat.win_view.WinPanel;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.playground.view.game_start.GameStartView;
import it.unibo.progetto_oop.overworld.playground.view.playground_view.ImplMapView;

public final class ViewManager {
    /**
     * start game card identifier.
     */
    private static final String START_GAME = "START GAME";

    /**
     * inventory card identifier.
     */
    public static final String INVENTORY_CARD = "INVENTORY";

    /**
     * overworld card identifier.
     */
    public static final String OVERWORLD_CARD = "OVERWORLD";

    /**
     * combat card identifier.
     */
    public static final String COMBAT_CARD = "COMBAT";

    /**
     * game over card identifier.
     */
    private static final String GAME_OVER = "GAME OVER";

    /**
     * win card identifier.
     */
    private static final String WIN = "WIN";

    /**
     * Preferred width for the game window.
     */
    private static final int PREFERRED_WIDTH = 1000;

    /**
     * Preferred height for the game window.
     */
    private static final int PREFERRED_HEIGHT = 700;

    /**
     * the card layout to switch between views.
     */
    private CardLayout cardLayout;

    /**
     * Minimum width for the game window.
     */
    private static final int MINIMUM_WIDTH = 960;

    /**
     * Minimum height for the game window.
     */
    private static final int MINIMUM_HEIGHT = 640;

    /**
     * the main panel that holds the different views.
     */
    private JPanel mainCardPanel;

    /**
     * inventory view.
     */
    private InventoryView invView;

    /**
     * The playground.
     */
    private ImplMapView playGroundView;

    /**
     * The start view for the game.
     */
    private GameStartView startView;

    /**
     * The controller for managing combat logic and interactions.
     */
    private CombatControllerApi combatController;

    /** Game over panel. */
    private GameOverPanel gameOverPanel;

    /** Win panel. */
    private WinPanel winPanel;

    /**
     * Method to start the view manager with the initial start view.
     * 
     * @param initialStartView the start view to display
     */
    public void start(final GameStartView initialStartView) {
        // Setup CardLayout and main panel
        this.cardLayout = new CardLayout();
        this.mainCardPanel = new JPanel(cardLayout);
        JFrame frame = new JFrame("JavaMysteryDungeon");
        this.startView = initialStartView;
        frame.setPreferredSize(
                new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        this.mainCardPanel.setMinimumSize(
                new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // first card
        this.mainCardPanel.add(this.startView, START_GAME);
        frame.setContentPane(this.mainCardPanel);
        frame.pack();
        frame.setVisible(true);

        // Mostro start game come default
        this.cardLayout.show(this.mainCardPanel, START_GAME);
    }

    /**
     * Displays the overworld view.
     */
    public void showOverworld() {
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD);
    }

    /**
     * Sets the playground view.
     * 
     * @param newPlayGroundView the playground view to set
     */
    public void setPlayGroundView(final ImplMapView newPlayGroundView) {
        this.playGroundView = newPlayGroundView;
        this.mainCardPanel.add(this.playGroundView, OVERWORLD_CARD);
    }

    /**
     * Method to set the inventory view.
     * 
     * @param newInvView the inventory view to set
     */
    public void setInventoryView(final InventoryView newInvView) {
        this.invView = newInvView;
        this.mainCardPanel.add(this.invView, INVENTORY_CARD);
    }

    /**
     * Sets the combat controller.
     *
     * Wraps the full controller in a small adapter exposing only the required API so
     * ViewManager does not keep a concrete mutable controller reference.
     *
     * @param fullController the concrete CombatController to adapt
     */
    public void setCombatController(final CombatController fullController) {
        this.combatController = new CombatControllerApi() {
            @Override
            public void setEncounteredEnemy(final Enemy enemy) {
                fullController.setEncounteredEnemy(enemy);
            }

            @Override
            public void setEnemyHp(final int currentHp, final int maxHp) {
                fullController.getModel().setEnemyCurrentHp(currentHp);
                fullController.getModel().setEnemyMaxHp(maxHp);
            }

            @Override
            public void resetForNewCombat() {
                fullController.resetForNewCombat();
            }

            @Override
            public void redrawView() {
                fullController.redrawView();
            }

            @Override
            public JPanel getViewPanel() {
                return fullController.getViewApi().getViewPanel();
            }
        };
        this.mainCardPanel.add(this.combatController.getViewPanel(), COMBAT_CARD);
    }

    public void showCombat(final Enemy encounteredEnemy) {
        this.combatController.setEncounteredEnemy(encounteredEnemy);
        this.combatController.setEnemyHp(encounteredEnemy.getCurrentHp(), encounteredEnemy.getMaxHp());
        this.combatController.resetForNewCombat();
        this.combatController.redrawView();
        this.cardLayout.show(this.mainCardPanel, COMBAT_CARD);
    }

    /**
     * Method to show the inventory view.
     * 
     * @param inventory the inventory to display
     */
    public void showInventory(final Inventory inventory) {
        if (this.invView == null) { // prima volta
            this.setInventoryView(new InventoryView(inventory, this));
        } else { // aggiorna la view esistente
            this.invView.updateInventoryModel(inventory);
            this.invView.refreshView();
        }
        this.cardLayout.show(this.mainCardPanel, INVENTORY_CARD);
    }

    // public void showCombat(Enemy encounteredEnemy) {
    //     this.combatController.setEncounteredEnemy(encounteredEnemy);
    //     this.combatController.getModel().setEnemyCurrentHp(encounteredEnemy.getCurrentHp());
    //     this.combatController.getModel().setEnemyMaxHp(encounteredEnemy.getMaxHp());
    //     this.combatController.resetForNewCombat();
    //     this.combatController.redrawView();
    //     this.cardLayout.show(this.mainCardPanel, COMBAT_CARD);
    // }

    public void setGameOverPanel(final GameOverPanel newGameOverPanel) {
        this.gameOverPanel = newGameOverPanel;
        this.mainCardPanel.add(this.gameOverPanel, GAME_OVER);
    }

    public void showGameOver() {
        this.cardLayout.show(this.mainCardPanel, GAME_OVER);
    }

    public void setWinPanel(final WinPanel newWinPanel) {
        this.winPanel = newWinPanel;
        this.mainCardPanel.add(this.winPanel, WIN);
    }

    public void showWin() {
        this.cardLayout.show(this.mainCardPanel, WIN);
    }
}
