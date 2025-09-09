package it.unibo.progetto_oop.combat.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

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
    private static final int VIEWPORT_WIDTH_CELLS = 3;

    /**
     * Number of cells in the viewport height-wise.
     */
    private static final int VIEWPORT_HEIGHT_CELLS = 1;

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
     * Status Area padding.
     */
    private static final int STATUS_PADDING = 5;

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
        this.setLayout(new BorderLayout(0, STATUS_PADDING));
        this.setBackground(FLOOR_COLOR);

        // Grid Panel
        this.gridPanel = new JPanel(
            new GridLayout(VIEWPORT_HEIGHT_CELLS, VIEWPORT_WIDTH_CELLS, 1, 1));
        this.gridPanel.setBackground(GRID_LINE_COLOR);
        this.add(this.gridPanel, BorderLayout.CENTER);

        // Status Area Panel and contents
        JPanel statusAreaPanel = createStatusAreaPanel();
        this.add(statusAreaPanel, BorderLayout.SOUTH);

        // Initial population
        populateGrid();

        // Set preferred size
        this.setPreferredSize(calculatePreferredSize());
    }

    private JButton createBackButton() {
        JButton button = new JButton("Back to Game");
        button.addActionListener(e -> {
            if (this.viewManager != null) {
                //SwingUtilities.getWindowAncestor(this).dispose();
                this.viewManager.showOverworld();
            }
        });

        return button;
    }

    private JLabel createStatusLabel() {
        JLabel label = new JLabel(
            "<html><i>Click an item...</i></html>",
            SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(
                STATUS_PADDING,
                STATUS_PADDING * 2,
                STATUS_PADDING,
                STATUS_PADDING * 2)
        ));

        label.setPreferredSize(
            new Dimension(STATUS_LABEL_W, STATUS_LABEL_H));

        return label;
    }

    private JPanel createStatusAreaPanel() {
        JPanel statusAreaPanel = new JPanel(
            new BorderLayout(0, STATUS_PADDING));
        statusAreaPanel.setBorder(
            BorderFactory.createEmptyBorder(
                STATUS_PADDING,
                STATUS_PADDING,
                STATUS_PADDING,
                STATUS_PADDING));

        this.bottomStatusLabel = createStatusLabel();
        statusAreaPanel.add(this.bottomStatusLabel, BorderLayout.CENTER);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.backButton = createBackButton();
        backButtonPanel.add(this.backButton);
        statusAreaPanel.add(backButtonPanel, BorderLayout.SOUTH);

        return statusAreaPanel;
    }

    private Dimension calculatePreferredSize() {
        int totalPreferredWidth = PREFERRED_CELL_WIDTH * VIEWPORT_WIDTH_CELLS;
        int gridActualHeight = PREFERRED_CELL_HEIGHT * VIEWPORT_HEIGHT_CELLS
            + VIEWPORT_HEIGHT_CELLS;
        int statusAreaHeight = bottomStatusLabel.getPreferredSize().height
            + backButton.getPreferredSize().height;

        return new Dimension(
                totalPreferredWidth, gridActualHeight + statusAreaHeight);
    }

    /**
     * Method to update the inventory model this view is looking at.
     * @param newInventory the new inventory to display
     */
    public void updateInventoryModel(final Inventory newInventory) {
        this.inventory = newInventory;
    }

    /**
     * Clears and re-populates the grid.
    */
    private void populateGrid() {
        if (this.gridPanel == null || this.inventory == null) {
            System.err.println("InventoryView: Cannot populate grid");
            return;
        }

        this.gridPanel.removeAll();

        java.util.List<Item> items = new ArrayList<>(
            this.inventory.getFullInventory().keySet());

        Color[] slotColors = new Color[] {
            ITEM_SLOT_1_COLOR, ITEM_SLOT_2_COLOR, ITEM_SLOT_3_COLOR
        };

        int nItems = items.size();
        int itemIndex = 0;

        for (int y = 0; y < VIEWPORT_HEIGHT_CELLS; y++) {
            for (int x = 0; x < VIEWPORT_WIDTH_CELLS; x++) {
                JButton cellButton;

                if (itemIndex < nItems) {
                    Item currentItem = items.get(itemIndex);
                    String desc = "<html>"
                        + currentItem.getDescription().replace("\n", "<br>")
                        + "<br><b style='color:blue;'>"
                        + this.inventory.getItemCount(currentItem)
                        + " in inventory"
                        + "</b></html>";
                    Color itemColor = slotColors[itemIndex % slotColors.length];
                    cellButton = createItemButton(
                        currentItem.getName(), itemColor, desc);
                    itemIndex++;
                } else {
                    cellButton = new JButton(); // cella vuota
                    cellButton.setEnabled(false);
                    cellButton.setOpaque(true);
                    cellButton.setBackground(FLOOR_COLOR);
                    cellButton.setBorder(
                        BorderFactory.createLineBorder(
                            GRID_LINE_COLOR.darker()));
                }

                cellButton.setPreferredSize(
                    new Dimension(PREFERRED_CELL_WIDTH, PREFERRED_CELL_HEIGHT));
                this.gridPanel.add(cellButton);
            }
        }

        this.gridPanel.revalidate();
        this.gridPanel.repaint();
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

}
