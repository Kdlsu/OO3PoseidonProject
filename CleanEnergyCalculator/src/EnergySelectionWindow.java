import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnergySelectionWindow extends JFrame {
    private JComboBox<String> energyTypeComboBox;
    private JButton calculateButton;
    private JTextArea outputTextArea;
    private JTextField specificValueField;
    private JLabel specificInputLabel;
    private JRadioButton perMinuteRadioButton;
    private JRadioButton perHourRadioButton;
    private JRadioButton perDayRadioButton;
    private JRadioButton perMonthRadioButton;
    private JRadioButton perYearRadioButton;
    private ButtonGroup timeButtonGroup;
    private EnergyTypeStatsMap energyStatsMap;

    public EnergySelectionWindow() {
        energyStatsMap = new EnergyTypeStatsMap();
        initializeComponents();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clean Energy Calculator");
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        energyTypeComboBox = new JComboBox<>(new String[]{"Water", "Air", "Solar", "Heat"});
        energyTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSpecificInputLabel();
            }
        });
        add(new JLabel("Select Energy Type:"));
        add(energyTypeComboBox);

        specificInputLabel = new JLabel();
        specificValueField = new JTextField(10);
        updateSpecificInputLabel(); // Update the specific input label initially based on the selected energy
        add(specificInputLabel);
        add(specificValueField);

        perMinuteRadioButton = new JRadioButton("Per Minute");
        perHourRadioButton = new JRadioButton("Per Hour");
        perDayRadioButton = new JRadioButton("Per Day");
        perMonthRadioButton = new JRadioButton("Per Month");
        perYearRadioButton = new JRadioButton("Per Year");

        timeButtonGroup = new ButtonGroup();
        timeButtonGroup.add(perMinuteRadioButton);
        timeButtonGroup.add(perHourRadioButton);
        timeButtonGroup.add(perDayRadioButton);
        timeButtonGroup.add(perMonthRadioButton);
        timeButtonGroup.add(perYearRadioButton);

        perHourRadioButton.setSelected(true);

        JPanel radioPanel = new JPanel(new GridLayout(5, 1));
        radioPanel.add(perMinuteRadioButton);
        radioPanel.add(perHourRadioButton);
        radioPanel.add(perDayRadioButton);
        radioPanel.add(perMonthRadioButton);
        radioPanel.add(perYearRadioButton);
        add(radioPanel);

        calculateButton = new JButton("Calculate Energy");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEnergy = (String) energyTypeComboBox.getSelectedItem();
                double specificValue = Double.parseDouble(specificValueField.getText());
                String timeSelection = getTimeSelection();
                String result = handleEnergyCalculation(selectedEnergy, specificValue, timeSelection);
                outputTextArea.setText(result);
            }
        });
        add(calculateButton);

        outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane);

        JLabel averageEnergyLabel = new JLabel("Average Energy:");
        add(averageEnergyLabel);
        add(new JLabel("Water: 0.0"));
        add(new JLabel("Air: 0.0"));
        add(new JLabel("Solar: 0.0"));
        add(new JLabel("Heat: 0.0"));
    }

    private void updateSpecificInputLabel() {
        String selectedEnergy = (String) energyTypeComboBox.getSelectedItem();
        specificInputLabel.setText("Enter " + getSpecificInputLabel(selectedEnergy) + ":");
    }

    private String getSpecificInputLabel(String energyType) {
        switch (energyType) {
            case "Water":
                return "Water Wheel Size";
            case "Air":
                return "Wind Speed";
            case "Solar":
                return "Time of Daylight";
            case "Heat":
                return "Heat Temperature";
            default:
                return "Specific Value";
        }
    }

    private String getTimeSelection() {
        if (perMinuteRadioButton.isSelected()) {
            return "Minute";
        } else if (perHourRadioButton.isSelected()) {
            return "Hour";
        } else if (perDayRadioButton.isSelected()) {
            return "Day";
        } else if (perMonthRadioButton.isSelected()) {
            return "Month";
        } else if (perYearRadioButton.isSelected()) {
            return "Year";
        } else {
            return "";
        }
    }

    private String handleEnergyCalculation(String selectedEnergy, double specificValue, String timeSelection) {
        switch (selectedEnergy) {
            case "Water":
                WaterEnergy waterEnergy = new WaterEnergy(specificValue);
                double flowOfWater = calculateFlowBasedOnTime(specificValue, timeSelection);
                double waterEnergyResult = waterEnergy.calculateEnergy(flowOfWater);
                updateAverageEnergy("Water", waterEnergyResult);
                return "Water Energy: " + waterEnergyResult;

            case "Air":
                AirEnergy airEnergy = new AirEnergy(specificValue);
                double airTemperature = calculateTemperatureBasedOnTime(specificValue, timeSelection);
                double airEnergyResult = airEnergy.calculateEnergy(airTemperature);
                updateAverageEnergy("Air", airEnergyResult);
                return "Air Energy: " + airEnergyResult;

            case "Solar":
                SolarEnergy solarEnergy = new SolarEnergy(specificValue);
                double solarPanelSize = calculatePanelSizeBasedOnTime(specificValue, timeSelection);
                double solarEnergyResult = solarEnergy.calculateEnergy(solarPanelSize);
                updateAverageEnergy("Solar", solarEnergyResult);
                return "Solar Energy: " + solarEnergyResult;

            case "Heat":
                HeatEnergy heatEnergy = new HeatEnergy(specificValue, getTimeInHours(timeSelection));
                double heatGeneratedOverTime = getDoubleInput("Enter Heat Generated Over Time:");
                double heatEnergyResult = heatEnergy.calculateEnergy(heatGeneratedOverTime);
                updateAverageEnergy("Heat", heatEnergyResult);
                return "Heat Energy: " + heatEnergyResult;

            default:
                return "Invalid energy type selection.";
        }
    }

    private double calculateFlowBasedOnTime(double specificValue, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return specificValue / 60.0;
        } else if (timeSelection.equals("Day")) {
            return specificValue * 24.0;
        } else if (timeSelection.equals("Month")) {
            return specificValue * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return specificValue * 24.0 * 365.0;
        } else {
            return specificValue;
        }
    }

    private double calculateTemperatureBasedOnTime(double specificValue, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return specificValue / 60.0;
        } else if (timeSelection.equals("Day")) {
            return specificValue * 24.0;
        } else if (timeSelection.equals("Month")) {
            return specificValue * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return specificValue * 24.0 * 365.0;
        } else {
            return specificValue;
        }
    }

    private double calculatePanelSizeBasedOnTime(double specificValue, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return specificValue / 60.0;
        } else if (timeSelection.equals("Day")) {
            return specificValue * 24.0;
        } else if (timeSelection.equals("Month")) {
            return specificValue * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return specificValue * 24.0 * 365.0;
        } else {
            return specificValue;
        }
    }

    private int getTimeInHours(String timeSelection) {
        if (timeSelection.equals("Minute")) {
            return 1;
        } else if (timeSelection.equals("Hour")) {
            return 1;
        } else if (timeSelection.equals("Day")) {
            return 24;
        } else if (timeSelection.equals("Month")) {
            return 24 * 30;
        } else if (timeSelection.equals("Year")) {
            return 24 * 365;
        } else {
            return 0;
        }
    }

    private double getDoubleInput(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null) {
            return 0.0;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return getDoubleInput(message);
        }
    }

    private void updateAverageEnergy(String energyType, double energyValue) {
        EnergyTypeStats energyTypeStats = energyStatsMap.getEnergyTypeStats(energyType);
        energyTypeStats.addEnergyValue(energyValue);

        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String labelText = label.getText();
                if (labelText.startsWith(energyType)) {
                    label.setText(energyType + ": " + energyTypeStats.getAverageEnergy());
                }
            }
        }
    }

    public void showWindow() {
        setVisible(true);
    }


}
