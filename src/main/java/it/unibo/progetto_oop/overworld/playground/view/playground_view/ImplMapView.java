package it.unibo.progetto_oop.overworld.playground.view.playground_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * A Swing-based implementation of the MapView interface.
 * This class provides a graphical representation of a grid-based map
 * using Java Swing components.
 */

public final class ImplMapView extends JPanel implements MapView {

    private static final long serialVersionUID = 1L;

    /**
     * The panel used to render the map view.
     */
    private final MapPanel panel;

    /**
     * Constructs a SwingMapView with the specified title and cell size.
     *
     * @param cellSize the size of each cell in the map
     */
    public ImplMapView(final int cellSize) {
        this.panel = new MapPanel(cellSize);
        setLayout(new BorderLayout());
        add(this.panel, BorderLayout.CENTER);
    }

    @Override
    public void render(final ReadOnlyGrid grid) {
        panel.setGrid(grid);
        revalidate();
        repaint();
    }

    /**
     * Sets the entity grid to be rendered on top of the base grid.
     *
     * @param entity the entity grid data
     */
    public void setEntityGrid(final ReadOnlyGrid entity) {
        panel.setEntityGrid(entity);
        repaint();
    }

    /**
     * Sets the camera target position on the map.
     *
     * @param p the position to center the camera on
     */
    public void setCameraTarget(final Position p) {
        panel.setCameraTarget(p);
        repaint();
    }

    /**
     * Sets the zoom level for the map view.
     *
     * @param z the zoom level, where 1.0 represents the default zoom
     */
    public void setZoom(final double z) {
        panel.setZoom(z);
        revalidate();
        repaint();
    }

    /* ============ Canvas ============ */
    private static final class MapPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        /**
         * The default width of the grid in cells.
         */
        private static final int DEFAULT_GRID_WIDTH = 20;

        /**
         * The default height of the grid in cells.
         */
        private static final int DEFAULT_GRID_HEIGHT = 15;

        /**
         * The alpha transparency value for the cell border color.
         */
        private static final int CELL_BORDER_ALPHA = 40;

        /**
         * The base grid representing the map structure.
         */
        private transient ReadOnlyGrid grid; // base

        /**
         * The overlay grid representing entities on the map.
         */
        private transient ReadOnlyGrid entityGrid; // overlay

        /**
         * The initial size of each cell in pixels.
         */
        private final int initialCell;
        /**
         * The zoom level for the map view.
         * A value of 1.0 represents the default zoom level.
         */
        private double zoom = 1.0;

        /**
         * The camera target position on the map.
         * This determines the center of the view.
         */
        private transient Position camTarget;

        // Sprite
        /**
         * The image representing the floor tile.
         */
        private transient final BufferedImage floorImg;

        /**
         * The image representing the stairs tile.
         */
        private transient final BufferedImage stairsImg;
        /**
         * The image representing the player entity.
         */
        private transient final BufferedImage playerImg;

        /**
         * The image representing the enemy entity.
         */
        private transient final BufferedImage enemyImg;
        /**
         * The image representing the item entity.
         */
        private transient final BufferedImage itemImg;

        /**
         * The image representing the boss entity.
         */
        private transient final BufferedImage bossImg;

        MapPanel(final int cellSize) {
            this.initialCell = cellSize;
            setBackground(Color.BLACK);
            this.floorImg = loadSprite("/spritesOverWorld/floor.png");
            this.stairsImg = loadSprite("/spritesOverWorld/stairs.png");
            this.playerImg = loadSprite("/spritesOverWorld/link.png");
            this.enemyImg = loadSprite("/spritesOverWorld/gengar.png");
            this.itemImg = loadSprite("/spritesOverWorld/potion.png");
            this.bossImg = loadSprite("/spritesOverWorld/boss.png");
        }

        void setGrid(final ReadOnlyGrid g) {
            this.grid = g;
        }

