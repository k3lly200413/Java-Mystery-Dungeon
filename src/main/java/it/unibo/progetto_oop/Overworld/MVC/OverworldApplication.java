package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import it.unibo.progetto_oop.Combat.Inventory.*;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class OverworldApplication {
    // MVC components
    private OverworldModel model;
    private OverworldView view;
    private OverworldController controller;

    private Inventory inventory;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OverworldApplication gameApp = new OverworldApplication();
            gameApp.start();
        });
    }

    private void start() {
        Player player = new Player(100); // Example max HP
        List<Item> items = new ArrayList<>();
        this.inventory = new Inventory();

        // create model
        this.model = new OverworldModel(player, items, inventory);
        
        // create view
        this.view = new OverworldView();

        // create controller
        this.controller = new OverworldController(model, view);
    }

    public OverworldModel getOverworldModel(){
        return this.model;
    }

    public OverworldView getView(){
        return this.view;
    }
}
