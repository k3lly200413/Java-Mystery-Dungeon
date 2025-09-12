package it.unibo.progetto_oop.combat.game_over_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * A JPanel representing the Game Over screen with a background image.
 */
public class GameOverPanel extends JPanel {

    /** RGB components for the button background color. */
    private static final int RED = 63;

    /** RGB components for the button background color. */
    private static final int GREEN = 46;

    /** RGB components for the button background color. */
    private static final int BLUE = 30;

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /** GridBagConstraints gridx value for layout. */
    private static final int GBC_GRIDY = 3;

    /** Color of the title text. */
    private static final Color TITLE_COLOR = Color.RED;

    /** Color of the subtitle text. */
    private static final Color SUBTITLE_COLOR = Color.WHITE;

    /** Font sizes and dimensions. */
    private static final float TITLE_FONT_SIZE = 64f;

    /** Font size for the subtitle text. */
    private static final float SUBTITLE_FONT_SIZE = 28f;

    /** Font size for the button text. */
    private static final int BUTTON_FONT_SIZE = 28;

    /** Button dimensions and spacing. */
    private static final int BUTTON_WIDTH = 360;

    /** Button height. */
    private static final int BUTTON_HEIGHT = 56;

    /** Vertical spacing between components. */
    private static final int VERTICAL_SPACING = 12;

    /** Extra vertical spacing for components. */
    private static final int EXTRA_VERTICAL_SPACING = 24;

    /** Button for restarting the game. */
    private final JButton restartButton;

    /** Background image for the game over panel. */
    private final transient Image backgroundImage;

    /**
     * Constructs a GameOverPanel with a background image and a restart button.
     *
     * @param onRestart a Runnable to execute when the restart button is clicked
     */
    public GameOverPanel(final Runnable onRestart) {
        final var url = GameOverPanel.class.getResource(
            "/spritesOverWorld/gameOverBackground.png");
        backgroundImage = new ImageIcon(url).getImage();

        super.setOpaque(false);
        super.setLayout(new GridBagLayout());
        super.setBorder(new EmptyBorder(
            EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(VERTICAL_SPACING, VERTICAL_SPACING,
                VERTICAL_SPACING, VERTICAL_SPACING);
        gbc.fill = GridBagConstraints.NONE;

        final JLabel title = new JLabel("GAME  OVER");
        title.setForeground(TITLE_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        super.add(title, gbc);

        gbc.gridy = 1;
        final JLabel subtitle = new JLabel("You're dead");
        subtitle.setForeground(SUBTITLE_COLOR);
        subtitle.setFont(subtitle.getFont().deriveFont(
            Font.PLAIN, SUBTITLE_FONT_SIZE));
        super.add(subtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(EXTRA_VERTICAL_SPACING, VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, VERTICAL_SPACING);
        super.add(Box.createVerticalStrut(VERTICAL_SPACING), gbc);

        gbc.gridy = GBC_GRIDY;
        restartButton = new JButton("Restart");
        restartButton.setFocusPainted(false);
        restartButton.setFont(
            new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(RED, GREEN, BLUE));
        restartButton.setBorder(
            BorderFactory.createLineBorder(Color.WHITE, GBC_GRIDY, true));
        restartButton.setPreferredSize(
            new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        restartButton.addActionListener(e -> {
            if (onRestart != null) {
                onRestart.run();
            }
        });
        super.add(restartButton, gbc);
    }

    /**
     * Paints the component with the background image.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
