package it.unibo.progetto_oop.Overworld.PlayGround;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SwingMapView extends JFrame implements MapView {

    private final MapPanel panel;

    public SwingMapView(String title, int cellSize) {
        super(title);
        this.panel = new MapPanel(cellSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new JScrollPane(panel));
        this.pack();
        this.setLocation(0, 0);   // angolo alto-sinistra
    }

    @Override
    public void render(List<ArrayList<TileType>> grid) {
        panel.setGrid(grid);
        this.pack();           // adatta la finestra alla mappa
        this.setVisible(true); // mostra la finestra
        panel.repaint();
    }

    /** Pannello che disegna la griglia. */
    private static class MapPanel extends JPanel {
        private List<ArrayList<TileType>> grid = List.of();
        private final int cell;

        MapPanel(int cellSize) {
            this.cell = cellSize;
            setBackground(Color.DARK_GRAY);
        }

        void setGrid(List<ArrayList<TileType>> g) {
            this.grid = (g == null) ? List.of() : g;
            revalidate();
        }

        @Override
        public Dimension getPreferredSize() {
            int rows = grid.size();
            int cols = rows == 0 ? 0 : grid.get(0).size();
            return new Dimension(cols * cell, rows * cell);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grid.isEmpty()) return;

            for (int r = 0; r < grid.size(); r++) {
                var row = grid.get(r);
                for (int c = 0; c < row.size(); c++) {
                    TileType tile = row.get(c);
                    g.setColor(colorFor(tile));
                    g.fillRect(c * cell, r * cell, cell, cell);

                    // reticolo sottile
                    g.setColor(new Color(0, 0, 0, 40));
                    g.drawRect(c * cell, r * cell, cell, cell);
                }
            }
        }

        private Color colorFor(TileType t) {
            return switch (t) {
                case WALL -> new Color(70, 70, 70);
                case FLOOR -> new Color(210, 210, 210);
                case TUNNEL -> new Color(255, 215, 0);
            };
        }
    }
}
