package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface CombatState {
    void handlePhysicalAttackInput(CombatController context);
    void handleLongRangeAttackInput(CombatController context, boolean isPoison);
    void handleInfoInput(CombatController context);
    void handleBackInput(CombatController context);
    void handleBagInput(CombatController context); // Add if implementing
    void handleRunInput(CombatController context);  // Add if implementing

}
