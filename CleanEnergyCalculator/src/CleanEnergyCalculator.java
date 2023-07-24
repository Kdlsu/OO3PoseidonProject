import javax.swing.*;

public class CleanEnergyCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnergySelectionWindow energySelectionWindow = new EnergySelectionWindow();
            energySelectionWindow.showWindow();
        });
    }
}
