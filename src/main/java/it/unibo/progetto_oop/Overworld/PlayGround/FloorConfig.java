package it.unibo.progetto_oop.Overworld.PlayGround;

public record FloorConfig(
        int width, int height,
        int nRooms,
        int minRoomW, int maxRoomW,
        int minRoomH, int maxRoomH
) {
    /** Comodo: FloorConfig.builder().size(...).rooms(...).build(); */
    //classe innestata per comodita' di costruzione
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int width = 50, height = 50;
        private int nRooms = 8;
        private int minRoomW = 5, maxRoomW = 12;
        private int minRoomH = 5, maxRoomH = 10;

        public Builder size(int w, int h) {
            this.width = w;
            this.height = h;
            return this;
        }
        public Builder rooms(int n) {
            this.nRooms = n;
            return this;
        }
        public Builder roomSize(int minW, int maxW, int minH, int maxH) {
            this.minRoomW = minW;
            this.maxRoomW = maxW;
            this.minRoomH = minH;
            this.maxRoomH = maxH;
            return this;
        }

        public FloorConfig build() {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("Grid size must be > 0");
            if (nRooms <= 0)
                throw new IllegalArgumentException("At least 1 room");
            if (minRoomW < 1 || minRoomH < 1)
                throw new IllegalArgumentException("Room min must be >= 1");
            if (maxRoomW < minRoomW || maxRoomH < minRoomH)
                throw new IllegalArgumentException("Room max must be >= min");
                
            return new FloorConfig(width, height, nRooms, minRoomW, maxRoomW, minRoomH, maxRoomH);
        }
    }
}
