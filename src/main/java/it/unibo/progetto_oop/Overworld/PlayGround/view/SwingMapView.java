package it.unibo.progetto_oop.Overworld.PlayGround.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public final class SwingMapView extends JFrame implements MapView {
    /** The panel used to render the map. */
    private final MapPanel panel;

    /** Action to be executed when the next floor is requested. */
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
        setContentPane(new JScrollPane(panel));
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
        pack();
        panel.repaint();
        if (!isVisible()) {
            setVisible(true);
        }
    }

    /* ============ Panel ============ */
    private static final class MapPanel extends JPanel {
        /** The grid structure representing the floor. */
        private StructureData grid;

        /** The size of each cell in the map. */
        private final int cell;

        MapPanel(final int cellSize) {
            this.cell = cellSize;
            setBackground(Color.BLACK);
            // setDoubleBuffered(true);
        }

        void setGrid(final StructureData g) {
            this.grid = g;
            // revalidate();
        }

        @Override
        public Dimension getPreferredSize() {
            int w = (grid == null ? 20 : grid.width());
            int h = (grid == null ? 15 : grid.height());
            return new Dimension(w * cell, h * cell);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            // if (grid == null) return;
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
