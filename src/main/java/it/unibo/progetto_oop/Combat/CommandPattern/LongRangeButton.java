package it.unibo.progetto_oop.Combat.CommandPattern;

import java.util.List;

import it.unibo.progetto_oop.Combat.Position.Position;

public class LongRangeButton implements GameButton {

    /**
     * The position of the flame.
     */
    private Position flame;
    /**
     * The direction of the flame.
     * It can be either 1 or -1, where 1 means right and -1 means left.
     * This is used to determine the direction of the flame.
     */
    private int direction;

    /**
     * Constructor for LongRangeButton.
     * @param flamePosition the initial position of the flame
     * @param flameDirection the direction of the flame, either 1 or -1
     */
    public final void setAttributes(
        final Position flamePosition,
        final int flameDirection) {
        this.flame = flamePosition;
        this.direction = flameDirection;
    }

    @Override
    public final List<Position> execute() {
        this.flame =
            new Position(this.flame.x() + this.direction, this.flame.y());
        return List.of(this.flame);
    }

}
