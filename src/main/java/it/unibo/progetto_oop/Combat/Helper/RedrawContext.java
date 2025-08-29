package it.unibo.progetto_oop.Combat.Helper;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;

import it.unibo.progetto_oop.Combat.Position.Position;

public class RedrawContext {
    /**
     * Position of the player.
     */
    private Position player;
    /**
     * Position of the enemy.
     */
    private Position enemy;
    /**
     * Position of the flame.
     */
    private Position flame;
    /**
     * Size of the flame.
     */
    private int flameSize;
    /**
     * deciding whether to draw or not the player.
     */
    private boolean drawPlayer;
    /**
     * deciding whether to draw or not the enemy.
     */
    private boolean drawEnemy;
    /**
     * deciding whether to draw or not the flame.
     */
    private boolean drawFlame;
    /**
     * deciding whether to draw or not the poison.
     */
    private boolean drawPoison;
    /**
     * Size range of the player.
     */
    private int playerRange;
    /**
     * Size range of the enemy.
     */
    private int enemyRange;
    /**
     * checking if the game is over.
     */
    private boolean isGameOver;
    /**
     * Position of who died (player or enemy).
     */
    private Position whoDied;
    /**
     * deciding whether to draw or not the boss ray attack.
     */
    private boolean drawBossRayAttack;
    /**
     * Path of the death ray (if any).
     */
    private ArrayList<Position> deathRayPath;
    /**
     * deciding whether to draw or not the poison damage.
     */
    private boolean drawPoisonDamage;
    /**
     * Y coordinate of the poison damage.
     */
    private int poisonYCoord;
    /**
     * deciding whether the enemy is charging an attack.
     */
    private boolean isCharging;
    /**
     * Distance of the charging " block " of the enemy.
     */
    private int chargingCellDistance;
    /**
     * Width of each square in the grid.
     */
    private int squareWidth;
    /**
     * Height of each square in the grid.
     */
    private int squareHeight;

    private RedrawContext(Builder builder) {
        this.player = builder.player;
        this.enemy = builder.enemy;
        this.flame = builder.flame;
        this.flameSize = builder.flameSize;
        this.drawPlayer = builder.drawPlayer;
        this.drawEnemy = builder.drawEnemy;
        this.drawFlame = builder.drawFlame;
        this.drawPoison = builder.drawPoison;
        this.playerRange = builder.playerRange;
        this.enemyRange = builder.enemyRange;
        this.isGameOver = builder.isGameOver;
        this.whoDied = builder.whoDied;
        this.drawBossRayAttack = builder.drawBossRayAttack;
        this.deathRayPath = builder.deathRayPath;
        this.drawPoisonDamage = builder.drawPoisonDamage;
        this.poisonYCoord = builder.poisonYCoord;
        this.isCharging = builder.isCharging;
        this.chargingCellDistance = builder.chargingCellDistance;
        this.squareWidth = builder.squareWidth;
        this.squareHeight = builder.squareHeight;
    }

    // Getters

    /**
     * Getter for player position.
     * @return the player position
     */
    public Position getPlayer() {
        return player;
    }
    /**
     * Getter for enemy position.
     * @return the enemy position
     */
    public Position getEnemy() {
        return enemy;
    }
    /**
     * Getter for flame position.
     * @return the flame position
     */
    public Position getFlame() {
        return flame;
    }
    /**
     * Getter for flame size.
     * @return the flame size
     */
    public int getFlameSize() {
        return flameSize;
    }
    /**
     * Getter for drawPlayer.
     * @return true if the player should be drawn, false otherwise
     */
    public boolean isDrawPlayer() {
        return drawPlayer;
    }
    /**
     * Getter for drawEnemy.
     * @return true if the enemy should be drawn, false otherwise
     */
    public boolean isDrawEnemy() {
        return drawEnemy;
    }
    /**
     * Getter for drawFlame.
     * @return true if the flame should be drawn, false otherwise
     */
    public boolean isDrawFlame() {
        return drawFlame;
    }
    /**
     * Getter for drawPoison.
     * @return true if the poison should be drawn, false otherwise
     */
    public boolean isDrawPoison() {
        return drawPoison;
    }
    /**
     * Getter for playerRange.
     * @return the player range
     */
    public int getPlayerRange() {
        return playerRange;
    }
    /**
     * Getter for enemyRange.
     * @return the enemy range
     */
    public int getEnemyRange() {
        return enemyRange;
    }
    /**
     * Getter for isGameOver.
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }
    /**
     * Getter for whoDied.
     * @return the position of who died
     */
    public Position getWhoDied() {
        return whoDied;
    }
    /**
     * Getter for drawBossRayAttack.
     * @return true if the boss ray attack should be drawn, false otherwise
     */
    public boolean isDrawBossRayAttack() {
        return drawBossRayAttack;
    }
    /**
     * Getter for deathRayPath.
     * @return the path of the death ray
     */
    public ArrayList<Position> getDeathRayPath() {
        return deathRayPath;
    }
    /**
     * Getter for drawPoisonDamage.
     * @return true if the poison damage should be drawn, false otherwise
     */
    public boolean isDrawPoisonDamage() {
        return drawPoisonDamage;
    }
    /**
     * Getter for poisonYCoord.
     * @return the Y coordinate of the poison damage
     */
    public int getPoisonYCoord() {
        return poisonYCoord;
    }
    /**
     * Getter for isCharging.
     * @return true if the enemy is charging, false otherwise
     */
    public boolean isCharging() {
        return isCharging;
    }
    /**
     * Getter for chargingCellDistance.
     * @return the distance of the charging cell
     */
    public int getChargingCellDistance() {
        return chargingCellDistance;
    }
    /**
     * Getter for squareWidth.
     * @return the width of each square in the grid
     */
    public int getSquareWidth() {
        return squareWidth;
    }
    /**
     * Getter for squareHeight.
     * @return the height of each square in the grid
     */
    public int getSquareHeight() {
        return squareHeight;
    }

