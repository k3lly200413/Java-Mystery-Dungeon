package it.unibo.progetto_oop.Overworld.PlayGround.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public final class SwingMapView extends JPanel implements MapView {

    private final MapPanel panel;

    /**
     * Constructs a SwingMapView with the specified title and cell size.
     *
     * @param title the title of the JFrame
     * @param cellSize the size of each cell in the map
     */
    public SwingMapView(final int cellSize) {
        this.panel = new MapPanel(cellSize);
        setLayout(new BorderLayout());
        add(this.panel, BorderLayout.CENTER);
    }

    @Override
    public void render(final StructureData grid) {
        panel.setGrid(grid);
        revalidate();
        repaint();
    }

    // Imposta la griglia delle entità
    public void setEntityGrid(final StructureData entity) {
        panel.setEntityGrid(entity);
        repaint();
    }

    public void setCameraTarget(final Position p) {
        panel.setCameraTarget(p);
        repaint();
    }

    public void setZoom(double z) {
        panel.setZoom(z);
        revalidate();
        repaint();
    }

    /* ============ Canvas ============ */
    private static final class MapPanel extends JPanel {
        private StructureData grid;        // base
        private StructureData entityGrid;  // overlay

        // dimensione cella base
        private final int initialCell;
        private double zoom = 1.0;
        private Position camTarget;

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

        void setCameraTarget(final Position p) {
            this.camTarget = p;
        }

        void setZoom(double z) {
            this.zoom = z;
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
            if (grid == null)
                return;

            final int cols = grid.width();
            final int rows = grid.height();

            // cell size dinamica
            final int baseCell = Math.max(1, Math.min(getWidth() / cols, getHeight() / rows));
            final int cell = Math.max(1, (int) Math.round(baseCell * zoom));

            // centra mappa su player
            final int mapW = cell * cols;
            final int mapH = cell * rows;
            int OffX = (int) Math.round(getWidth() / 2.0 - camTarget.x() * cell);
            int OffY = (int) Math.round(getHeight() / 2.0 - camTarget.y() * cell);
            final int offX = (mapW > getWidth())
                    ? clamp(OffX, getWidth() - mapW, 0)
                    : (getWidth() - mapW) / 2;
            final int offY = (mapH > getHeight())
                    ? clamp(OffY, getHeight() - mapH, 0)
                    : (getHeight() - mapH) / 2;

            // Disegna TERRENO
            drawCells(g, grid, cell, rows, cols, offX, offY);

            // Disegna ENTITÀ sopra
            drawCells(g, entityGrid, cell, rows, cols, offX, offY);
        }

        private void drawCells(Graphics g, StructureData TypeGrid, int cellSize, int rows, int cols, int offX, int offY) {
            if (TypeGrid == null)
                return;
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    final TileType e = TypeGrid.get(x, y);
                    if (e != TileType.NONE) {
                        final int px = offX + x * cellSize;
                        final int py = offY + y * cellSize;

                        final BufferedImage entSprite = spriteFor(e);
                        if (entSprite != null) {
                            g.drawImage(entSprite, px, py, cellSize, cellSize, null);
                        } else {
                            g.setColor(colorFor(e));
                            g.fillRect(px, py, cellSize, cellSize);
                        }

                        // bordo celle
                        g.setColor(new Color(0, 0, 0, 40));
                        g.drawRect(px, py, cellSize, cellSize);
                    }
                }
            }
        }
        
        private static int clamp(int v, int min, int max) {
            return Math.max(min, Math.min(max, v));
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
