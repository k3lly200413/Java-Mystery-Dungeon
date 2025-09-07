package it.unibo.progetto_oop;

import it.unibo.progetto_oop.Overworld.PlayGround.GameLuncher;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        GameLuncher app = new GameLuncher();
        app.start();
    }
}
