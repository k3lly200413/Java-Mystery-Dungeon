package it.unibo.progetto_oop.overworld.mvc.generation_entities;

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
    /** The default power level of the player. */
    private static final int PLAYER_POWER = 100;
    /** The default health points of the enemy. */
    private static final int ENEMY_HP = 100;
    /** The default power level of the enemy. */
    private static final int ENEMY_POWER = 20;
    /** The default health multiplier for the boss. */
    private static final int BOSS_HP_MULT = 3;
    /** The default power multiplier for the boss. */
    private static final int BOSS_POWER_MULT = 2;

    /** Default configuration. */
    public static final EntityStatsConfig DEFAULT = builder().build();

    /** Entry-point for the builder. */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        /** The maximum health points of the player. */
        private int playerMaxHp = PLAYER_MAX_HP;
        /** The stamina level of the player. */
        private int playerStamina = PLAYER_STAMINA;
        /** The power level of the player. */
        private int playerPower = PLAYER_POWER;
        /** The health points of the enemy. */
        private int enemyHp = ENEMY_HP;
        /** The power level of the enemy. */
        private int enemyPower = ENEMY_POWER;
        /** The health multiplier for the boss. */
        private int bossHpMultiplier = BOSS_HP_MULT;
        /** The power multiplier for the boss. */
        private int bossPowerMultiplier = BOSS_POWER_MULT;

        /**
         * Sets the maximum health points of the player.
         * @param v the maximum health points value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerMaxHp(final int v) {
            this.playerMaxHp = v;
            return this;
        }

        /**
         * Sets the stamina level of the player.
         * @param v the stamina value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerStamina(final int v) {
            this.playerStamina = v;
            return this;
        }

        /**
         * Sets the power level of the player.
         * @param v the power value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder playerPower(final int v) {
            this.playerPower = v;
            return this;
        }

        /**
         * Sets the health points of the enemy.
         * @param v the health points value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder enemyHp(final int v) {
            this.enemyHp = v;
            return this;
        }

        /**
         * Sets the power level of the enemy.
         * @param v the power value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder enemyPower(final int v) {
            this.enemyPower = v;
            return this;
        }

        /**
         * Sets the health multiplier for the boss.
         * @param v the multiplier value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder bossHpMultiplier(final int v) {
            this.bossHpMultiplier = v;
            return this;
        }

        /**
         * Sets the power multiplier for the boss.
         * @param v the multiplier value to set.
         * @return the Builder instance for method chaining.
         */
        public Builder bossPowerMultiplier(final int v) {
            this.bossPowerMultiplier = v;
            return this;
        }

        /**
         * Builds and validates an EntityStatsConfig instance.
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
