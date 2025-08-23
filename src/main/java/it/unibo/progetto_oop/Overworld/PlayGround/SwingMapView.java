package it.unibo.progetto_oop.Overworld.PlayGround;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SwingMapView extends JFrame implements MapView, FloorObserver {

    private final MapPanel panel;

    public SwingMapView(String title, int cellSize) {
        super(title);
        this.panel = new MapPanel(cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JScrollPane(panel));
        setLocation(0, 0);
    }

    // per compatibilità col controller attuale
    @Override public void render(StructureData grid) {
        panel.setGrid(grid);
        pack();
        setVisible(true);
        panel.repaint();
    }

    // Observer: chiamato dal Floor quando cambia qualcosa
    @Override public void floorChanged(StructureData grid) {
        render(grid);
    }

    /** --- */
    private static class MapPanel extends JPanel {
        private StructureData grid;
        private final int cell;

        MapPanel(int cellSize){ this.cell = cellSize; setBackground(Color.BLACK); }

        void setGrid(StructureData g){ this.grid = g; revalidate(); }

        @Override public Dimension getPreferredSize() {
            int w = (grid == null ? 20 : grid.width());
            int h = (grid == null ? 15 : grid.height());
            return new Dimension(w * cell, h * cell);
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grid == null) return;
            for (int y = 0; y < grid.height(); y++) {
                for (int x = 0; x < grid.width(); x++) {
                    TileType t = grid.get(x, y);
                    g.setColor(colorFor(t));
                    g.fillRect(x * cell, y * cell, cell, cell);
                    g.setColor(new Color(0,0,0,40));
                    g.drawRect(x * cell, y * cell, cell, cell);
                }
            }
        }

        private Color colorFor(TileType t) {
            return switch (t) {
                case WALL     -> new Color(120,120,120);
                case ROOM    -> new Color(220,220,220);
                case TUNNEL -> new Color(255, 215, 0);
                case PLAYER,ENEMY,ITEM,STAIRS  -> new Color(0,0,0);
            };
        }
    }
}
