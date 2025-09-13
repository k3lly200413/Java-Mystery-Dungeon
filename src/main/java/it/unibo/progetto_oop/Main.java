package it.unibo.progetto_oop;

/** the main class of the game. */
public final class Main {
    private Main() {
    }

    /**
     * Main method to start the game.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final JavaMysteryDungeon app = new JavaMysteryDungeon();
        app.start();
    }
}
