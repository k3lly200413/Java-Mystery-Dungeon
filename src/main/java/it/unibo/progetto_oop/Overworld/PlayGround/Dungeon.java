package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.ArrayList;

public class Dungeon {
    private final List<Floor> floors;
    private int currentFloor;

    public Dungeon(List<Floor> floors) {
        this.floors = new ArrayList<>();
        this.currentFloor = 0;
        if (floors == null || floors.isEmpty()) {
            throw new IllegalArgumentException("Need at least one floor");
        }
        this.floors.addAll(floors);
    }

    public Floor getCurrentFloor() {
        return floors.get(currentFloor);
    }

    public boolean nextFloor() {
        if (this.currentFloor < floors.size() - 1) {
            this.currentFloor++;
            return true;
        }
        return false;
    }
}
