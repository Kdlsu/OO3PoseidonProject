import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnergySelectionWindow extends JFrame {
    private JComboBox<String> energyTypeComboBox;
    private JButton calculateButton;
    private JTextArea outputTextArea;
    private JTextField powerInputField;
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
        add(energyTypeComboBox);

        powerInputField = new JTextField(10);
        add(new JLabel("Power Generated:"));
        add(powerInputField);

        perMinuteRadioButton = new JRadioButton("Per Minute");
        perHourRadioButton = new JRadioButton("Per Hour");
        perDayRadioButton = new JRadioButton("Per Day");
        perMonthRadioButton = new JRadioButton("Per Month");
        perYearRadioButton = new JRadioButton("Per Year");

        // Add radio buttons to a button group, so only one can be selected at a time
        timeButtonGroup = new ButtonGroup();
        timeButtonGroup.add(perMinuteRadioButton);
        timeButtonGroup.add(perHourRadioButton);
        timeButtonGroup.add(perDayRadioButton);
        timeButtonGroup.add(perMonthRadioButton);
        timeButtonGroup.add(perYearRadioButton);

        // Set "Per Hour" as the default selection
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
                double power = Double.parseDouble(powerInputField.getText());
                String timeSelection = getTimeSelection();
                String result = handleEnergyCalculation(selectedEnergy, power, timeSelection);
                outputTextArea.setText(result); // Set the result text in the output text area
            }
        });
        add(calculateButton);

        // Initialize and add the output text area
        outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane);

        // Display the average energy of each energy type
        JLabel averageEnergyLabel = new JLabel("Average Energy:");
        add(averageEnergyLabel);
        add(new JLabel("Water: 0.0"));
        add(new JLabel("Air: 0.0"));
        add(new JLabel("Solar: 0.0"));
        add(new JLabel("Heat: 0.0"));
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

    private String handleEnergyCalculation(String selectedEnergy, double power, String timeSelection) {
        // Perform the energy calculations and return the result as a string
        switch (selectedEnergy) {
            case "Water":
                double waterWheelSize = getDoubleInput("Enter Water Wheel Size:");
                WaterEnergy waterEnergy = new WaterEnergy(waterWheelSize);
                double flowOfWater = calculateFlowBasedOnTime(power, timeSelection);
                double waterEnergyResult = waterEnergy.calculateEnergy(flowOfWater);
                updateAverageEnergy("Water", waterEnergyResult); // Update the average energy display
                return "Water Energy: " + waterEnergyResult;

            case "Air":
                double windSpeed = getDoubleInput("Enter Wind Speed:");
                AirEnergy airEnergy = new AirEnergy(windSpeed);
                double airTemperature = calculateTemperatureBasedOnTime(power, timeSelection);
                double airEnergyResult = airEnergy.calculateEnergy(airTemperature);
                updateAverageEnergy("Air", airEnergyResult); // Update the average energy display
                return "Air Energy: " + airEnergyResult;

            case "Solar":
                double timeOfDayLight = getDoubleInput("Enter Time of Daylight:");
                SolarEnergy solarEnergy = new SolarEnergy(timeOfDayLight);
                double solarPanelSize = calculatePanelSizeBasedOnTime(power, timeSelection);
                double solarEnergyResult = solarEnergy.calculateEnergy(solarPanelSize);
                updateAverageEnergy("Solar", solarEnergyResult); // Update the average energy display
                return "Solar Energy: " + solarEnergyResult;

            case "Heat":
                double heatTemperature = getDoubleInput("Enter Heat Temperature:");
                int time = calculateTimeInHours(power, timeSelection);
                HeatEnergy heatEnergy = new HeatEnergy(heatTemperature, time);
                double heatGeneratedOverTime = getDoubleInput("Enter Heat Generated Over Time:");
                double heatEnergyResult = heatEnergy.calculateEnergy(heatGeneratedOverTime);
                updateAverageEnergy("Heat", heatEnergyResult); // Update the average energy display
                return "Heat Energy: " + heatEnergyResult;

            default:
                return "Invalid energy type selection.";
        }
    }

    // Utility methods to convert power to specific units based on time
    private double calculateFlowBasedOnTime(double power, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return power / 60.0;
        } else if (timeSelection.equals("Day")) {
            return power * 24.0;
        } else if (timeSelection.equals("Month")) {
            return power * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return power * 24.0 * 365.0;
        } else {
            return power;
        }
    }

    private double calculateTemperatureBasedOnTime(double power, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return power / 60.0;
        } else if (timeSelection.equals("Day")) {
            return power * 24.0;
        } else if (timeSelection.equals("Month")) {
            return power * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return power * 24.0 * 365.0;
        } else {
            return power;
        }
    }

    private double calculatePanelSizeBasedOnTime(double power, String timeSelection) {
        // Implement conversion based on time (per minute, per hour, etc.)
        if (timeSelection.equals("Minute")) {
            return power / 60.0;
        } else if (timeSelection.equals("Day")) {
            return power * 24.0;
        } else if (timeSelection.equals("Month")) {
            return power * 24.0 * 30.0;
        } else if (timeSelection.equals("Year")) {
            return power * 24.0 * 365.0;
        } else {
            return power;
        }
    }

    private int calculateTimeInHours(double power, String timeSelection) {
        // Convert time to hours
        if (timeSelection.equals("Minute")) {
            return (int) (power / 60.0);
        } else if (timeSelection.equals("Hour")) {
            return (int) power;
        } else if (timeSelection.equals("Day")) {
            return (int) (power * 24.0);
        } else if (timeSelection.equals("Month")) {
            return (int) (power * 24.0 * 30.0);
        } else if (timeSelection.equals("Year")) {
            return (int) (power * 24.0 * 365.0);
        } else {
            return 0;
        }
    }

    private double getDoubleInput(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null) {
            // User clicked cancel or closed the input dialog
            return 0.0;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
            return getDoubleInput(message);
        }
    }

    // Update the average energy display for each energy type
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
