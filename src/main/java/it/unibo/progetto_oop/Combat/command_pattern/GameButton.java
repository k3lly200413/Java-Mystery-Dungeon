<<<<<<< HEAD
package it.unibo.progetto_oop.combat.command_pattern;
=======
package it.unibo.progetto_oop.Combat.command_pattern;
>>>>>>> CombatTransition

import java.util.List;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

/**
 * Interface to be used by buttons.
 * Represents the action each button will be able to execute
 *
 */
@FunctionalInterface
public interface GameButton {
    /**
     * Executes a command.
     *
     * @return A list of positions, used for animation.
     */
    List<Position> execute();
}
