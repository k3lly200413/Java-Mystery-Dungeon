package JMD;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // MODEL
        Floor floor = new Floor();

        // VIEW (celle da 16px, puoi aumentare o ridurre)
        SwingMapView view = new SwingMapView("Mystery Dungeon - Map", 14);

        // CONTROLLER
        MapController controller = new MapController(floor, view);

        // Avvia la UI sul thread Swing
        SwingUtilities.invokeLater(controller::show);
    }
}
