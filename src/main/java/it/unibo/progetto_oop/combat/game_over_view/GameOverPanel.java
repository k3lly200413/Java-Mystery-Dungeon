package it.unibo.progetto_oop.combat.game_over_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


/**
 * A JPanel representing the Game Over screen.
 * This panel is designed to be reusable and can be integrated
 * into different parts of the application where a game over
 * scenario needs to be displayed.
 */
public class GameOverPanel extends JPanel {

    /** Color used for the title text. */
    private static final Color TITLE_COLOR = Color.RED;

    /** Color used for the subtitle text. */
    private static final Color SUBTITLE_COLOR = Color.BLACK;

    /** Background color of the panel. */
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    /** Font size of the title label. */
    private static final float TITLE_FONT_SIZE = 64f;

    /** Font size of the subtitle label. */
    private static final float SUBTITLE_FONT_SIZE = 28f;

    /** Font size of the restart button label. */
    private static final float BUTTON_FONT_SIZE = 22f;

    /** Preferred width of the restart button. */
    private static final int BUTTON_WIDTH = 360;

    /** Preferred height of the restart button. */
    private static final int BUTTON_HEIGHT = 56;

    /** Vertical spacing between components. */
    private static final int VERTICAL_SPACING = 12;

    /** Extra vertical spacing before the button. */
    private static final int EXTRA_VERTICAL_SPACING = 24;

    /** Width of the frame for testing purposes. */
    private static final int FRAME_WIDTH = 900;

    /** Height of the frame for testing purposes. */
    private static final int FRAME_HEIGHT = 600;

    /** Button to restart the game. */
    private final JButton restartButton;

    /**
     * Constructs a GameOverPanel with a restart button.
     * The provided Runnable is executed when the restart button is clicked.
     * @param onRestart a Runnable
     * to be executed when the restart button is clicked
     */
    public GameOverPanel(final Runnable onRestart) {
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(EXTRA_VERTICAL_SPACING,
        EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING,
        EXTRA_VERTICAL_SPACING));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(VERTICAL_SPACING, VERTICAL_SPACING,
        VERTICAL_SPACING, VERTICAL_SPACING);
        gbc.fill = GridBagConstraints.NONE;

        // Title
        final JLabel title = new JLabel("GAME OVER");
        title.setForeground(TITLE_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        add(title, gbc);

        // Subtitle
        gbc.gridy = 1;
        final JLabel subtitle = new JLabel("You're dead");
        subtitle.setForeground(SUBTITLE_COLOR);
        subtitle.setFont(subtitle.getFont().
        deriveFont(Font.PLAIN, SUBTITLE_FONT_SIZE));
        add(subtitle, gbc);

        // Spacer
        gbc.gridy = 2;
        gbc.insets = new Insets(EXTRA_VERTICAL_SPACING, VERTICAL_SPACING,
        EXTRA_VERTICAL_SPACING, VERTICAL_SPACING);
        add(Box.createVerticalStrut(VERTICAL_SPACING), gbc);

        // Restart Button
        gbc.gridy = 3;
        restartButton = new JButton("Restart");
        restartButton.setFocusPainted(false);
        restartButton.setFont(restartButton.getFont().
        deriveFont(Font.BOLD, BUTTON_FONT_SIZE));
        restartButton.setPreferredSize(
            new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        restartButton.addActionListener(e -> {
            if (onRestart != null) {
                onRestart.run();
            }
        });
        add(restartButton, gbc);


    }

    /**
     * Main method for testing the GameOverPanel independently.
     * Creates a JFrame to host the panel and
     * demonstrates its appearance and functionality.
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Java Mystery Dungeon - Game Over ");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

            GameOverPanel panel = new GameOverPanel(() -> {
                    System.out.println("Restart button clicked!");
            });
            f.setContentPane(panel);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

