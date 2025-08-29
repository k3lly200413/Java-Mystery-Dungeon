package it.unibo.progetto_oop.Combat.Inventory;

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

import it.unibo.progetto_oop.Overworld.MVC.OverworldApplication;

public class InventoryView extends JPanel {
    private static final int VIEWPORT_WIDTH_CELLS = 13;
    private static final int VIEWPORT_HEIGHT_CELLS = 13;
    private static final int PREFERRED_CELL_WIDTH = 70;
    private static final int PREFERRED_CELL_HEIGHT = 60;

    private static final Color FLOOR_COLOR = Color.LIGHT_GRAY;
    private static final Color GRID_LINE_COLOR = Color.GRAY;
    private static final Color ITEM_SLOT_1_COLOR = Color.BLACK;
    private static final Color ITEM_SLOT_2_COLOR = Color.BLUE;
    private static final Color ITEM_SLOT_3_COLOR = Color.CYAN;

    private final OverworldApplication game;
    private Inventory inventory; 

    private JPanel gridPanel; 
    private JLabel bottomStatusLabel;
    private JButton backButton;

    public InventoryView(Inventory initialInventory, OverworldApplication game) {
        this.inventory = initialInventory;
        this.game = game;

        // Layout Panel
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(FLOOR_COLOR);

        // Grid Panel
        this.gridPanel = new JPanel(new GridLayout(VIEWPORT_HEIGHT_CELLS, VIEWPORT_WIDTH_CELLS, 1, 1));
        this.gridPanel.setBackground(GRID_LINE_COLOR);
        this.add(this.gridPanel, BorderLayout.CENTER);

        // Status Area Panel and contents
        JPanel statusAreaPanel = new JPanel(new BorderLayout(0, 5));
        statusAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.bottomStatusLabel = new JLabel("<html><i>Click an item...</i></html>", SwingConstants.CENTER);
        this.bottomStatusLabel.setOpaque(true);
        this.bottomStatusLabel.setBackground(Color.WHITE);
        this.bottomStatusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        this.bottomStatusLabel.setPreferredSize(new Dimension(100, 80)); // Give it some height
        statusAreaPanel.add(this.bottomStatusLabel, BorderLayout.CENTER);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.backButton = new JButton("Back to Game");
        this.backButton.addActionListener(e -> {
            if (this.game != null) {
                // TODO: back to overworld
            }
        });
        backButtonPanel.add(this.backButton);
        statusAreaPanel.add(backButtonPanel, BorderLayout.SOUTH);

        this.add(statusAreaPanel, BorderLayout.SOUTH);

        // Initial population
        populateGrid();

        // Set preferred size
        int totalPreferredWidth = PREFERRED_CELL_WIDTH * VIEWPORT_WIDTH_CELLS+20;
        int gridActualHeight = PREFERRED_CELL_HEIGHT * VIEWPORT_HEIGHT_CELLS+(VIEWPORT_HEIGHT_CELLS);
        int statusAreaHeight = bottomStatusLabel.getPreferredSize().height+backButton.getPreferredSize().height+20;
        this.setPreferredSize(new Dimension(totalPreferredWidth, gridActualHeight+statusAreaHeight));
    }

    /**
     * Method to update the inventory model this view is looking at.
     */
    public void updateInventoryModel(Inventory newInventory) {
        this.inventory = newInventory;
    }

    /**
     * Clears and re-populates the grid
    */
    private void populateGrid() {
        // in case of catastrofic error
        if (this.gridPanel == null || this.inventory == null) {
            System.err.println("InventoryView: Cannot populate grid, panel or inventory is null.");
            return;
        }

        this.gridPanel.removeAll(); 

        java.util.List<Item> items = new ArrayList<>(this.inventory.getFullInventory().keySet());
        System.out.println("DEBUG populateGrid: Item size => "+items.size());

        // Grid Population Logic
        int middleY = Math.round((VIEWPORT_HEIGHT_CELLS-1) / 2.0f);
        int middleX = Math.round((VIEWPORT_WIDTH_CELLS-1) / 2.0f);
        int quarterX = Math.round((VIEWPORT_WIDTH_CELLS-1) / 4.0f);
        int threeQuarterX = middleX+(middleX-quarterX);

        JButton cellButton;
        boolean isItemSlot = false;
        Item currentItem = null;
        Color itemColor = null;

        for (int y = 0; y<VIEWPORT_HEIGHT_CELLS; y++) {
            for (int x = 0; x<VIEWPORT_WIDTH_CELLS; x++) {
                isItemSlot = false;
                currentItem = null;
                itemColor = null;
                if (y == middleY) {
                    if (x == quarterX && items.size()>0) { // I am at 1/4 and there is at least one item
                        System.out.println("Description => " + items.get(0).getDescription());

                        isItemSlot = true; 
                        currentItem = items.get(0); // 
                        itemColor = ITEM_SLOT_1_COLOR;
                    } else if (x == middleX && items.size()>1) {
                        isItemSlot = true; 
                        currentItem = items.get(1); // 3/4
                        itemColor = ITEM_SLOT_2_COLOR;
                    } else if (x == threeQuarterX && items.size()>2) {
                        isItemSlot = true; 
                        currentItem = items.get(2); 
                        itemColor = ITEM_SLOT_3_COLOR;
                    }
                }

                if (isItemSlot && currentItem != null) { // Create item button
                    String desc = "<html>"+currentItem.getDescription().replace("\n", "<br>")+"</html>";
                    System.out.println("New Description = > "+desc);
                    cellButton = createItemButton(currentItem.getName(), itemColor, desc);
                } else {
                    cellButton = new JButton(); // Placeholder button
                    cellButton.setEnabled(false);
                    cellButton.setOpaque(true);
                    cellButton.setBackground(FLOOR_COLOR);
                    cellButton.setBorder(BorderFactory.createLineBorder(GRID_LINE_COLOR.darker()));
                }
                cellButton.setPreferredSize(new Dimension(PREFERRED_CELL_WIDTH, PREFERRED_CELL_HEIGHT));
                this.gridPanel.add(cellButton);
            }
        }

        // Finalize
        this.gridPanel.revalidate(); // recaltulate layout of the panel
        this.gridPanel.repaint(); // repaint the panel()
    }

    /**
     * Helper method MATTE
    */
    private JButton createItemButton(String text, Color color, String htmlDescriptionActionCommand) {
        JButton button = new JButton(text);
        // TODO
        return button;
    }

    /**
     * Public method to be called when the view needs to reflect the current inventory state.
     */
    public void refreshView() {
        populateGrid(); // Rebuild the grid's content

        if (bottomStatusLabel != null) {
            bottomStatusLabel.setText("<html><i>Click an item in the grid to see its description.</i></html>");
        }
    }

}
