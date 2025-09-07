package it.unibo.progetto_oop.Overworld.PlayGround.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class GameStartView extends JPanel {

    private final JButton startButton = new JButton("Start");
    private Runnable onStart = () -> { };

    public GameStartView() {
        setLayout(new BorderLayout());

        // Titolo
        JLabel title = new JLabel("Java Mystery Dungeon", SwingConstants.CENTER);
        JPanel center = new JPanel();
        center.add(title);

        // Barra bottoni
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton.addActionListener(e -> onStart.run());
        bottom.add(startButton);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    public void setOnStart(final Runnable action) {
        this.onStart = Objects.requireNonNull(action);
    }

}
