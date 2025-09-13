package it.unibo.progetto_oop.overworld.playground.view.game_start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The GameStartView class represents the initial view of the game,
 * displaying a title and a start button.
 * It extends JPanel and uses a BorderLayout to arrange components.
 */
public final class GameStartView extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The font size for the title label.
     */
    private static final int TITLE_FONT_SIZE = 55;

    /**
     * The font size for the button text.
     */
    private static final int FONT_BUTTON = 28;

    /**
     * The red component of the background color.
     */
    private static final int R = 63;

    /**
     * The green component of the background color.
     */
    private static final int G = 46;
    /**
     * The blue component of the background color.
     */
    private static final int B = 30;

    /**
     * The thickness of the button border.
     */
    private static final int THICKNESS = 3;

    /**
     * The button used to start the game.
     */
    private final JButton startButton;

    /**
     * The background image displayed in the view.
     */
    private transient Image backgroundImage;

    /**
     * The title label displayed at the top of the view.
     */
    private final JLabel title;

    /**
     * The action to be executed when the start button is pressed.
     */
    private transient Runnable onStart = () -> { };

    /**
     * Constructs a new GameStartView,
     * initializing the layout, title, and start button.
     */
    public GameStartView() {
        setLayout(new BorderLayout());

        final var resourcePath = "/spritesOverWorld/startBackground.png";
        final var url = getClass().getResource(resourcePath);
        this.backgroundImage = url != null ? new ImageIcon(url).getImage() : null;

        // title
        this.title = new JLabel("Java Mystery Dungeon", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, TITLE_FONT_SIZE));

        // button
        this.startButton = new JButton("  Start  ");
        startButton.setFont(new Font("SansSerif", Font.BOLD, FONT_BUTTON));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(R, G, B));
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, THICKNESS, true));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> onStart.run());

        add(title, BorderLayout.NORTH);

        final JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(startButton);
        add(center, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Sets the action to be executed when the start button is pressed.
     *
     * @param action the Runnable action to execute
     * @throws NullPointerException if the action is null
     */
    public void setOnStart(final Runnable action) {
        this.onStart = Objects.requireNonNull(action);
    }
}
