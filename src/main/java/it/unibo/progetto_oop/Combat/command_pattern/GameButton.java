package it.unibo.progetto_oop.combat.command_pattern;

import java.util.List;

<<<<<<< HEAD:src/main/java/it/unibo/progetto_oop/Combat/CommandPattern/GameButton.java
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
=======
import it.unibo.progetto_oop.combat.position.Position;
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa:src/main/java/it/unibo/progetto_oop/Combat/command_pattern/GameButton.java

/**
 * Interface to be used by buttons.
 * Represents the action each button will be able to execute
 *
 */
public interface GameButton {
    /**
     * Executes a command.
     * @return A list of positions, used for animation.
     */
    List<Position> execute();
}
