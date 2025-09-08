package it.unibo.progetto_oop.combat.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.progetto_oop.overworld.mvc.ViewManager;

public class InventoryView extends JPanel {
    /**
     * Number of cells in the viewport width-wise.
     */
    private static final int VIEWPORT_WIDTH_CELLS = 13;

    /**
     * Number of cells in the viewport height-wise.
     */
    private static final int VIEWPORT_HEIGHT_CELLS = 13;

    /**
     * Preferred cell width in pixels.
     */
    private static final int PREFERRED_CELL_WIDTH = 70;

    /**
     * Preferred cell height in pixels.
     */
    private static final int PREFERRED_CELL_HEIGHT = 60;

    /**
     * status label width.
     */
    private static final int STATUS_LABEL_W = 100;

    /**
     * status label height.
     */
    private static final int STATUS_LABEL_H = 80;

    /**
     * Floor color (background of the grid).
     */
    private static final Color FLOOR_COLOR = Color.LIGHT_GRAY;

    /**
     * Grid line color.
     */
    private static final Color GRID_LINE_COLOR = Color.GRAY;

    /**
     * Color for item slot 1.
     */
    private static final Color ITEM_SLOT_1_COLOR = Color.BLACK;

    /**
     * Color for item slot 2.
     */
    private static final Color ITEM_SLOT_2_COLOR = Color.BLUE;

    /**
     * Color for item slot 3.
     */
    private static final Color ITEM_SLOT_3_COLOR = Color.CYAN;

    /** Default background color (R component). */
    private static final int BG_COLOR_R = 235;

    /** Default background color (G component). */
    private static final int BG_COLOR_G = 239;

    /** Default background color (B component). */
    private static final int BG_COLOR_B = 245;

    /**
    * Border line thickness in pixels.
    */
    private static final int BORDER_THICKNESS = 2;

    /**
    * Top and bottom padding in pixels.
    */
    private static final int BORDER_PADDING_VERTICAL = 6;

    /**
    * Left and right padding in pixels.
    */
    private static final int BORDER_PADDING_HORIZONTAL = 10;

    /**
     * view manager to switch back to overworld.
     */
    private final ViewManager viewManager;

    /**
     * the inventory this view is displaying.
     */
    private Inventory inventory;

    /**
     * Panel containing the grid of item buttons.
     */
    private JPanel gridPanel;

    /**
     * Status label at the bottom.
     */
    private JLabel bottomStatusLabel;

    /**
     * Back to game button.
     */
    private JButton backButton;


    /**
     * constructor for the inventory view.
     * @param initialInventory the inventory this view is referring
     * @param newViewManager the view manager
     */
    public InventoryView(final Inventory initialInventory,
    final ViewManager newViewManager) {
        this.inventory = initialInventory;
        this.viewManager = newViewManager;

        // Layout Panel
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(FLOOR_COLOR);

        // Grid Panel
        this.gridPanel = new JPanel(
            new GridLayout(VIEWPORT_HEIGHT_CELLS, VIEWPORT_WIDTH_CELLS, 1, 1));
        this.gridPanel.setBackground(GRID_LINE_COLOR);
        this.add(this.gridPanel, BorderLayout.CENTER);

        // Create status area
        createStatusArea();

        // Initial population
        populateGrid();

        // Set preferred size
        calculateAndSetPreferredSize();
    }