    // Builder class
    public static class Builder {
        /**
         * Position of the player.
         */
        private Position player;
        /**
         * Position of the enemy.
         */
        private Position enemy;
        /**
         * Position of the flame.
         */
        private Position flame;
        /**
         * Size of the flame.
         */
        private int flameSize;
        /**
         * deciding whether to draw or not the player.
         */
        private boolean drawPlayer;
        /**
         * deciding whether to draw or not the enemy.
         */
        private boolean drawEnemy;
        /**
         * deciding whether to draw or not the flame.
         */
        private boolean drawFlame;
        /**
         * deciding whether to draw or not the poison.
         */
        private boolean drawPoison;
        /**
         * Size range of the player.
         */
        private int playerRange;
        /**
         * Size range of the enemy.
         */
        private int enemyRange;
        /**
         * checking if the game is over.
         */
        private boolean isGameOver;
        /**
         * Position of who died (player or enemy).
         */
        private Position whoDied;
        /**
         * deciding whether to draw or not the boss ray attack.
         */
        private boolean drawBossRayAttack;
        /**
         * Path of the death ray (if any).
         */
        private ArrayList<Position> deathRayPath;
        /**
         * deciding whether to draw or not the poison damage.
         */
        private boolean drawPoisonDamage;
        /**
         * Y coordinate of the poison damage.
         */
        private int poisonYCoord;
        /**
         * deciding whether the enemy is charging an attack.
         */
        private boolean isCharging;
        /**
         * Distance of the charging " block " of the enemy.
         */
        private int chargingCellDistance;
        /**
         * Width of each square in the grid.
         */
        private int squareWidth;
        /**
         * Height of each square in the grid.
         */
        private int squareHeight;

        public Builder player(Position player) {
            this.player = player;
            return this;
        }
        public Builder enemy(Position enemy) {
            this.enemy = enemy;
            return this;
        }
        public Builder flame(Position flame) {
            this.flame = flame;
            return this;
        }
        public Builder flameSize(int flameSize) {
            this.flameSize = flameSize;
            return this;
        }
        public Builder drawPlayer(boolean drawPlayer) {
            this.drawPlayer = drawPlayer;
            return this;
        }
        public Builder drawEnemy(boolean drawEnemy) {
            this.drawEnemy = drawEnemy;
            return this;
        }
        public Builder drawFlame(boolean drawFlame) {
            this.drawFlame = drawFlame;
            return this;
        }
        public Builder drawPoison(boolean drawPoison) {
            this.drawPoison = drawPoison;
            return this;
        }
        public Builder playerRange(int playerRange) {
            this.playerRange = playerRange;
            return this;
        }
        public Builder enemyRange(int enemyRange) {
            this.enemyRange = enemyRange;
            return this;
        }
        public Builder isGameOver(boolean isGameOver) {
            this.isGameOver = isGameOver;
            return this;
        }
        public Builder whoDied(Position whoDied) {
            this.whoDied = whoDied;
            return this;
        }
        public Builder drawBossRayAttack(boolean drawBossRayAttack) {
            this.drawBossRayAttack = drawBossRayAttack;
            return this;
        }
        public Builder deathRayPath(ArrayList<Position> deathRayPath) {
            this.deathRayPath = deathRayPath;
            return this;
        }
        public Builder drawPoisonDamage(boolean drawPoisonDamage) {
            this.drawPoisonDamage = drawPoisonDamage;
            return this;
        }
        public Builder poisonYCoord(int poisonYCoord) {
            this.poisonYCoord = poisonYCoord;
            return this;
        }
        public Builder isCharging(boolean isCharging) {
            this.isCharging = isCharging;
            return this;
        }
        public Builder chargingCellDistance(int chargingCellDistance) {
            this.chargingCellDistance = chargingCellDistance;
            return this;
        }
        public Builder squareWidth(int squareWidth) {
            this.squareWidth = squareWidth;
            return this;
        }
        public Builder squareHeight(int squareHeight) {
            this.squareHeight = squareHeight;
            return this;
        }

        public RedrawContext build() {
            return new RedrawContext(this);
        }
    }
}