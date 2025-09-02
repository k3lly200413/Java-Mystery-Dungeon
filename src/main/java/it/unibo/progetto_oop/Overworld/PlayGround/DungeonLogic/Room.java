package it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;


public class Room implements Iterable<Position>{
    int x, y, width, height;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Room other) {
        return (this.x < other.x + other.width && this.x + this.width > other.x &&
                this.y < other.y + other.height && this.y + this.height > other.y);
    }

    public boolean contains(Position p) {
    return p.x() >= this.x
        && p.x() < this.x + this.width
        && p.y() >= this.y
        && p.y() < this.y + this.height;
}


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Iterator<Position> iterator() {
        return new Iterator<>() {
            private int cx = x, cy = y;

            @Override
            public boolean hasNext() {
                return cy < y + height;
            }

            @Override
            public Position next() {
                if (!hasNext()) throw new NoSuchElementException();
                Position c = new Position(cx, cy);
                cx++;
                if (cx >= x + width) { cx = x; cy++; }
                return c;
            }
        };
    }
}