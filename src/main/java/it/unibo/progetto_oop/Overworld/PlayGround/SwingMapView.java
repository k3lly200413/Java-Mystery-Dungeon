package it.unibo.progetto_oop.Overworld.PlayGround;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SwingMapView extends JFrame implements MapView {
    private final MapPanel panel;

    public SwingMapView(String title, int cellSize) {
        super(title);
        this.panel = new MapPanel(cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JScrollPane(panel));
        //pack();
        setLocation(0, 0);
    }

    @Override
public void render(StructureData grid) {
    System.out.println("[VIEW] grid = " + grid + " size=" + grid.width() + "x" + grid.height());
    panel.setGrid(grid);
    pack();              // chiama pack DOPO setGrid
    setVisible(true);
    panel.repaint();
}


    /** --- */
    private static class MapPanel extends JPanel {
        private StructureData grid = null;
        private final int cell;

        MapPanel(int cellSize) { this.cell = cellSize; setBackground(Color.DARK_GRAY); }

        void setGrid(StructureData g) { this.grid = g; revalidate(); }

        @Override public Dimension getPreferredSize() {
            if (grid == null) return new Dimension(400, 300);
            return new Dimension(grid.width() * cell, grid.height() * cell);
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grid == null) {
                System.out.println("[VIEW] grid == null");
                return;
            }
            if (grid.width() == 0 || grid.height() == 0) {
                System.out.println("[VIEW] grid empty size " + grid.width() + "x" + grid.height());
                return;
            }

            if (grid == null) return;

            for (int y = 0; y < grid.height(); y++) {
                for (int x = 0; x < grid.width(); x++) {
                    TileType t = grid.get(x, y);
                    g.setColor(colorFor(t));
                    g.fillRect(x * cell, y * cell, cell, cell);
                    g.setColor(new Color(0, 0, 0, 40));
                    g.drawRect(x * cell, y * cell, cell, cell);
                }
            }
        }

        private Color colorFor(TileType t) {
            return switch (t) {
                case WALL     -> new Color(70, 70, 70);
                case FLOOR    -> new Color(210, 210, 210);
                case TUNNEL -> new Color(255, 215, 0);
            };
        }
    }
}

