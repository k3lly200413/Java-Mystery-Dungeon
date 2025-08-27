package it.unibo.progetto_oop.Overworld.PlayGround;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public final class SwingMapView extends JFrame implements MapView {
    private final MapPanel panel;
    private Runnable nextFloorAction = () -> {};

    public SwingMapView(String title, int cellSize) {
        super(title);
        this.panel = new MapPanel(cellSize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JScrollPane(panel));
        //setLocationByPlatform(true);

        // messo qui per semplicità e testing, dovrebbe essere il controller a dire alla view cosa fare
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'n') {
                    nextFloorAction.run();
                }
            }
        });
        setFocusable(true);
    }

    /**azione da fare quando premo n. */
    public void onNextFloorRequested(Runnable action) {
        this.nextFloorAction = Objects.requireNonNull(action);
    }

    /** Mostra la finestra. */
    public void showView() {
        pack();
        setVisible(true);
    }

    /* ------------ MapView ------------ */
    @Override
    public void render(StructureData grid) {
        panel.setGrid(grid);
        pack();
        panel.repaint();
        if (!isVisible()) setVisible(true);
    }

    /* ============ Panel ============ */
    private static final class MapPanel extends JPanel {
        private StructureData grid;
        private final int cell;

        MapPanel(int cellSize) {
            this.cell = Math.max(4, cellSize);
            setBackground(Color.BLACK);
            //setDoubleBuffered(true);
        }

        void setGrid(StructureData g) {
            this.grid = g;
            //revalidate();
        }

        @Override
        public Dimension getPreferredSize() {
            int w = (grid == null ? 20 : grid.width());
            int h = (grid == null ? 15 : grid.height());
            return new Dimension(w * cell, h * cell);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //if (grid == null) return;
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

        private Color colorFor(TileType t) { //switch expression
            return switch (t) {
                case WALL   -> new Color(120, 120, 120);
                case ROOM   -> new Color(220, 220, 220);
                case TUNNEL -> new Color(255, 215, 0); 
                case STAIRS -> new Color(0, 170, 255);
                case PLAYER -> new Color(80, 200, 120);
                case ENEMY  -> new Color(200, 50, 50);  
                case ITEM   -> new Color(160, 120, 255);
                default     -> Color.MAGENTA;         
            };
        }
    }
}
