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

    private static final Color TITLE_COLOR = Color.RED;
    private static final Color SUBTITLE_COLOR = Color.WHITE; // meglio bianco per contrasto
    private static final float TITLE_FONT_SIZE = 64f;
    private static final float SUBTITLE_FONT_SIZE = 28f;
    private static final int BUTTON_FONT_SIZE = 28;
    private static final int BUTTON_WIDTH = 360;
    private static final int BUTTON_HEIGHT = 56;
    private static final int VERTICAL_SPACING = 12;
    private static final int EXTRA_VERTICAL_SPACING = 24;

    private final JButton restartButton;
    private final Image backgroundImage;

    public GameOverPanel(final Runnable onRestart) {
        var url = getClass().getResource("/spritesOverWorld/gameOverBackground.png");
        backgroundImage = new ImageIcon(url).getImage();

        setOpaque(false); // importante: non dipingere background piatto
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, EXTRA_VERTICAL_SPACING));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(VERTICAL_SPACING, VERTICAL_SPACING,
                VERTICAL_SPACING, VERTICAL_SPACING);
        gbc.fill = GridBagConstraints.NONE;

        // Titolo
        final JLabel title = new JLabel("GAME OVER");
        title.setForeground(TITLE_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        add(title, gbc);

        // Sottotitolo
        gbc.gridy = 1;
        final JLabel subtitle = new JLabel("You're dead");
        subtitle.setForeground(SUBTITLE_COLOR);
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, SUBTITLE_FONT_SIZE));
        add(subtitle, gbc);

        // Spazio
        gbc.gridy = 2;
        gbc.insets = new Insets(EXTRA_VERTICAL_SPACING, VERTICAL_SPACING,
                EXTRA_VERTICAL_SPACING, VERTICAL_SPACING);
        add(Box.createVerticalStrut(VERTICAL_SPACING), gbc);

        // Bottone Restart
        gbc.gridy = 3;
        restartButton = new JButton("Restart");
        restartButton.setFocusPainted(false);
        restartButton.setFont(new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(63, 46, 30));
        restartButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        restartButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        restartButton.addActionListener(e -> {
            if (onRestart != null) {
                onRestart.run();
            }
        });
        add(restartButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna immagine di sfondo scalata a tutto il pannello
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
