package it.unibo.progetto_oop.combat.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryViewTest {

    private InventoryView inventoryView;
    private Inventory mockInventory;

    @BeforeEach
    void setUp() {
        // Mock dell'inventario
        mockInventory = mock(Inventory.class);

        // Lista di item fittizi
        Item item1 = new Item("Spada", "Una spada affilata");
        Item item2 = new Item("Scudo", "Scudo robusto");
        Item item3 = new Item("Pozione", "Pozione curativa");

        Map<Item, Integer> itemsMap = new LinkedHashMap<>();
        itemsMap.put(item1, 1);
        itemsMap.put(item2, 1);
        itemsMap.put(item3, 1);

        // Configura il mock
        when(mockInventory.getFullInventory()).thenReturn(itemsMap);

        // Crea InventoryView con pannello mock
        inventoryView = new InventoryView();
        inventoryView.setInventory(mockInventory);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(
                InventoryView.VIEWPORT_HEIGHT_CELLS,
                InventoryView.VIEWPORT_WIDTH_CELLS));
        inventoryView.setGridPanel(gridPanel);
    }

    @Test
    void testPopulateGridWithMock() {
        // Chiama il metodo
        inventoryView.populateGrid();

        JPanel panel = inventoryView.getGridPanel();
        int expectedCells = InventoryView.VIEWPORT_HEIGHT_CELLS
                * InventoryView.VIEWPORT_WIDTH_CELLS;
        assertEquals(expectedCells, panel.getComponentCount(),
                "Il numero di celle della griglia dovrebbe essere corretto");

        // Controlla che almeno un bottone abbia un testo (cioè un item)
        boolean foundItemButton = Arrays.stream(panel.getComponents())
                .filter(c -> c instanceof JButton)
                .map(c -> (JButton) c)
                .anyMatch(btn -> btn.getText() != null && !btn.getText().isEmpty());

        assertTrue(foundItemButton, "Almeno un item button dovrebbe essere presente");

        // Verifica che il mock sia stato chiamato
        verify(mockInventory, times(1)).getFullInventory();
    }
}
