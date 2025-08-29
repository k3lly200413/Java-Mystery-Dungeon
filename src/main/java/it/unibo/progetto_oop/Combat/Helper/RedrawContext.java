package it.unibo.progetto_oop.Combat.Helper;

import java.util.ArrayList;

import it.unibo.progetto_oop.Combat.Position.Position;

public final class RedrawContext {
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
    /**
     * Position of who is poisoned (player or enemy).
     */
    private Position whoIsPoisoned;

    private RedrawContext(final Builder builder) {
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
        this.whoIsPoisoned = builder.whoIsPoisoned;
    }

    // Getters

    /**
     * Getter for player position.
     *
     * @return the player position
     */
    public Position getPlayer() {
        return player;
    }

    /**
     * Getter for enemy position.
     *
     * @return the enemy position
     */
    public Position getEnemy() {
        return enemy;
    }

    /**
     * Getter for flame position.
     *
     * @return the flame position
     */
    public Position getFlame() {
        return flame;
    }

    /**
     * Getter for flame size.
     *
     * @return the flame size
     */
    public int getFlameSize() {
        return flameSize;
    }

    /**
     * Getter for drawPlayer.
     *
     * @return true if the player should be drawn, false otherwise
     */
    public boolean isDrawPlayer() {
        return drawPlayer;
    }

    /**
     * Getter for drawEnemy.
     *
     * @return true if the enemy should be drawn, false otherwise
     */
    public boolean isDrawEnemy() {
        return drawEnemy;
    }

    /**
     * Getter for drawFlame.
     *
     * @return true if the flame should be drawn, false otherwise
     */
    public boolean isDrawFlame() {
        return drawFlame;
    }

    /**
     * Getter for drawPoison.
     *
     * @return true if the poison should be drawn, false otherwise
     */
    public boolean isDrawPoison() {
        return drawPoison;
    }

    /**
     * Getter for playerRange.
     *
     * @return the player range
     */
    public int getPlayerRange() {
        return playerRange;
    }

    /**
     * Getter for enemyRange.
     *
     * @return the enemy range
     */
    public int getEnemyRange() {
        return enemyRange;
    }

    /**
     * Getter for isGameOver.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Getter for whoDied.
     *
     * @return the position of who died
     */
    public Position getWhoDied() {
        return whoDied;
    }

    /**
     * Getter for drawBossRayAttack.
     *
     * @return true if the boss ray attack should be drawn, false otherwise
     */
    public boolean isDrawBossRayAttack() {
        return drawBossRayAttack;
    }

    /**
     * Getter for deathRayPath.
     *
     * @return the path of the death ray
     */
    public ArrayList<Position> getDeathRayPath() {
        return deathRayPath;
    }

    /**
     * Getter for drawPoisonDamage.
     *
     * @return true if the poison damage should be drawn, false otherwise
     */
    public boolean isDrawPoisonDamage() {
        return drawPoisonDamage;
    }

    /**
     * Getter for poisonYCoord.
     *
     * @return the Y coordinate of the poison damage
     */
    public int getPoisonYCoord() {
        return poisonYCoord;
    }

    /**
     * Getter for isCharging.
     *
     * @return true if the enemy is charging, false otherwise
     */
    public boolean isCharging() {
        return isCharging;
    }

    /**
     * Getter for chargingCellDistance.
     *
     * @return the distance of the charging cell
     */
    public int getChargingCellDistance() {
        return chargingCellDistance;
    }

    /**
     * Getter for squareWidth.
     *
     * @return the width of each square in the grid
     */
    public int getSquareWidth() {
        return squareWidth;
    }

