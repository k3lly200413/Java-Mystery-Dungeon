package it.unibo.progetto_oop.combat.win_view;

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
public class WinPanel extends JPanel {

    /** Color of the title text. */
    private static final Color TITLE_COLOR = Color.WHITE;

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
    private final Image backgroundImage;

    /** RGB components for the button background color. */
    private final int red = 63;

    /** RGB components for the button background color. */
    private final int green = 46;

    /** RGB components for the button background color. */
    private final int blue = 30;

    /** GridBagConstraints gridx value for layout. */
    private final int gbcGridy = 3;

    /**
     * Constructs a GameOverPanel with a background image and a restart button.
     *
     * @param onRestart a Runnable to execute when the restart button is clicked
     */
    public WinPanel(final Runnable onRestart) {
        final var url = getClass().getResource(
            "/spritesOverWorld/winBackground.png");
        backgroundImage = new ImageIcon(url).getImage();

        setOpaque(false); // importante: non dipingere background piatto
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(
            EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(VERTICAL_SPACING, VERTICAL_SPACING,
                VERTICAL_SPACING, VERTICAL_SPACING);
        gbc.fill = GridBagConstraints.NONE;

        // Titolo
        final JLabel title = new JLabel("WIN!");
        title.setForeground(TITLE_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        add(title, gbc);

        // Sottotitolo
        gbc.gridy = 1;
        final JLabel subtitle = new JLabel(
            "You beat the boss!");
        subtitle.setForeground(SUBTITLE_COLOR);
        subtitle.setFont(subtitle.getFont().deriveFont(
            Font.PLAIN, SUBTITLE_FONT_SIZE));
        add(subtitle, gbc);

        // Spazio
        gbc.gridy = 2;
        gbc.insets = new Insets(EXTRA_VERTICAL_SPACING, VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, VERTICAL_SPACING);
        add(Box.createVerticalStrut(VERTICAL_SPACING), gbc);

        // Bottone Restart
        gbc.gridy = gbcGridy;
        restartButton = new JButton("Restart");
        restartButton.setFocusPainted(false);
        restartButton.setFont(
            new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(red, green, blue));
        restartButton.setBorder(
            BorderFactory.createLineBorder(Color.WHITE, gbcGridy, true));
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
     * Paints the component with the background image.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // Disegna immagine di sfondo scalata a tutto il pannello
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
