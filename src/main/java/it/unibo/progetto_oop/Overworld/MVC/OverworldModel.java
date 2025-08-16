package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;
import it.unibo.progetto_oop.Combat.Position.Position;

public class OverworldModel {
    private Player player;
    private List<Item> items = new ArrayList<>();
    private Inventory inventory;
    private final Set<Position> walls;
    private List<Enemy> enemies;
    private GenericEnemyState currentEnemyState;
    private List<Enemy> beatenEnemies = new ArrayList<>();

    

    public OverworldModel(Player player, List<Item> items, Inventory inventory, Set<Position> walls, List<Enemy> enemies) {
        this.player = player;
        this.items = items;
        this.inventory = inventory;
        this.walls = walls;
        this.enemies = enemies;
    }

    // --- getter methods ---

    /**
     * 
     * @return Posizione del giocatore
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * 
     * @return the list of items in the overworld
     */
    public List<Item> getItem(){
        return this.items;
    }


    /**
     * 
     * @return the player's inventory
     */

    public Inventory getInventoryInstance(){
        return this.inventory;
    }

    /**
     * 
     * @return lista dei muri
     */
    public Set<Position> getWalls(){
        return this.walls;
    }

    /**
     * 
     * @return Lista dei nemici sulla mappa
     */
    public List<Enemy> getEnemies(){
        return this.enemies;
    }
    

    // methods

    private boolean enemyHitPlayer(){
        return this.enemies.stream().anyMatch(enemy -> enemy.getCurrentPosition().equals(this.player.getPosition()));
    }

    private void removeEnemy(Enemy enemyToRemove){
        if (this.enemies.contains(enemyToRemove)){
            this.enemies.remove(enemyToRemove);
            this.beatenEnemies.add(enemyToRemove);
        }
    }

    public boolean isMoveValid(Position target){
        if(this.walls.contains(target)){
            return false;
        }
        if (this.enemyHitPlayer()){

            // if player wins:
            this.enemies.stream().filter(enemy -> enemy.getCurrentPosition().equals(target)).findFirst().ifPresent(presentEnemy -> this.removeEnemy(presentEnemy));
        }
        return true;
    }

    public void tryToMovePlayer(MoveDirection direction){
        Position nextPosition = this.getNextDirection(direction);
        if (this.isMoveValid(nextPosition)){
            // set the new position of the player
            // if item is present in the position, remove it from the list
        }
        // possible enemy interaction
    }

    private Position getNextDirection(MoveDirection direction){
        switch (direction) {
            case UP:
                return new Position(0, -1);
            case DOWN:
                return new Position(0, 1);
            case LEFT:
                return new Position(-1, 0);
            case RIGHT:
                return new Position(1, 0);
            case NONE:
                System.out.println("direction is 0 please fix");
                return new Position(0, 0);
            default:
                System.out.println("Direction is NULL please fix");
                return new Position(0, 0);
        }
    }

}