    /**
     * Getter for squareHeight.
     *
     * @return the height of each square in the grid
     */
    public int getSquareHeight() {
        return squareHeight;
    }
    /**
     * Getter for whoIsPoisoned.
     * @return the position of who is poisoned
     */
    public Position getWhoIsPoisoned() {
        return whoIsPoisoned;
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
        /**
         * Position of who is poisoned (player or enemy).
         */
        private Position whoIsPoisoned;


        /**
         * Builder for the player position.
         *
         * @param playerPosition the position of the player
         * @return the builder
         */
        public Builder player(final Position playerPosition) {
            this.player = playerPosition;
            return this;
        }

        /**
         * Builder for the enemy position.
         *
         * @param enemyPosition the position of the enemy
         * @return the builder
         */
        public Builder enemy(final Position enemyPosition) {
            this.enemy = enemyPosition;
            return this;
        }

        /**
         * Builder for the flame position.
         *
         * @param flamePosition the position of the flame
         * @return the builder
         */
        public Builder flame(final Position flamePosition) {
            this.flame = flamePosition;
            return this;
        }

        /**
         * Builder for the flame size.
         *
         * @param flameSizeInt the size of the flame
         * @return the builder
         */
        public Builder flameSize(final int flameSizeInt) {
            this.flameSize = flameSizeInt;
            return this;
        }

        /**
         * Builder for the player drawing.
         *
         * @param drawPlayerboolean whether to draw the player
         * @return the builder
         */
        public Builder drawPlayer(final boolean drawPlayerboolean) {
            this.drawPlayer = drawPlayerboolean;
            return this;
        }

        /**
         * Builder for the enemy drawing.
         *
         * @param drawEnemyboolean whether to draw the enemy
         * @return the builder
         */
        public Builder drawEnemy(final boolean drawEnemyboolean) {
            this.drawEnemy = drawEnemyboolean;
            return this;
        }

        /**
         * Builder for the flame drawing.
         *
         * @param drawFlameboolean whether to draw the flame
         * @return the builder
         */
        public Builder drawFlame(final boolean drawFlameboolean) {
            this.drawFlame = drawFlameboolean;
            return this;
        }

        /**
         * Builder for the poison drawing.
         *
         * @param drawPoisonboolean whether to draw the poison
         * @return the builder
         */
        public Builder drawPoison(final boolean drawPoisonboolean) {
            this.drawPoison = drawPoisonboolean;
            return this;
        }

        /**
         * Builder for the player range.
         *
         * @param playerRangeInt the range of the player
         * @return the builder
         */
        public Builder playerRange(final int playerRangeInt) {
            this.playerRange = playerRangeInt;
            return this;
        }

        /**
         * Builder for the enemy range.
         *
         * @param enemyRangeInt the range of the enemy
         * @return the builder
         */
        public Builder enemyRange(final int enemyRangeInt) {
            this.enemyRange = enemyRangeInt;
            return this;
        }

        /**
         * Builder for the game over state.
         *
         * @param isGameOverboolean whether the game is over
         * @return the builder
         */
        public Builder isGameOver(final boolean isGameOverboolean) {
            this.isGameOver = isGameOverboolean;
            return this;
        }

        /**
         * Builder for the who died position.
         *
         * @param whoDiedPosition the position of the character who died
         * @return the builder
         */
        public Builder whoDied(final Position whoDiedPosition) {
            this.whoDied = whoDiedPosition;
            return this;
        }

        /**
         * Builder for the boss ray attack drawing.
         *
         * @param drawBossRayAttackboolean whether to draw the boss ray attack
         * @return the builder
         */
        public Builder drawBossRayAttack(
            final boolean drawBossRayAttackboolean) {
                this.drawBossRayAttack = drawBossRayAttackboolean;
                return this;
        }

        /**
         * Builder for the death ray path.
         *
         * @param deathRayPathArrayList the path of the death ray
         * @return the builder
         */
        public Builder deathRayPath(
            final ArrayList<Position> deathRayPathArrayList) {
                this.deathRayPath = deathRayPathArrayList;
                return this;
        }

        /**
         * Builder for the poison damage drawing.
         *
         * @param drawPoisonDamageboolean whether to draw the poison damage
         * @return the builder
         */
        public Builder drawPoisonDamage(final boolean drawPoisonDamageboolean) {
            this.drawPoisonDamage = drawPoisonDamageboolean;
            return this;
        }

        /**
         * Builder for the poison Y coordinate.
         *
         * @param poisonYCoordInt the Y coordinate of the poison
         * @return the builder
         */
        public Builder poisonYCoord(final int poisonYCoordInt) {
            this.poisonYCoord = poisonYCoordInt;
            return this;
        }

        /**
         * Builder for the charging state.
         *
         * @param isChargingboolean whether the character is charging
         * @return the builder
         */
        public Builder isCharging(final boolean isChargingboolean) {
            this.isCharging = isChargingboolean;
            return this;
        }

        /**
         * Builder for the charging cell distance.
         *
         * @param chargingCellDistanceInt the charging cell distance
         * @return the builder
         */
        public Builder chargingCellDistance(final int chargingCellDistanceInt) {
            this.chargingCellDistance = chargingCellDistanceInt;
            return this;
        }

        /**
         * Builder for the square width.
         *
         * @param squareWidthInt the width of the square
         * @return the builder
         */
        public Builder squareWidth(final int squareWidthInt) {
            this.squareWidth = squareWidthInt;
            return this;
        }

        /**
         * Builder for the square height.
         *
         * @param squareHeightInt the height of the square
         * @return the builder
         */
        public Builder squareHeight(final int squareHeightInt) {
            this.squareHeight = squareHeightInt;
            return this;
        }
        /**
         * Builder for the who is poisoned position.
         *
         * @param whoIsPoisonedPosition position of who is poisoned
         * @return the builder
         */
        public Builder whoIsPoisoned(
            final Position whoIsPoisonedPosition) {
            this.whoIsPoisoned = whoIsPoisonedPosition;
            return this;
        }
        /**
         * Builder for the square height.
         * @return the builder
         */
        public RedrawContext build() {
            return new RedrawContext(this);
        }
    }
}