        void setEntityGrid(final ReadOnlyGrid eg) {
            this.entityGrid = eg;
        }

        void setCameraTarget(final Position p) {
            this.camTarget = p;
        }

        void setZoom(final double z) {
            this.zoom = z;
        }

        @Override
        public Dimension getPreferredSize() {
            final int w = grid == null ? DEFAULT_GRID_WIDTH : grid.width();
            final int h = grid == null ? DEFAULT_GRID_HEIGHT : grid.height();
            return new Dimension(w * initialCell, h * initialCell);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (grid == null) {
                return;
            }

            final int cols = grid.width();
            final int rows = grid.height();

            // dynamic cell size
            final int baseCell = Math.max(
                1, Math.min(getWidth() / cols, getHeight() / rows)
            );
            final int cell = Math.max(
                1, (int) Math.round(baseCell * zoom)
            );

            // center on player
            final int mapW = cell * cols;
            final int mapH = cell * rows;
            final int offsetX = (int) Math.round(
                getWidth() / 2.0 - camTarget.x() * cell
            );
            final int offsetY = (int) Math.round(
                getHeight() / 2.0 - camTarget.y() * cell
            );
            final int offX = (mapW > getWidth())
                    ? clamp(offsetX, getWidth() - mapW, 0)
                    : (getWidth() - mapW) / 2;
            final int offY = (mapH > getHeight())
                    ? clamp(offsetY, getHeight() - mapH, 0)
                    : (getHeight() - mapH) / 2;

            // draw base
            drawCells(g, grid, cell, rows, cols, offX, offY);

            // draw entities on top
            drawCells(g, entityGrid, cell, rows, cols, offX, offY);
        }

        private void drawCells(
                final Graphics g,
                final ReadOnlyGrid typeGrid,
                final int cellSize,
                final int rows,
                final int cols,
                final int offX,
                final int offY
        ) {
            if (typeGrid == null) {
                return;
            }
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    final TileType e = typeGrid.get(x, y);
                    if (e != TileType.NONE) {
                        final int px = offX + x * cellSize;
                        final int py = offY + y * cellSize;

                        final BufferedImage entSprite = spriteFor(e);
                        if (entSprite != null) {
                            g.drawImage(
                                entSprite, px, py, cellSize, cellSize, null
                            );
                        } else {
                            g.setColor(colorFor(e));
                            g.fillRect(px, py, cellSize, cellSize);
                        }
                        // draw cell border
                        g.setColor(new Color(0, 0, 0, CELL_BORDER_ALPHA));
                        g.drawRect(px, py, cellSize, cellSize);
                    }
                }
            }
        }

        private static int clamp(final int v, final int min, final int max) {
            return Math.max(min, Math.min(max, v));
        }

        private static BufferedImage loadSprite(final String path) {
            try (var is = ImplMapView.class.getResourceAsStream(path)) {
                return is == null ? null : ImageIO.read(is);
            } catch (final IOException e) {
                return null;
            }
        }

        private BufferedImage spriteFor(final TileType t) {
            return switch (t) {
                // ground
                case ROOM -> floorImg;
                case TUNNEL -> floorImg;
                case STAIRS -> stairsImg;

                // entities
                case PLAYER -> playerImg;
                case ENEMY -> enemyImg;
                case BOSS -> bossImg;
                case ITEM -> itemImg;

                case NONE -> null;
                default -> null;
            };
        }

        private Color colorFor(final TileType t) {
            return switch (t) {
                case WALL -> Color.BLACK;
                case ROOM -> Color.LIGHT_GRAY;
                case TUNNEL -> Color.LIGHT_GRAY;
                case STAIRS -> Color.CYAN;
                case PLAYER -> Color.GREEN;
                case ENEMY -> Color.RED;
                case BOSS -> Color.MAGENTA;
                case ITEM -> Color.PINK;
                case NONE -> new Color(0, 0, 0, 0); // clear
            };
        }
    }
}
