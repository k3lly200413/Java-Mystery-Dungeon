package it.unibo.progetto_oop.Overworld.PlayGround.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public final class SwingMapView extends JFrame implements MapView {

    private final MapPanel panel;
    private Runnable nextFloorAction = () -> { };

    /**
     * Constructs a SwingMapView with the specified title and cell size.
     *
     * @param title the title of the JFrame
     * @param cellSize the size of each cell in the map
     */
    public SwingMapView(final String title, final int cellSize) {
        super(title);
        this.panel = new MapPanel(cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(panel);
        setLocationByPlatform(true);
        setMinimumSize(new Dimension(400, 300));
        setResizable(true);
        //setLocationByPlatform(true);

        // messo qui per semplicità e testing,
        // dovrebbe essere il controller a dire alla view cosa fare
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyChar() == 'n') {
                    nextFloorAction.run();
                }
            }
        });
        setFocusable(true);
    }

    /**
     * Sets the action to be executed when the next floor is requested.
     *
     * @param action the action to execute when 'n' is pressed
     */
    public void onNextFloorRequested(final Runnable action) {
        this.nextFloorAction = Objects.requireNonNull(action);
    }

    /**
     * Makes the view visible.
     */
    public void showView() {
        pack();
        setVisible(true);
    }

    @Override
    public void render(final StructureData grid) {
        panel.setGrid(grid);
        if (!isVisible()) {
            pack();
            setVisible(true);
        }
        panel.revalidate(); //ricalcolo delle dimensioni
        panel.repaint();
    }

    public JPanel getPanel() {
        return this.panel;
    }

    /* ============ Panel ============ */
    private static final class MapPanel extends JPanel {
        /** The grid structure representing the floor. */
        private StructureData grid;

        /** The size of each cell in the map. */
        private final int initialCell;

        MapPanel(final int cellSize) {
            this.initialCell = cellSize;
            setBackground(Color.BLACK);
            // setDoubleBuffered(true);
        }

        void setGrid(final StructureData g) {
            this.grid = g;
        }

        @Override
        public Dimension getPreferredSize() {
            int w = (grid == null ? 20 : grid.width());
            int h = (grid == null ? 15 : grid.height());
            return new Dimension(w * initialCell, h * initialCell);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (grid == null) return;

            final int cols = grid.width();
            final int rows = grid.height();

            // cella dinamica
            final int cell = Math.max(1, Math.min(getWidth() / cols, getHeight() / rows));

            // centr0 la mappa nel pannello
            final int mapW = cell * cols;
            final int mapH = cell * rows;
            final int offX = (getWidth()  - mapW) / 2;
            final int offY = (getHeight() - mapH) / 2;

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    final TileType t = grid.get(x, y);
                    g.setColor(colorFor(t));
                    g.fillRect(offX + x * cell, offY + y * cell, cell, cell);
                    g.setColor(new Color(0, 0, 0, 40));
                    g.drawRect(offX + x * cell, offY + y * cell, cell, cell);
                }
            }
        }

        private Color colorFor(final TileType t) { // switch expression
            return switch (t) {
                case WALL   ->  Color.BLACK;
                case ROOM   ->  Color.LIGHT_GRAY;
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