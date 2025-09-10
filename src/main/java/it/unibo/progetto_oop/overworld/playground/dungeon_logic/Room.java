package it.unibo.progetto_oop.overworld.playground.dungeon_logic;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.progetto_oop.overworld.playground.data.Position;


public class Room implements Iterable<Position> {
    /**
     * The x-coordinate of the room.
     */
    private int x;

    /**
     * The y-coordinate of the room.
     */
    private int y;
    /**
     * The width of the room.
     */
    private int width;

    /**
     * The height of the room.
     */
    private int height;

    /**
     * Constructs a Room with the specified position and dimensions.
     * @param xCoord the x-coordinate of the room
     * @param yCoord the y-coordinate of the room
     * @param roomWidth the width of the room
     * @param roomHeight the height of the room
     */
    public Room(
        final int xCoord,
        final int yCoord,
        final int roomWidth,
        final int roomHeight) {
        this.x = xCoord;
        this.y = yCoord;
        this.width = roomWidth;
        this.height = roomHeight;
    }

    /**
     * Checks if this room intersects with another room.
     * @param other the other room to check for intersection
     * @return true if the rooms intersect, false otherwise
     */
    public final boolean intersects(final Room other) {
        return (this.x < other.x + other.width
                && this.x + this.width > other.x
                && this.y < other.y + other.height
                && this.y + this.height > other.y);
    }

    /**
     * Checks if the given position is within the bounds of the room.
     * @param p the position to check
     * @return true if the position is inside the room, false otherwise
     */
    public final boolean contains(final Position p) {
        return p.x() >= this.x
                && p.x() < this.x + this.width
                && p.y() >= this.y
                && p.y() < this.y + this.height;
    }

    /**
     * Gets the x-coordinate of the room.
     * @return the x-coordinate of the room
     */
    public final int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the room.
     * @param newX the new x-coordinate of the room
     */
    public final void setX(final int newX) {
        this.x = newX;
    }

    /**
     * Gets the y-coordinate of the room.
     * @return the y-coordinate of the room
     */
    public final int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the room.
     * @param newY the new y-coordinate of the room
     */
    public final void setY(final int newY) {
        this.y = newY;
    }

    /**
     * Gets the width of the room.
     * @return the width of the room
     */
    public final int getWidth() {
        return width;
    }

    /**
     * Sets the width of the room.
     * @param newWidth the new width of the room
     */
    public final void setWidth(final int newWidth) {
        this.width = newWidth;
    }

    /**
     * Gets the height of the room.
     * @return the height of the room
     */
    public final int getHeight() {
        return height;
    }

    /**
     * Sets the height of the room.
     * @param newHeight the new height of the room
     */
    public final void setHeight(final int newHeight) {
        this.height = newHeight;
    }

    @Override
    public final Iterator<Position> iterator() {
        return new Iterator<>() {
            private int cx = x;
            private int cy = y;

            @Override
            public boolean hasNext() {
                return cy < y + height;
            }

            @Override
            public Position next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Position c = new Position(cx, cy);
                cx++;
                if (cx >= x + width) {
                    cx = x;
                    cy++;
                }
                return c;
            }
        };
    }
}
