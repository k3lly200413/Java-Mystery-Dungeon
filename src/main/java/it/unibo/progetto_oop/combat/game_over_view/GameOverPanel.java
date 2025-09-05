package it.unibo.progetto_oop.combat.game_over_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Schermata Game Over riutilizzabile.
 * Usa GridBagLayout per centrare tutto, disegna una cornice,
 * e collega Invio/Space al bottone "Ricomincia".
 */
public class GameOverPanel extends JPanel {

    // Colori e font (puoi ajustarli a piacere)
    private static final Color TITLE_COLOR = Color.BLUE;

    public GameOverPanel() {
        setOpaque(true);
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(24, 24, 24, 24));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.NONE;

        // Titolo
        final JLabel title = new JLabel("GAME OVER");
        title.setForeground(TITLE_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 64f));
        add(title, gbc);


    }

    /* ---- Demo standalone (puoi eliminarla nel tuo progetto) ---- */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Game Over");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setMinimumSize(new Dimension(900, 600));


            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

