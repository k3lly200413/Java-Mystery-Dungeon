package it.unibo.progetto_oop.Combat.DrawHelper;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class DrawHelper {
    public boolean neighbours(Position pos1, Position pos2, int dist) {
        // Check if two positions are neighbours (adjacent in any direction)
        return (Math.abs(pos1.x() - pos2.x()) <= dist && Math.abs(pos1.y() - pos2.y()) <= dist);
        //&& (Math.abs(pos1.x() - pos2.x()) + Math.abs(pos1.y() - pos2.y()) <= dist);
    }

    public boolean deathNeighbours(Position pos1, Position pos2, int dist) {
        // Check if two positions are neighbours (adjacent in any direction)
        return (Math.abs(pos1.x() - pos2.x()) == dist && Math.abs(pos1.y() - pos2.y()) == dist) || 
        (Math.abs(pos1.x() - pos2.x()) == dist && pos1.y() == pos2.y()) ||
        (pos1.x() == pos2.x() && Math.abs(pos1.y() - pos2.y()) == dist) ||
        (pos1.x() == pos2.x() && pos2.y() == pos1.y());
    }
}
