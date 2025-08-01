package it.unibo.progetto_oop.Combat.CommandPattern;

import java.util.List;

import it.unibo.progetto_oop.Combat.Position.Position;

public class LongRangeButton implements GameButton{
    
    private Position flame;
    private int direction;

    public void setAttributes(Position flame, int direction){
        this.flame = flame;
        this.direction = direction;
    }

    @Override
    public List<Position> execute() {
        this.flame = new Position(this.flame.x() + this.direction, this.flame.y());
        return List.of(this.flame);
    }

}