    /**
     * Creates and configures the status area at the bottom of the inventory.
     */
    private void createStatusArea() {
        JPanel statusAreaPanel = new JPanel(new BorderLayout(0, 5));
        statusAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Status label
        this.bottomStatusLabel = new JLabel(
            "<html><i>Click an item...</i></html>",
            SwingConstants.CENTER);
        this.bottomStatusLabel.setOpaque(true);
        this.bottomStatusLabel.setBackground(Color.WHITE);
        this.bottomStatusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        this.bottomStatusLabel.setPreferredSize(
            new Dimension(STATUS_LABEL_W, STATUS_LABEL_H));
        statusAreaPanel.add(this.bottomStatusLabel, BorderLayout.CENTER);

        // Back button
        createBackButton(statusAreaPanel);

        this.add(statusAreaPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates and configures the back button.
     */
    private void createBackButton(JPanel statusAreaPanel) {
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.backButton = new JButton("Back to Game");
        this.backButton.addActionListener(e -> {
            if (this.viewManager != null) {
                this.viewManager.showOverworld();
            }
        });
        backButtonPanel.add(this.backButton);
        statusAreaPanel.add(backButtonPanel, BorderLayout.SOUTH);
    }

    /**
     * Calculates and sets the preferred size for the entire panel.
     */
    private void calculateAndSetPreferredSize() {
        int totalPreferredWidth = PREFERRED_CELL_WIDTH * VIEWPORT_WIDTH_CELLS;
        int gridActualHeight = PREFERRED_CELL_HEIGHT * VIEWPORT_HEIGHT_CELLS
            + VIEWPORT_HEIGHT_CELLS; // Add space for grid lines
        int statusAreaHeight = bottomStatusLabel.getPreferredSize().height
            + backButton.getPreferredSize().height + 20; // Extra padding
        this.setPreferredSize(
            new Dimension(totalPreferredWidth, gridActualHeight + statusAreaHeight));
    }

    /**
     * Clears and re-populates the grid with improved logic for multiple potions.
     */
    private void populateGrid() {
        if (this.gridPanel == null || this.inventory == null) {
            System.err.println("InventoryView: Cannot populate grid - null components");
            return;
        }

        this.gridPanel.removeAll();

        // Group potions by type and get sorted list
        Map<String, PotionInfo> potionTypes = groupPotionsByType();
        List<PotionInfo> sortedPotions = new ArrayList<>(potionTypes.values());
        
        System.out.println("DEBUG populateGrid: Found " + sortedPotions.size() + " potion types");

        // Calculate grid positions for potion display area
        GridPositions positions = calculateGridPositions();

        // Populate grid cells
        populateGridCells(sortedPotions, positions);

        // Finalize grid updates
        this.gridPanel.revalidate();
        this.gridPanel.repaint();
    }

    /**
     * Groups potions by type and calculates quantities.
     */
    private Map<String, PotionInfo> groupPotionsByType() {
        Map<String, PotionInfo> potionTypes = new LinkedHashMap<>();
        
        for (Map.Entry<Item, Integer> entry : this.inventory.getFullInventory().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            
            // Assume all items are potions for now, or add instanceof check
            String potionType = item.getName();
            
            potionTypes.merge(potionType, 
                new PotionInfo(item, quantity, potionType),
                (existing, newInfo) -> existing.combineWith(newInfo));
        }
        
        return potionTypes;
    }


    /**
     * Calculates key positions in the grid for item placement.
     */
    private GridPositions calculateGridPositions() {
        int middleY = (VIEWPORT_HEIGHT_CELLS - 1) / 2;
        int middleX = (VIEWPORT_WIDTH_CELLS - 1) / 2;
        int quarterX = (VIEWPORT_WIDTH_CELLS - 1) / 4;
        int threeQuarterX = middleX + (middleX - quarterX);
        
        return new GridPositions(middleY, middleX, quarterX, threeQuarterX);
    }

    /**
     * Populates individual grid cells with potions or empty slots.
     */
    private void populateGridCells(List<PotionInfo> potions, GridPositions positions) {
        for (int y = 0; y < VIEWPORT_HEIGHT_CELLS; y++) {
            for (int x = 0; x < VIEWPORT_WIDTH_CELLS; x++) {
                JButton cellButton = createCellButton(x, y, potions, positions);
                cellButton.setPreferredSize(
                    new Dimension(PREFERRED_CELL_WIDTH, PREFERRED_CELL_HEIGHT));
                this.gridPanel.add(cellButton);
            }
        }
    }

    /**
     * Creates a single cell button based on position and available potions.
     */
    private JButton createCellButton(int x, int y, List<PotionInfo> potions, GridPositions positions) {
        PotionInfo potionAtPosition = getPotionAtPosition(x, y, potions, positions);
        
        if (potionAtPosition != null) {
            return createPotionButton(potionAtPosition);
        } else {
            return createEmptyCell();
        }
    }

    /**
     * Determines which potion should be displayed at the given grid position.
     */
    private PotionInfo getPotionAtPosition(int x, int y, List<PotionInfo> potions, GridPositions positions) {
        if (y != positions.middleY) {
            return null; // Only middle row has potions
        }
        
        if (x == positions.quarterX && potions.size() > 0) {
            return potions.get(0);
        } else if (x == positions.middleX && potions.size() > 1) {
            return potions.get(1);
        } else if (x == positions.threeQuarterX && potions.size() > 2) {
            return potions.get(2);
        }
        
        return null;
    }

    /**
     * Creates a button for a potion with quantity display.
     */
    private JButton createPotionButton(PotionInfo potionInfo) {
        Color potionColor = getPotionColor(potionInfo.getType());
        String description = formatPotionDescription(potionInfo);
        
        System.out.println("Creating potion button: " + potionInfo.getItem().getName() 
            + " (x" + potionInfo.getQuantity() + ")");
        
        return createItemButton(
            potionInfo.getItem().getName() + " (x" + potionInfo.getQuantity() + ")",
            potionColor,
            description
        );
    }

    /**
     * Creates an empty floor cell.
     */
    private JButton createEmptyCell() {
        JButton cellButton = new JButton();
        cellButton.setEnabled(false);
        cellButton.setOpaque(true);
        cellButton.setBackground(FLOOR_COLOR);
        cellButton.setBorder(
            BorderFactory.createLineBorder(GRID_LINE_COLOR.darker()));
        return cellButton;
    }

    /**
     * Gets the display color for a potion type.
     */
    private Color getPotionColor(String potionType) {
        switch (potionType.toLowerCase()) {
            case "health": return Color.RED;
            case "mana": return Color.BLUE;
            case "speed": return Color.YELLOW;
            case "strength": return Color.ORANGE;
            default: return ITEM_SLOT_1_COLOR;
        }
    }

    /**
     * Formats the description for a potion including quantity info.
     */
    private String formatPotionDescription(PotionInfo potionInfo) {
        String baseDesc = potionInfo.getItem().getDescription().replace("\n", "<br>");
        return String.format("<html>%s<br><b>Quantity: %d</b></html>", 
            baseDesc, potionInfo.getQuantity());
    }

    // Helper classes
    private static class GridPositions {
        final int middleY, middleX, quarterX, threeQuarterX;
        
        GridPositions(int middleY, int middleX, int quarterX, int threeQuarterX) {
            this.middleY = middleY;
            this.middleX = middleX;
            this.quarterX = quarterX;
            this.threeQuarterX = threeQuarterX;
        }
    }

    private static class PotionInfo {
        private final Item item;
        private int quantity;
        private final String type;
        
        PotionInfo(Item item, int quantity, String type) {
            this.item = item;
            this.quantity = quantity;
            this.type = type;
        }
        
        PotionInfo combineWith(PotionInfo other) {
            return new PotionInfo(this.item, this.quantity + other.quantity, this.type);
        }

    /**
    * Creates a button for an item with consistent style and behavior.
    * @param text Button text (item name)
    * @param color Color to highlight the slot (border)
    * @param htmlDescriptionActionCommand HTML description
    * to display/use as an action command
    * @return configured JButton
    */
    private JButton createItemButton(final String text, final Color color,
            final String htmlDescriptionActionCommand) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(
            new Color(BG_COLOR_R, BG_COLOR_G, BG_COLOR_B));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, BORDER_THICKNESS),
            BorderFactory.createEmptyBorder(
                BORDER_PADDING_VERTICAL, BORDER_PADDING_HORIZONTAL,
                BORDER_PADDING_VERTICAL, BORDER_PADDING_HORIZONTAL)
        ));

        button.setToolTipText(htmlDescriptionActionCommand);
        button.setActionCommand(htmlDescriptionActionCommand);

        button.addActionListener(e -> {
            if (this.bottomStatusLabel != null) {
                this.bottomStatusLabel.setText(e.getActionCommand());
                this.bottomStatusLabel.revalidate();
                this.bottomStatusLabel.repaint();
            }

        });

        return button;
    }

    /**
     * Public method to be called when the view
     * needs to reflect the current inventory state.
     */
    public void refreshView() {
        populateGrid(); // Rebuild the grid's content

        if (bottomStatusLabel != null) {
            bottomStatusLabel.setText(
                "<html><i>"
                + "Click an item in the grid to see its description."
                + "</i></html>");
        }
    }

    /**
     * Method to update the inventory model this view is looking at.
     * @param newInventory the new inventory to display
     */
    public void updateInventoryModel(final Inventory newInventory) {
        this.inventory = newInventory;
    }


}
