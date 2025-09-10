package it.unibo.progetto_oop;

public final class Main {
    private Main() {
    }

    /**
     * Main method to start the game.
     * 
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        GameLuncher app = new GameLuncher();
        app.start();
    }
}
