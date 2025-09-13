package it.unibo.progetto_oop.overworld.mvc.generation_entities;

/**
 * Configuration record for entity stats in the game.
 * Includes stats for the player, regular enemies, and bosses.
 *
 * @param playerMaxHp        the maximum health points of the player
 * @param playerStamina      the stamina level of the player
 * @param playerPower        the power level of the player
 * @param enemyHp            the health points of a regular enemy
 * @param enemyPower         the power level of a regular enemy
 * @param bossHpMultiplier   the health multiplier for bosses
 * @param bossPowerMultiplier the power multiplier for bosses
 */
public record EntityStatsConfig(
        int playerMaxHp,
        int playerStamina,
        int playerPower,
        int enemyHp,
        int enemyPower,
        int bossHpMultiplier,
        int bossPowerMultiplier
) {
    // default centralizzati (unica fonte di verità)
    /** The default maximum health points of the player. */
    private static final int PLAYER_MAX_HP = 100;
    /** The default stamina level of the player. */
    private static final int PLAYER_STAMINA = 100;
    private static final int PLAYER_POWER = 35;
    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 10;
    private static final int BOSS_HP_MULT = 3;
    private static final int BOSS_POWER_MULT = 2;

    /**
     * Creates a new Builder instance for constructing EntityStatsConfig objects.
     *
     * @return a new Builder instance
     */
    public static final class Builder {

        public static final EntityStatsConfig DEFAULT = builder().build();
        private int playerMaxHp = PLAYER_MAX_HP;
        private int playerStamina = PLAYER_STAMINA;
        private int playerPower = PLAYER_POWER;
        private int enemyHp = ENEMY_HP;
        private int enemyPower = ENEMY_POWER;
        private int bossHpMultiplier = BOSS_HP_MULT;
        private int bossPowerMultiplier = BOSS_POWER_MULT;

        /**
         * Entry-point for the builder.
         *
         * @return new builder
         */
        public static Builder builder() {
            return new Builder();
        }

        /**
         * Sets the maximum health points of the player.
         *
         * @param v the maximum health points value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerMaxHp(final int v) {
            this.playerMaxHp = v;
            return this;
        }

        /**
         * Sets the stamina level of the player.
         *
         * @param v the stamina value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerStamina(final int v) {
            this.playerStamina = v;
            return this;
        }

        /**
         * Sets the power level of the player.
         *
         * @param v the power value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerPower(final int v) {
            this.playerPower = v;
            return this;
        }

        /**
         * Sets the health points of the enemy.
         *
         * @param v the health points value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder enemyHp(final int v) {
            this.enemyHp = v;
            return this;
        }

        /**
         * Sets the power level of the enemy.
         *
         * @param v the power value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder enemyPower(final int v) {
            this.enemyPower = v;
            return this;
        }

        /**
         * Sets the health multiplier for the boss.
         *
         * @param v the multiplier value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder bossHpMultiplier(final int v) {
            this.bossHpMultiplier = v;
            return this;
        }

        /**
         * Sets the power multiplier for the boss.
         *
         * @param v the multiplier value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder bossPowerMultiplier(final int v) {
            this.bossPowerMultiplier = v;
            return this;
        }

        /**
         * Builds and validates an EntityStatsConfig instance.
         *
         * @return a new EntityStatsConfig instance with the specified values.
         * @throws IllegalArgumentException if any of the values are invalid.
         */
        public EntityStatsConfig build() {
            if (playerMaxHp <= 0) {
                throw new IllegalArgumentException("playerMaxHp must be > 0");
            }
            if (playerStamina <= 0) {
                throw new IllegalArgumentException("playerStamina must be > 0");
            }
            if (playerPower <= 0) {
                throw new IllegalArgumentException("playerPower must be > 0");
            }
            if (enemyHp <= 0) {
                throw new IllegalArgumentException("enemyHp must be > 0");
            }
            if (enemyPower <= 0) {
                throw new IllegalArgumentException("enemyPower must be > 0");
            }
            if (bossHpMultiplier <= 0) {
                throw new IllegalArgumentException(
                    "bossHpMultiplier must be > 0");
            }
            if (bossPowerMultiplier <= 0) {
                throw new IllegalArgumentException(
                    "bossPowerMultiplier must be > 0");
            }

            return new EntityStatsConfig(
                playerMaxHp, playerStamina, playerPower,
                enemyHp, enemyPower,
                bossHpMultiplier, bossPowerMultiplier
            );
        }
    }
}
