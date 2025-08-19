package it.unibo.progetto_oop.Overworld.PlayGround;

public enum TileType {
    WALL,       // muri
    FLOOR,      // stanze
    TUNNEL;   // corridoi

    @Override
    public String toString() {
        return switch (this) {
            case WALL -> "#";
            case FLOOR -> ".";
            case TUNNEL -> ":";
        };
    }
}
