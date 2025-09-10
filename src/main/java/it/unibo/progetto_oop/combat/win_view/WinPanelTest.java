package it.unibo.progetto_oop.combat.win_view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class WinPanelTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("WinPanel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // dimensione finestra

            // Crea WinPanel con azione sul bottone restart
            WinPanel panel = new WinPanel(() -> {
                System.out.println("Restart clicked!");
                frame.dispose(); // chiudi finestra per test
            });

            frame.setContentPane(panel);
            frame.setLocationRelativeTo(null); // centra sullo schermo
            frame.setVisible(true);
        });
    }
}
