package it.unibo.progetto_oop.overworld.playground.data;

/**
 * Immutable configuration record for floor generation parameters.
 *
 * @param width     the width of the floor grid
 * @param height    the height of the floor grid
 * @param nRooms    the number of rooms to generate
 * @param minRoomW  the minimum width of a room
 * @param maxRoomW  the maximum width of a room
 * @param minRoomH  the minimum height of a room
 * @param maxRoomH  the maximum height of a room
 * @param nFloors   the number of floors to generate
 * @param tileSize  the size of each tile in pixels
 */

public record FloorConfig(
        int width, int height,
        int nRooms,
        int minRoomW, int maxRoomW,
        int minRoomH, int maxRoomH,
        int nFloors,
        int tileSize
) {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int NROOMS = 8;
    public static final int MIN_W = 5;
    public static final int MAX_W = 12;
    public static final int MIN_H = 5;
    public static final int MAX_H = 10;
    public static final int NFLOORS = 5;
    public static final int TILE_SIZE = 14;

    /**
     * Creates a new Builder instance for constructing FloorConfig objects.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing FloorConfig instances.
     */
    public static class Builder {
        private int width = WIDTH;
        private int height = HEIGHT;
        private int nRooms = NROOMS;
        private int minRoomW = MIN_W;
        private int maxRoomW = MAX_W;
        private int minRoomH = MIN_H;
        private int maxRoomH = MAX_H;
        private int nFloors = NFLOORS;
        private int tileSize = TILE_SIZE;

        /**
         * Sets the size of the floor grid.
         *
         * @param w the width of the grid, must be a positive integer
         * @param h the height of the grid, must be a positive integer
         *
         * @return the Builder instance for method chaining
         */
        public final Builder size(final int w, final int h) {
            this.width = w;
            this.height = h;
            return this;
        }

        /**
         * Sets the number of rooms for the floor configuration.
         *
         * @param n the number of rooms, must be a positive integer
         *
         * @return the Builder instance for method chaining
         */
        public final Builder rooms(final int n) {
            this.nRooms = n;
            return this;
        }

        /**
         * Sets the room size for the floor configuration.
         *
         * @param minW the minimum room width, must be >= 1
         * @param maxW the maximum room width, must be >= minW
         * @param minH the minimum room height, must be >= 1
         * @param maxH the maximum room height, must be >= minH
         *
         * @return the Builder instance for method chaining
         */
        public final Builder roomSize(
                final int minW, final int maxW,
                final int minH, final int maxH
        ) {
            this.minRoomW = minW;
            this.maxRoomW = maxW;
            this.minRoomH = minH;
            this.maxRoomH = maxH;
            return this;
        }

        /**
         * Sets the number of floors for the floor configuration.
         *
         * @param n the number of floors, must be a positive integer
         *
         * @return the Builder instance for method chaining
         */
        public final Builder floors(final int n) {
            this.nFloors = n;
            return this;
        }

        /**
         * Sets the tile size for the floor configuration.
         *
         * @param s the size of each tile, must be a positive integer
         *
         * @return the Builder instance for method chaining
         */
        public final Builder tileSize(final int s) {
            this.tileSize = s;
            return this;
        }

        /**
         * Builds FloorConfig instance with the specified parameters.
         * Validates the parameters.
         *
         * @return a new FloorConfig instance
         * @throws IllegalArgumentException if any parameter is invalid
         */
        public final FloorConfig build() {
            if (this.width <= 0 || this.height <= 0) {
                throw new IllegalArgumentException("Grid size must be > 0");
            }
            if (this.nRooms <= 0) {
                throw new IllegalArgumentException("At least 1 room");
            }
            if (this.minRoomW < 1 || this.minRoomH < 1) {
                throw new IllegalArgumentException("Room min must be >= 1");
            }
            if (this.maxRoomW < this.minRoomW
                || this.maxRoomH < this.minRoomH) {
                throw new IllegalArgumentException("Room max must be >= min");
            }
            if (this.nFloors <= 0) {
                throw new IllegalArgumentException("At least 1 floor");
            }

            return new FloorConfig(
                width, height, nRooms, minRoomW, maxRoomW,
                minRoomH, maxRoomH, nFloors, tileSize
            );
        }
    }
}
