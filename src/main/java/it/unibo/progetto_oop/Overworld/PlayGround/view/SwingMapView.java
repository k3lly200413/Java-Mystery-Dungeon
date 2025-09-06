package it.unibo.progetto_oop.Overworld.PlayGround.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        // Tasto 'n' per next floor (il controller imposta la callback)
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

    // Imposta l'azione eseguita quando si preme 'n'
    public void onNextFloorRequested(final Runnable action) {
        this.nextFloorAction = Objects.requireNonNull(action);
    }

    // Mostra la view.
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
        panel.revalidate(); // ricalcolo dimensioni preferite
        panel.repaint();
    }

    public JPanel getPanel() {
        return this.panel;
    }

    // Imposta la griglia delle entità
    public void setEntityGrid(final StructureData entity) {
        panel.setEntityGrid(entity);
    }

    /* ============ Panel ============ */
    private static final class MapPanel extends JPanel {
        private StructureData grid;        // base
        private StructureData entityGrid;  // overlay

        // dimensione cella base
        private final int initialCell;

        // Sprite 
        private BufferedImage floorImg;
        private BufferedImage stairsImg;
        private BufferedImage playerImg;
        private BufferedImage enemyImg;
        private BufferedImage itemImg;

        MapPanel(final int cellSize) {
            this.initialCell = cellSize;
            setBackground(Color.BLACK);
            // Carica sprite dalle risorse
            this.floorImg = loadSprite("/spritesOverworld/floor.png");
            this.stairsImg = loadSprite("/spritesOverworld/stairs.png");
            this.playerImg = loadSprite("/spritesOverworld/link.png");
            this.enemyImg  = loadSprite("/spritesOverworld/gengar.png");
            this.itemImg   = loadSprite("/spritesOverworld/potion.png");
        }

        void setGrid(final StructureData g) {
            this.grid = g;
        }

        void setEntityGrid(final StructureData eg) {
            this.entityGrid = eg;
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

            // cell size dinamica
            final int cell = Math.max(1, Math.min(getWidth() / cols, getHeight() / rows));

            // centra la mappa
            final int mapW = cell * cols;
            final int mapH = cell * rows;
            final int offX = (getWidth()  - mapW) / 2;
            final int offY = (getHeight() - mapH) / 2;

            // Disegna TERRENO
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    final TileType t = grid.get(x, y);
                    final int px = offX + x * cell;
                    final int py = offY + y * cell;

                    final BufferedImage baseSprite = spriteFor(t);
                    if (baseSprite != null) {
                        g.drawImage(baseSprite, px, py, cell, cell, null);
                    } else {
                        g.setColor(colorFor(t));
                        g.fillRect(px, py, cell, cell);
                    }
                }
            }

            // Disegna ENTITÀ sopra
            if (entityGrid != null) {
                for (int y = 0; y < rows; y++) {
                    for (int x = 0; x < cols; x++) {
                        final TileType e = entityGrid.get(x, y);
                        if (e == TileType.NONE) continue;

                        final int px = offX + x * cell;
                        final int py = offY + y * cell;

                        final BufferedImage entSprite = spriteFor(e);
                        if (entSprite != null) {
                            g.drawImage(entSprite, px, py, cell, cell, null);
                        } else {
                            g.setColor(colorFor(e));
                            g.fillRect(px, py, cell, cell);
                        }

                        // bordo celle
                        g.setColor(new Color(0, 0, 0, 40));
                        g.drawRect(px, py, cell, cell);
                    }
                }
            }
        }

        private static BufferedImage loadSprite(final String path) {
            try (var is = SwingMapView.class.getResourceAsStream(path)) {
                return is == null ? null : ImageIO.read(is);
            } catch (Exception e) {
                return null;
            }
        }

        private BufferedImage spriteFor(final TileType t) {
            return switch (t) {
                // Terreno
                case ROOM -> floorImg;
                case TUNNEL -> floorImg;
                case STAIRS -> stairsImg;

                // Entità
                case PLAYER -> playerImg;
                case ENEMY  -> enemyImg;
                case ITEM   -> itemImg;

                // Nessuno / default
                case NONE   -> null;
                default     -> null;
            };
        }

        private Color colorFor(final TileType t) {
            return switch (t) {
                case WALL   ->  Color.BLACK;
                case ROOM   ->  Color.LIGHT_GRAY;
                case TUNNEL -> new Color(255, 215, 0);
                case STAIRS -> new Color(0, 170, 255);
                case PLAYER -> new Color(80, 200, 120);
                case ENEMY  -> new Color(200, 50, 50);
                case ITEM   -> new Color(160, 120, 255);
                case NONE   -> new Color(0, 0, 0, 0); // trasparente
                default     -> Color.MAGENTA;
            };
        }
    }
}
