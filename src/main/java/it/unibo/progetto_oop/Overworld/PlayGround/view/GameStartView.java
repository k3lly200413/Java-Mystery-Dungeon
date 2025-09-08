package it.unibo.progetto_oop.Overworld.PlayGround.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class GameStartView extends JPanel {

    private final JButton startButton;
    private final Image backgroundImage;
    private final JLabel title;
    private Runnable onStart = () -> { };

    public GameStartView() {

        // Carica immagine dalle risorse
        var url = getClass().getResource("/spritesOverworld/startBackground.png");
        this.backgroundImage = new ImageIcon(url).getImage();

        setLayout(new BorderLayout());

        // Titolo
        this.title = new JLabel("Java Mystery Dungeon", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 55));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // Bottone
        this.startButton = new JButton("  Start  ");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 28));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(63, 46, 30));
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        startButton.addActionListener(e -> onStart.run());

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(startButton);
        add(center, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setOnStart(final Runnable action) {
        this.onStart = Objects.requireNonNull(action);
    }
}
